package com.jingxiang.datachange.service;
//
//
//import com.github.pagehelper.PageInfo;
//
//import java.io.UnsupportedEncodingException;
//import java.security.NoSuchAlgorithmException;
//import java.util.List;
//
//
//public interface UserInfoService {
//
//    //查询所有用户
//    PageInfo<UserInfo> getAllUsers(int pageNum,int pageSize);
//
//    //修改用户
//    void updateUser(UserInfo userInfo);
//
//    //新增
//    boolean isExist(String username);      //判断用户名是否已存在,false已经存在，true不存在。
//
//    void addUserInfo(UserInfo userInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException;
//
//    void registerUserInfo(UserInfo userInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException;
//
//    //绑定、修改xpack用户信息
//
//    //禁用用户（逻辑删除）
//    void setDisableUserById(Integer id);
//
//    void setDisableUserByUsername(String username);
//
//    //启用用户
//    void setEnableUserById(Integer id);
//
//    void setEnableUserByUsername(String username);
//
//    //物理删除
//    void deleteUserInfoById(String ids);
//
//    void deleteUserInfoByUsername(String username);
//
//    //改密码
//    void updatePasswordById(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;
//
//    void updatePasswordByUsername(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;
//
//    //查询密码
//    String findPasswordById(Integer id);
//
//    String findPasswordByUsername(String username);
//
//    //查询UserInfo对象
//    UserInfo findUserInfoById(Integer id);
//
//    UserInfo findUserInfoByUsername(String username);
//
//
//    //查询权限组
//    List<String> findPermissionsById(Integer id);
//
//    List<String> findPermissionsByUsername(String username);
//
//    //查询用户和权限的信息
//    UserInfo queryUPByUsername(String username);
//
//    UserInfo queryAllByUserkey(String userkey);
//
//    //根据用户名新增用户权限
//    void addPermissionByUsername(String username, String permission);
//
//    //根据用户id添加权限
//    void addPermissionByUserId(Integer user_id);
//
//    //根据用户名更新用户权限
//    void updatePermissionByUsername(String username, Integer permission_id, String permission);
//
//    //根据用户名删除具体权限
//    void deletePermissionByUsername(String username, Integer permission_id);
//
//    //根据userkey获取xpack权限
//    UserInfo getXpackByUsername(String username);
//
//
//    //获取超级用户
//    UserInfo getSuperUser();
//
//    //根据用户名更新登录时间,上次登录时间,登录次数
//    void updateLogintimeByUsername(String username);
//
//
//    //根据用户获取有权限的开放接口列表id
//    List<Integer> getAccessUrlId(String username);
//}
