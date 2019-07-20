package com.jingxiang.datachange.controller;

import com.jingxiang.datachange.entity.Email;
import com.jingxiang.datachange.service.EmailService;
import com.jingxiang.datachange.util.QueryRequest;
import com.jingxiang.datachange.util.ResponseBo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class EmailController extends BaseController{
    @Autowired
    private EmailService emailService;

    @RequestMapping("email")
    @RequiresPermissions("email:list")
    public String index() {
        return "system/dict/dict";
    }

    @ApiOperation(value = "查询所有邮件信息", httpMethod = "GET")
    @RequestMapping("email/list")
    @RequiresPermissions("email:list")
    @ResponseBody
    public Map<String, Object> getEmail(QueryRequest request, Email email) {
        try {
            return super.selectByPageNumSize(request,()->emailService.findAllEmail(email));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    @ApiOperation(value = "删除邮件", httpMethod = "GET")
    @RequestMapping("email/delete")
    @RequiresPermissions("email:delete")
    @ResponseBody
    public ResponseBo deleteById(String ids) {
        try {
            List<String> list = Arrays.asList(ids.split(","));
            emailService.batchDelete(list,"id",Email.class);
            return ResponseBo.ok("删除邮件成功");
        } catch (Exception e) {
            return ResponseBo.error("删除邮件失败");
        }
    }



}
