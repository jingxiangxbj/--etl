package com.jingxiang.datachange.service;

import com.jingxiang.datachange.entity.Role;
import com.jingxiang.datachange.entity.RoleMenu;
import com.jingxiang.datachange.entity.RoleWithMenu;

import java.util.List;

public interface RoleService extends IService<Role> {

	List<Role> findUserRole(String userName);

	List<Role> findAllRole(Role role);

	RoleWithMenu findRoleWithMenus(Long roleId);

	Role findByName(String roleName);

	void addRole(Role role, Long[] menuIds);
	
	void updateRole(Role role, Long[] menuIds);

	void deleteRoles(String roleIds);
}
