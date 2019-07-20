package com.jingxiang.datachange.mapper;

import com.jingxiang.datachange.config.MyMapper;
import com.jingxiang.datachange.entity.User;
import com.jingxiang.datachange.entity.UserWithRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserMapper extends MyMapper<User> {
    List<User> findUserList(User user);

    List<UserWithRole> findUserWithRole(Long userId);

    User findUserProfile(Long userId);

    List<User> select();
}