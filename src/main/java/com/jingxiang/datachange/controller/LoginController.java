package com.jingxiang.datachange.controller;

import com.jingxiang.datachange.entity.User;
import com.jingxiang.datachange.service.UserService;
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

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


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



}
