package com.jingxiang.datachange.service.impl;

import com.jingxiang.datachange.entity.UserRole;
import com.jingxiang.datachange.service.BaseService;
import com.jingxiang.datachange.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserRoleServiceImpl extends BaseService<UserRole> implements UserRoleService {

	@Override
	public void deleteUserRolesByRoleId(String roleIds) {
		List<String> list = Arrays.asList(roleIds.split(","));
		this.batchDelete(list, "roleId", UserRole.class);
	}

	@Override
	public void deleteUserRolesByUserId(String userIds) {
		List<String> list = Arrays.asList(userIds.split(","));
		this.batchDelete(list, "userId", UserRole.class);
	}

}
