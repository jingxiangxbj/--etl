package com.jingxiang.datachange.controller;

import com.jingxiang.datachange.config.Log;
import com.jingxiang.datachange.entity.User;
import com.jingxiang.datachange.entity.UserRole;
import com.jingxiang.datachange.entity.UserWithRole;
import com.jingxiang.datachange.service.UserService;
import com.jingxiang.datachange.util.MD5Util;
import com.jingxiang.datachange.util.QueryRequest;
import com.jingxiang.datachange.util.ResponseBo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
@Slf4j
@Controller
public class UserController extends BaseController {


    @Autowired
    private UserService userService;

    private static final String ON = "on";

    @RequestMapping("user")
    @RequiresPermissions("user:list")
    public String index() {
//        User user = super.getCurrentUser();
//        model.addAttribute("user", user);
        return "system/user/user";
    }

    @RequestMapping("user/checkUserName")
    @ResponseBody
    public boolean checkUserName(String username, String oldusername) {
        if (StringUtils.isNotBlank(oldusername) && username.equalsIgnoreCase(oldusername)) {
            return true;
        }
        User result = this.userService.findByName(username);
        return result == null;
    }

    @RequestMapping(value = "user/getUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBo getUser(Long userId) {
        try {
            UserWithRole user = this.userService.findById(userId);
            return ResponseBo.ok(user);
        } catch (Exception e) {
            return ResponseBo.error("获取用户失败，请联系网站管理员！");
        }
    }

    @RequestMapping("user/list")
    @RequiresPermissions("user:list")
    @ResponseBody
    public Map<String, Object> userList(QueryRequest request, User user) {
        try {
            return super.selectByPageNumSize(request, () -> this.userService.findUserList(user, request));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @RequestMapping("user/regist")
    @ResponseBody
    public ResponseBo regist(User user) {
        try {
            User result = this.userService.findByName(user.getUsername());
            if (result != null) {
                return ResponseBo.warn("该用户名已被使用！");
            }
            this.userService.registUser(user);
            return ResponseBo.ok();
        } catch (Exception e) {
            return ResponseBo.error("注册失败，请联系网站管理员！");
        }
    }

    @Log("新增用户")
    @RequiresPermissions("user:add")
    @RequestMapping("user/add")
    @ResponseBody
    public ResponseBo addUser(User user, Long[] roles) {
        try {
            if (ON.equalsIgnoreCase(user.getStatus()))
                user.setStatus(User.STATUS_VALID);
            else
                user.setStatus(User.STATUS_LOCK);
            this.userService.addUser(user, roles);
            return ResponseBo.ok("新增用户成功！");
        } catch (Exception e) {
log.error("新增用户失败",e);
            return ResponseBo.error("新增用户失败，请联系网站管理员！");
        }
    }

    @RequiresPermissions("user:update")
    @RequestMapping("user/update")
    @Log("修改用户")
    @ResponseBody
    public ResponseBo updateUser(User user, Long[] rolesSelect) {
        try {
            if (ON.equalsIgnoreCase(user.getStatus()))
                user.setStatus(User.STATUS_VALID);
            else
                user.setStatus(User.STATUS_LOCK);
            this.userService.updateUser(user, rolesSelect);
            return ResponseBo.ok("修改用户成功！");
        } catch (Exception e) {
            log.error("修改用户失败",e);
            return ResponseBo.error("修改用户失败，请联系网站管理员！");
        }
    }


    @RequiresPermissions("user:delete")
    @RequestMapping("user/delete")
    @Log("删除用户")
    @ResponseBody
    public ResponseBo deleteUsers(String ids) {
        try {
            this.userService.deleteUsers(ids);
            return ResponseBo.ok("删除用户成功！");
        } catch (Exception e) {
            log.error("删除用户失败",e);
            return ResponseBo.error("删除用户失败，请联系网站管理员！");
        }
    }

    @RequestMapping("user/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = getCurrentUser();
        return MD5Util.validPassword(password, user.getPassword());
    }

    @RequestMapping("user/updatePassword")
    @Log("修改密码")
    @ResponseBody
    public ResponseBo updatePassword(String newPassword) {
        try {
            this.userService.updatePassword(newPassword);
            return ResponseBo.ok("更改密码成功！");
        } catch (Exception e) {
            log.error("更改密码失败",e);
            return ResponseBo.error("更改密码失败，请联系网站管理员！");
        }
    }

    @RequestMapping("user/profile")
    public String profileIndex(Model model) {
        User user = super.getCurrentUser();
        Long userId = user.getUserId();
        user = this.userService.findUserProfile(userId);
        String ssex = user.getSsex();
        if (User.SEX_MALE.equals(ssex)) {
            user.setSsex("性别：男");
        } else if (User.SEX_FEMALE.equals(ssex)) {
            user.setSsex("性别：女");
        } else {
            user.setSsex("性别：保密");
        }
        model.addAttribute("user", user);
        return "system/user/profile";
    }

    @RequestMapping("user/getUserProfile")
    @ResponseBody
    public ResponseBo getUserProfile(Long userId) {
        try {
            // User user = new User();
            // user.setUserId(userId);
            return ResponseBo.ok(this.userService.findUserProfile(userId));
        } catch (Exception e) {
            return ResponseBo.error("获取用户信息失败，请联系网站管理员！");
        }
    }

    @RequestMapping("user/updateUserProfile")
    @Log("更新个人信息")
    @ResponseBody
    public ResponseBo updateUserProfile(User user) {
        try {
            User currentUser = super.getCurrentUser();
            Long userId = currentUser.getUserId();
            user.setUserId(userId);
            this.userService.updateUserProfile(user);
            return ResponseBo.ok("更新个人信息成功！");
        } catch (Exception e) {
            log.error("更新用户信息失败",e);
            return ResponseBo.error("更新用户信息失败，请联系网站管理员！");
        }
    }


}
