package com.jingxiang.datachange.service;

import com.jingxiang.datachange.entity.UserRole;

public interface UserRoleService extends IService<UserRole> {

	void deleteUserRolesByRoleId(String roleIds);

	void deleteUserRolesByUserId(String userIds);
}
