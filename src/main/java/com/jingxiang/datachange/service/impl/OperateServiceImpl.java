package com.jingxiang.datachange.service.impl;

import com.jingxiang.datachange.config.RestClientHelper;
import com.jingxiang.datachange.entity.Email;
import com.jingxiang.datachange.entity.StudentInfo;
import com.jingxiang.datachange.entity.StudentInfoResult;
import com.jingxiang.datachange.entity.User;
import com.jingxiang.datachange.repository.EtlRepository;
import com.jingxiang.datachange.service.MailService;
import com.jingxiang.datachange.service.OperateService;
import com.jingxiang.datachange.util.*;
import org.apache.shiro.SecurityUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OperateServiceImpl implements OperateService {
    @Autowired
    private EtlRepository etlRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private EmailServiceImpl emailService;
    @Value("${spring.mail.username}")
    private String from;

  //  @Scheduled(fixedRate = 60000)
    @Override
    public void transformStudentInfo() throws IOException {
        List<StudentInfo> studentInfos = this.extractStudentInfo();
        TransformUtils util = new TransformUtils();
        for (StudentInfo studentInfo : studentInfos
                ) {
            StudentInfoResult studentInfoResult = new StudentInfoResult();
            Integer id = studentInfo.getId();
            String birth = studentInfo.getBirth();
            String address = studentInfo.getAddress();
            Integer age = studentInfo.getAge();
            String gender = studentInfo.getGender();
            String name = studentInfo.getName();
            String mobile = studentInfo.getMobile();
            String idCard = studentInfo.getIdCard();
            String hobby = studentInfo.getHobby();
            String etlMobile = PhoneUtils.etlPhone(mobile);
            String etlName = NameUtils.etlName(name);
            String birthDay = DateUtils.dateStr2DateStr(birth);
            String eltAddress = AddressUtils.eltAddress(address);
            String etlIdCard = IdCardUtils.etlIdCard(idCard);
            studentInfoResult.setId(id);
            LoadUtils loadUtils = new LoadUtils();
            RestHighLevelClient client = RestClientHelper.getClient();
            if ((null == etlName || etlName.equals("")) || (eltAddress == null || eltAddress.equals("") || (etlMobile == null || etlMobile.equals(""))
                    || (etlIdCard == null || etlIdCard.equals("")) )) {
                studentInfoResult.setName(etlName);
                studentInfoResult.setAddress(eltAddress);
                studentInfoResult.setMobile(etlMobile);
                studentInfoResult.setIdCard(etlIdCard);
                studentInfoResult.setAge(age);
                studentInfoResult.setGender(gender);
                studentInfoResult.setBirth(birthDay);
                util.start("FAIL", "TRANSFORM", "转换失败，数据为空或不符合格式要求", studentInfo.getId(), studentInfo.toString(), studentInfoResult.toString(), client);
            //    User userInfo = (User) SecurityUtils.getSubject().getPrincipal();
              //  String username = userInfo.getUsername();
               // String email = userInfo.getEmail();

                Email mail = new Email();
                mail.setUser("admin");
                mail.setToemail("oliviajingxiang@163.com");
                mail.setFromemail(from);
                Calendar calendar = Calendar.getInstance();
                Date time = calendar.getTime();
                mail.setCreatetime(time);
                mail.setTitle("数据转换异常邮件");
                mail.setContent("admin"+"你好,id为"+studentInfo.getId()+"的数据转换异常，字段为空或不符合格式要求!原始数据是："+studentInfo.toString());
                mailService.sendMail(mail);
               emailService.save(mail);
            } else {
                if (age == null || age != IdCardUtils.getAge(etlIdCard)){
                    studentInfoResult.setAge(IdCardUtils.getAge(etlIdCard));}
                    else {
                    studentInfoResult.setAge(age);
                }
                if (birthDay == null || birthDay != IdCardUtils.getBirthday(etlIdCard)){
                    studentInfoResult.setBirth(IdCardUtils.getBirthday(etlIdCard));}
                    else {
                    studentInfo.setBirth(birthDay);
                }
                if (gender == null || gender != IdCardUtils.getGender(etlIdCard)){
                    studentInfoResult.setGender(IdCardUtils.getGender(etlIdCard));}
                    else {
                    studentInfo.setGender(gender);
                }
                studentInfoResult.setAddress(eltAddress);
                studentInfoResult.setName(etlName);
                studentInfoResult.setIdCard(etlIdCard);
                studentInfoResult.setMobile(etlMobile);
                studentInfoResult.setHobby(hobby);
                util.start("SUCCESS", "TRANSFORM", "转换成功", studentInfo.getId(), studentInfo.toString(), studentInfoResult.toString(), client);
                loadUtils.start("SUCCESS", "LOAD", "存储成功", studentInfoResult.getName(), studentInfoResult.getAge(), studentInfoResult.getBirth(),
                        studentInfoResult.getGender(), studentInfoResult.getMobile(), studentInfoResult.getAddress(), studentInfoResult.getIdCard(), studentInfo.getId(), client);
                etlRepository.addStudentResult(studentInfoResult);
            }
        }
    }

    @Override
    public List<StudentInfo> extractStudentInfo() throws IOException {
        try {
            List<StudentInfo> allStudent = etlRepository.extractStudentInfo();
            for (StudentInfo studentInfo : allStudent
                    ) {
                // System.out.println(studentInfo);
                // if(studentInfo.) //去重
                RestHighLevelClient client = RestClientHelper.getClient();
                ExtractUtils util = new ExtractUtils();
                util.start("SUCCESS", "EXTRACT", "抽取成功", studentInfo.getId(), studentInfo.toString(), client);
            }
            etlRepository.deleteStudent();
            return allStudent;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
