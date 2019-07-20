package com.jingxiang.datachange.mapper;

import com.jingxiang.datachange.config.MyMapper;
import com.jingxiang.datachange.entity.Role;
import com.jingxiang.datachange.entity.RoleWithMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface RoleMapper extends MyMapper<Role> {
    List<Role> findUserRole(String userName);
    List<Role> findAll();

    List<RoleWithMenu> findById(Long roleId);
    void deleteRole(long id);
}