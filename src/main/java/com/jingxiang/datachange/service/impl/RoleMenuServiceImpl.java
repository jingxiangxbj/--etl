package com.jingxiang.datachange.service.impl;

import com.jingxiang.datachange.entity.RoleMenu;
import com.jingxiang.datachange.service.BaseService;
import com.jingxiang.datachange.service.RoleMenuServie;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RoleMenuServiceImpl extends BaseService<RoleMenu> implements RoleMenuServie {

	@Override
	public void deleteRoleMenusByRoleId(String roleIds) {
		List<String> list = Arrays.asList(roleIds.split(","));
		this.batchDelete(list, "roleId", RoleMenu.class);
	}

	@Override
	public void deleteRoleMenusByMenuId(String menuIds) {
		List<String> list = Arrays.asList(menuIds.split(","));
		this.batchDelete(list, "menuId", RoleMenu.class);
	}

}
