package com.jingxiang.datachange.controller;

import com.jingxiang.datachange.entity.UserOnline;
import com.jingxiang.datachange.service.SessionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class SessionController {


    @Autowired
    SessionService sessionService;

    @RequestMapping("session")
    @RequiresPermissions("session:list")
    public String online() {
        return "system/monitor/online";
    }

    @ResponseBody
    @RequestMapping("session/list")
    @RequiresPermissions("session:list")
    public Map<String, Object> list() {
        List<UserOnline> list = sessionService.list();
        Map<String, Object> rspData = new HashMap<>();
        rspData.put("rows", list);
        rspData.put("total", list.size());
        return rspData;
    }


}
