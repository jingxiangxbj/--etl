package com.jingxiang.datachange.controller;

import com.alibaba.fastjson.JSON;
import com.jingxiang.datachange.entity.QQDTO;
import com.jingxiang.datachange.entity.QQOpenidDTO;
import com.jingxiang.datachange.entity.QQProperties;
import com.jingxiang.datachange.entity.User;
import com.jingxiang.datachange.service.UserService;
import com.jingxiang.datachange.util.HttpsUtils;
import com.jingxiang.datachange.util.MD5Util;
import com.jingxiang.datachange.util.ResponseBo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.UUID;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;
//    @Autowired
//    private OperateService operateService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/index")
    public String toIndex(Model model) {
        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("user", currentUser);
        return "index";
    }

    @RequestMapping("/")
    public String redirectIndex() {
        return "redirect:/index";
    }

    @GetMapping("/403")
    public String forbid() {
        return "403";
    }

    @ApiOperation(value = "登录", httpMethod = "POST")
    @ResponseBody
    @PostMapping(value = "/login")
    public ResponseBo login(String username, String password, Boolean rememberMe) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = userService.findByName(username);
        if (user == null) {
            return ResponseBo.error("用户不存在！");
        }
        String pwdInDb = user.getPassword();
        boolean flag = MD5Util.validPassword(password, pwdInDb);
        if (flag == false) {
            return ResponseBo.error("用户名或密码错误！");
        }
        String md5password = MD5Util.getValidPassword(password, pwdInDb);
        UsernamePasswordToken token = new UsernamePasswordToken(username, md5password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            if (subject != null)
                subject.logout();
            subject.login(token);
            //更新登录时间和登录次数
            userService.updateLoginTime(username);
            return ResponseBo.ok();
        } catch (UnknownAccountException | IncorrectCredentialsException | LockedAccountException e) {
            e.printStackTrace();
            return ResponseBo.error(e.getMessage());
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseBo.error("认证失败！");
        }
    }

    private QQProperties qqProperties;

    @GetMapping("/login/qq")
    public void getCode(HttpServletResponse response) {
        try {
            response.sendRedirect(qqProperties.getCode_callback_uri()
                    + "?client_id=" + qqProperties.getClient_id()
                    + "&state=" + UUID.randomUUID()
                    + "&redirect_uri=" + qqProperties.getRedirect_uri()
                    + "&response_type=code");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/authorize/qq")
    public String authorizeQQ(HashMap<String, String> msg, String code) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", qqProperties.getRedirect_uri());
        params.put("client_id", qqProperties.getClient_id());
        params.put("client_secret", qqProperties.getClient_secret());
        String result = HttpsUtils.doGet(qqProperties.getAccess_token_callback_uri(), params);
        String[] strings = result.split("&");
        HashMap<String, String> results = new HashMap<>();
        for (String string : strings
                ) {
            String[] split = string.split("=");
            if (split.length > 1) {
                results.put(split[0], split[1]);
            }
        }
        String openidContent = HttpsUtils.doGet(qqProperties.getOpenid_callback_uri() + "?access_token=" + results.get("access_token"));
        String openid = openidContent.substring(openidContent.indexOf("{"), openidContent.indexOf("}" + 1));
        QQOpenidDTO qqOpenidDTO = JSON.parseObject(openid, QQOpenidDTO.class);
        params.clear();
        params.put("access_token", results.get("access_token"));//设置access_token
        params.put("openid", qqOpenidDTO.getOpenid());//设置openid
        params.put("oauth_consumer_key", qqOpenidDTO.getClient_id());
        String userInfo = HttpsUtils.doPost(qqProperties.getUser_info_callback_uri(), params);
        QQDTO qqdto = JSON.parseObject(userInfo, QQDTO.class);
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(qqdto.getNickname(), qqOpenidDTO.getOpenid()));
        } catch (Exception e) {
            msg.put("msg", "第三方登陆失败,请联系管理！");
            return "login.html";
        }
        return "redirect:/index";

    }
}
