package com.jingxiang.datachange.service.impl;//package com.supconit.its.prod.monitor_dataflow.service.impl;
//
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.supconit.its.prod.monitor_dataflow.entity.UserInfo;
//import com.supconit.its.prod.monitor_dataflow.repository.UserInfoRepository;
//import com.supconit.its.prod.monitor_dataflow.service.UserInfoService;
//import com.supconit.its.prod.monitor_dataflow.util.MD5Util;
//import org.apache.shiro.SecurityUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.UnsupportedEncodingException;
//import java.security.NoSuchAlgorithmException;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//
//@Service
//public class UserInfoServiceImpl implements UserInfoService {
//    @Autowired
//    private UserInfoRepository userInfoRepository;
//
//    @Override
//    public PageInfo<UserInfo> getAllUsers(int pageNum, int pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<UserInfo> users = userInfoRepository.getAllUsers();
//        PageInfo<UserInfo> userInfoPageInfo = new PageInfo<>(users);
//        return userInfoPageInfo;
//
//    }
//
//    @Override
//    public void updateUser(UserInfo userInfo) {
//        userInfoRepository.updateUser(userInfo);
//    }
//
//    @Override
//    public boolean isExist(String username) {
//        UserInfo userInfo = userInfoRepository.findUserInfoByUsername(username);
//        if (userInfo != null) {
//            return false;      //已存在
//        }
//        return true;    //不存在，可继续添加
//    }
//
//    @Override
//    public void addUserInfo(UserInfo userInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        String username = userInfo.getUsername();
//        String password = userInfo.getPassword();
//        String email = userInfo.getEmail();
//        String mobile = userInfo.getMobile();
//        String ssex = userInfo.getSsex();
//        String md5password = MD5Util.getEncryptedPwd(password);
//        userInfoRepository.addUserInfo(username, md5password, email,mobile,ssex);
//    }
//
//    @Override
//    public void registerUserInfo(UserInfo userInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        try {
//            String encryptedPwd = MD5Util.getEncryptedPwd(userInfo.getPassword());
//            userInfoRepository.registerUserInfo(userInfo.getUsername(), encryptedPwd);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//
//
//    @Override
//    public void setDisableUserById(Integer id) {
//        userInfoRepository.setDisableUserById(id);
//    }
//
//    @Override
//    public void setDisableUserByUsername(String username) {
//        userInfoRepository.setDisableUserByUsername(username);
//    }
//
//    @Override
//    public void setEnableUserById(Integer id) {
//        userInfoRepository.setEnableUserById(id);
//    }
//
//    @Override
//    public void setEnableUserByUsername(String username) {
//        userInfoRepository.setEnableUserByUsername(username);
//    }
//
//    @Override
//    public void deleteUserInfoById(String ids) {
//        List<String> list = Arrays.asList(ids.split(","));
//        for (String id:list
//             ) {
//            int i = Integer.parseInt(id);
//            userInfoRepository.deleteUserInfoById(i);
//
//        }
//    }
//
//    @Override
//    public void deleteUserInfoByUsername(String username) {
//        userInfoRepository.deleteUserInfoByUsername(username);
//    }
//
//    @Override
//    public void updatePasswordById(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
//        Integer userId = user.getId();
//        String md5password = MD5Util.getEncryptedPwd(password);
//        userInfoRepository.updatePasswordById(userId, md5password);
//    }
//
//    @Override
//    public void updatePasswordByUsername(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        String md5password = MD5Util.getEncryptedPwd(password);
//        userInfoRepository.updatePasswordByUsername(username, md5password);
//    }
//
//    @Override
//    public UserInfo findUserInfoById(Integer id) {
//        UserInfo userInfo = userInfoRepository.findUserInfoById(id);
//        return userInfo;
//    }
//
//    @Override
//    public UserInfo findUserInfoByUsername(String username) {
//        UserInfo userInfo = userInfoRepository.findUserInfoByUsername(username);
//        return userInfo;
//    }
//
//
//    @Override
//    public String findPasswordById(Integer id) {
//        String md5password = userInfoRepository.findPasswordById(id);
//        return md5password;
//    }
//
//    @Override
//    public String findPasswordByUsername(String username) {
//        String md5password = userInfoRepository.findPasswordByUsername(username);
//        return md5password;
//    }
//
//    @Override
//    public List<String> findPermissionsById(Integer id) {
//        List<String> permissions = userInfoRepository.findPermissionsById(id);
//        return permissions;
//    }
//
//    @Override
//    public List<String> findPermissionsByUsername(String username) {
//        List<String> permissions = userInfoRepository.findPermissionsByUsername(username);
//        return permissions;
//    }
//
//    @Override
//    public UserInfo queryUPByUsername(String username) {
//        return userInfoRepository.queryUPByUsername(username);
//    }
//
//    @Override
//    public UserInfo queryAllByUserkey(String userkey) {
//        return userInfoRepository.queryAllByUserkey(userkey);
//    }
//
//    @Override
//    public void addPermissionByUsername(String username, String permission) {
//        userInfoRepository.addPermissionByUsername(username, permission);
//    }
//
//    @Override
//    public void addPermissionByUserId(Integer user_id) {
//
//    }
//
//    @Override
//    public void updatePermissionByUsername(String username, Integer permission_id, String permission) {
//        userInfoRepository.updatePermissionByUsername(username, permission_id, permission);
//    }
//
//    @Override
//    public void deletePermissionByUsername(String username, Integer permission_id) {
//        userInfoRepository.deletePermissionByUsername(username, permission_id);
//    }
//
//    @Override
//    public UserInfo getXpackByUsername(String username) {
//        UserInfo userInfo = userInfoRepository.getXpackByUsername(username);
//        return userInfo;
//    }
//
//
//    @Override
//    public UserInfo getSuperUser() {
//        return userInfoRepository.getSuperUser();
//    }
//
//    @Override
//    public void updateLogintimeByUsername(String username) {
//        try {
//            Timestamp lastlogintime = userInfoRepository.getLoginTimeByUsername(username);
//            userInfoRepository.updateLogintimeByUsername(username, lastlogintime);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public List<Integer> getAccessUrlId(String username) {
//        List<Integer> list = new ArrayList<>();
//        String strs = userInfoRepository.getAccessUrlId(username);
//        if (strs != null && strs.length() > 0) {
//            String[] array = strs.split(",");
//            for (String str : array
//                    ) {
//                list.add(Integer.parseInt(str));
//            }
//        }
//        return list;
//    }
//
//
//}
