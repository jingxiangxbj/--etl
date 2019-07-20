package com.jingxiang.datachange.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jingxiang.datachange.entity.User;
import com.jingxiang.datachange.entity.UserOnline;
import com.jingxiang.datachange.mapper.UserMapper;
import com.jingxiang.datachange.service.SessionService;
import com.jingxiang.datachange.util.GetAddressUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Shiro Session 对象管理
 *
 * @author MrBird
 * @link https://mrbird.cc/Spring-Boot-Shiro%20session.html
 */
@Service("sessionService")
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionDAO sessionDAO;

    @Autowired
    ObjectMapper mapper;
    @Resource
    private  UserMapper userMapper;

    @Override
    public List<UserOnline> list() {
        List<UserOnline> list = new ArrayList<>();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            UserOnline userOnline = new UserOnline();
            User user;
            SimplePrincipalCollection principalCollection;
            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
                continue;
            } else {
                principalCollection = (SimplePrincipalCollection) session
                        .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                user = (User) principalCollection.getPrimaryPrincipal();
                userOnline.setUsername(user.getUsername());
                userOnline.setUserId(user.getUserId().toString());
            }
            userOnline.setId((String) session.getId());
            userOnline.setHost(session.getHost().equals("0:0:0:0:0:0:0:1") ? "127.0.0.1": session.getHost());
            userOnline.setStartTimestamp(session.getStartTimestamp());
            userOnline.setLastAccessTime(session.getLastAccessTime());
            long timeout = session.getTimeout();
            userOnline.setStatus(timeout == 0L ? "0" : "1");
           // String address = GetAddressUtils.getCityNameByTaoBaoAPI(userOnline.getHost());
            //userOnline.setLocation(address);
            User user1 = userMapper.selectByPrimaryKey(user);
            userOnline.setLoginCount(user1.getLoginCount());
            userOnline.setTimeout(timeout);
            list.add(userOnline);
        }
        return list;
    }

    @Override
    public boolean forceLogout(String sessionId) {
        Session session = sessionDAO.readSession(sessionId);
        session.setTimeout(0);
        session.stop();
        sessionDAO.delete(session);
        return true;
    }

}
