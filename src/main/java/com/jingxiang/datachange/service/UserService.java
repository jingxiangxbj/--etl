package com.jingxiang.datachange.service;

import com.jingxiang.datachange.entity.User;
import com.jingxiang.datachange.entity.UserRole;
import com.jingxiang.datachange.entity.UserWithRole;
import com.jingxiang.datachange.util.QueryRequest;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@CacheConfig(cacheNames = "UserService")
public interface UserService extends IService<User> {

    UserWithRole findById(Long userId);

    User findByName(String userName);

    @Cacheable(key = "#p0.toString() + (#p1 != null ? #p1.toString() : '')")
    List<User> findUserList(User user, QueryRequest request);

    @CacheEvict(key = "#p0", allEntries = true)
    void registUser(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException;


    @CacheEvict(allEntries = true)
    void addUser(User user, Long[] roles) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    @CacheEvict(key = "#p0", allEntries = true)
    void updateUser(User user, Long[] roles);

    @CacheEvict(key = "#p0", allEntries = true)
    void deleteUsers(String userIds);

    void updateLoginTime(String userName);


    void updatePassword(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    User findUserProfile(Long userId);

    void updateUserProfile(User user);
}
