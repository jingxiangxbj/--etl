package com.jingxiang.datachange.config.shiro;

import com.jingxiang.datachange.entity.Menu;
import com.jingxiang.datachange.entity.Role;
import com.jingxiang.datachange.entity.User;
import com.jingxiang.datachange.service.MenuService;
import com.jingxiang.datachange.service.RoleService;
import com.jingxiang.datachange.service.UserService;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    @Override
    public String getName() {
        return "myRealm";
    }

    /* *
     * @date 2018/9/30 9:01
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @description: 身份认证登录
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        User user = userService.findByName(username);
        if (User.STATUS_LOCK.equals(user.getStatus()))
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        //不为空可在此检测用户状态，抛出用户状态异常等
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        return authenticationInfo;

    }

    /* *
     * @date 2018/9/30 9:01
     * @param [principals]
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @description: 权限管理
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String userName = user.getUsername();

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 获取用户角色集
        List<Role> roleList = this.roleService.findUserRole(userName);
        Set<String> roleSet = roleList.stream().map(Role::getRoleName).collect(Collectors.toSet());
        simpleAuthorizationInfo.setRoles(roleSet);

        // 获取用户权限集
        List<Menu> permissionList = this.menuService.findUserPermissions(userName);
        Set<String> permissionSet = permissionList.stream().map(Menu::getPerms).collect(Collectors.toSet());
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    //清除缓存,修改权限后用MyReal myReal = new MyReal(); myreal.clearCached();来清除缓存
//    public void clearCached(){
//        PrincipalCollection principals =  SecurityUtils.getSubject().getPrincipals();
//        super.clearCache(principals);
//    }
    public  void clearCache() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }

    //清除认证缓存
    public void clearCachedAuthenticationInfo() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCachedAuthenticationInfo(principals);
    }

    //清除授权缓存
    public void clearCachedAuthorizationInfo() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCachedAuthorizationInfo(principals);
    }

    public static User currentUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }
}
