package com.jingxiang.datachange.service;

import com.jingxiang.datachange.entity.UserOnline;

import java.util.List;

public interface SessionService {

	List<UserOnline> list();

	boolean forceLogout(String sessionId);
}
