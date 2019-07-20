package com.jingxiang.datachange.service.impl;

import com.jingxiang.datachange.config.shiro.MyRealm;
import com.jingxiang.datachange.entity.User;
import com.jingxiang.datachange.entity.UserRole;
import com.jingxiang.datachange.entity.UserWithRole;
import com.jingxiang.datachange.mapper.UserMapper;
import com.jingxiang.datachange.mapper.UserRoleMapper;
import com.jingxiang.datachange.service.BaseService;
import com.jingxiang.datachange.service.UserRoleService;
import com.jingxiang.datachange.service.UserService;
import com.jingxiang.datachange.util.MD5Util;
import com.jingxiang.datachange.util.QueryRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends BaseService<User> implements UserService {


    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private MyRealm myRealm;

    @Override
    public User findByName(String userName) {
        Example example = new Example(User.class);
        example.createCriteria().andCondition("lower(username)=", userName.toLowerCase());
        try {
            List<User> list = this.selectByExample(example);
            User user = new User();
            if (list.size() > 0) {
                return list.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> findUserList(User user, QueryRequest request) {
        try {
            return userMapper.findUserList(user);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void registUser(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        try {
            user.setCreateTime(new Date());
            user.setPassword(MD5Util.getEncryptedPwd(user.getPassword()));
            user.setStatus("1");
            this.save(user);
            UserRole ur = new UserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(25L);
            this.userRoleMapper.insert(ur);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(User user, Long[] roles) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        user.setCreateTime(new Date());
        user.setPassword(MD5Util.getEncryptedPwd(user.getPassword()));
        try {
            this.save(user);
            roles = roles == null ? new Long[]{3l} : roles;
            setUserRoles(user, roles);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUserRoles(User user, Long[] roles) {
        Arrays.stream(roles).forEach(roleId -> {
            UserRole ur = new UserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(roleId);
            this.userRoleMapper.insert(ur);
        });
    }

    @Override
    public void updateUser(User user, Long[] roles) {
        user.setPassword(null);
        user.setUsername(null);
        user.setModifyTime(new Date());
        this.updateNotNull(user);
        Example example = new Example(UserRole.class);
        example.createCriteria().andCondition("user_id=", user.getUserId());
        this.userRoleMapper.deleteByExample(example);
        roles = roles == null ? new Long[]{25l} : roles;
        setUserRoles(user, roles);
        User currentUser = MyRealm.currentUser();
        if (StringUtils.equalsAnyIgnoreCase(currentUser.getUsername(),user.getUsername())){
            myRealm.clearCache();
        }
    }

    @Override
    public void deleteUsers(String userIds) {
        List<String> list = Arrays.asList(userIds.split(","));//任意类型多个参数转换为相应类型的集合
        this.batchDelete(list, "userId", User.class);
        //关联删除用户角色
        this.userRoleService.deleteUserRolesByUserId(userIds);
    }

    @Override
    public void updateLoginTime(String userName) {
        Example example = new Example(User.class);
        example.createCriteria().andCondition("lower(username)=", userName.toLowerCase());
        User user = new User();
        User byName = this.findByName(userName);
        Date loginTime = byName.getLoginTime();
        user.setLastLoginTime(loginTime);
        long count = byName.getLoginCount();
        user.setLoginTime(new Date());
        user.setModifyTime(new Date());
        user.setLoginCount(count + 1);
        userMapper.updateByExampleSelective(user, example);
    }

    @Override
    public void updatePassword(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Example example = new Example(User.class);
        example.createCriteria().andCondition("username=", user.getUsername());
        user.setPassword(MD5Util.getEncryptedPwd(password));
        userMapper.updateByExampleSelective(user, example);
    }

    @Override
    public UserWithRole findById(Long userId) {
        List<UserWithRole> list = userMapper.findUserWithRole(userId);
        List<Long> roleList = list.stream().map(UserWithRole::getRoleId).collect(Collectors.toList());
        if (list.isEmpty())
            return null;
        UserWithRole userWithRole = list.get(0);
        userWithRole.setRoleIds(roleList);
        return userWithRole;
    }

    @Override
    public User findUserProfile(Long userId) {
        try {
            return userMapper.selectByPrimaryKey(userId);
            //return this.userMapper.findUserProfile(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateUserProfile(User user) {
        user.setModifyTime(new Date());
        this.updateNotNull(user);
    }

}
