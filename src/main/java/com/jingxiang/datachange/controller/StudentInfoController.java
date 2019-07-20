package com.jingxiang.datachange.controller;

import com.github.abel533.echarts.Option;
import com.jingxiang.datachange.entity.StudentInfoResult;
import com.jingxiang.datachange.service.StudentInfoService;
import com.jingxiang.datachange.util.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class StudentInfoController extends BaseController {
    @Autowired
    private StudentInfoService studentInfoService;
    @RequestMapping("hobby")
    @RequiresPermissions("studentInfo:hobby")
    public String index(){
        return "system/monitor/hobby";
    }

    @ResponseBody
    @RequiresPermissions("studentInfo:hobby")
    @GetMapping("hobby/countHobby")
    public ResponseBo countHobby() {
        try {
            Option option = studentInfoService.countHobby();
            return ResponseBo.ok(option);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseBo.error();
        }
    }
    @RequestMapping("studentInfo")
    @RequiresPermissions("studentInfo:list")
    public String indexs(){
        return "system/monitor/studentInfo";
    }
    @ResponseBody
    @RequiresPermissions("studentInfo:list")
    @GetMapping("studentInfo/list")
    public Map<String, Object> studentInfoResult(QueryRequest request, StudentInfoResult studentInfoResult) {
       if( org.apache.commons.lang3.StringUtils.isAnyBlank(studentInfoResult.getGender()) || org.apache.commons.lang3.StringUtils.isAnyBlank(studentInfoResult.getName())){
          studentInfoResult.setGender(null);
          studentInfoResult.setName(null);
       }
        List<StudentInfoResult> allStudentResult = this.studentInfoService.findAllStudentResult(studentInfoResult);
        for (StudentInfoResult student:allStudentResult
                ) {
            student.setIdCard(CommonUtils.idEncrypt(student.getIdCard()));
        }
        Map<String, Object> stringObjectMap = super.selectByPageNumSize(request, () -> allStudentResult);
        return stringObjectMap;
    }
    @RequestMapping("studentInfo/excel")
    @ResponseBody
    public ResponseBo userExcel(StudentInfoResult studentInfoResult) {
        if( org.apache.commons.lang3.StringUtils.isAnyBlank(studentInfoResult.getGender()) || org.apache.commons.lang3.StringUtils.isAnyBlank(studentInfoResult.getName())){
            studentInfoResult.setGender(null);
            studentInfoResult.setName(null);
        }
        try {
           List<StudentInfoResult> list = studentInfoService.findAllStudentResult(studentInfoResult);
           return FileUtil.createExcelByPOIKit("用户表", list, StudentInfoResult.class);
        } catch (Exception e) {
           return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("studentInfo/csv")
    @ResponseBody
    public ResponseBo userCsv(StudentInfoResult studentInfoResult) {
        if( org.apache.commons.lang3.StringUtils.isAnyBlank(studentInfoResult.getGender()) || org.apache.commons.lang3.StringUtils.isAnyBlank(studentInfoResult.getName())){
            studentInfoResult.setGender(null);
            studentInfoResult.setName(null);
        }
        try {
            List<StudentInfoResult> list = studentInfoService.findAllStudentResult(studentInfoResult);
            return FileUtil.createCsv("用户表", list, StudentInfoResult.class);
        } catch (Exception e) {
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }
}
