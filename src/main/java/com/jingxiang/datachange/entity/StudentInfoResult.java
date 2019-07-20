package com.jingxiang.datachange.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.jingxiang.datachange.config.ExportConfig;

import java.io.Serializable;
import java.util.Date;

@lombok.Data
public class StudentInfoResult implements Serializable {
    private static final long serialVersionUID = -4852732617765810959L;
    private Integer id;
    @ExportConfig(value = "姓名")
    private String name;
    @ExportConfig(value = "年龄")
    private Integer age;
    @ExportConfig(value = "出生日期")
    private String birth;
    @ExportConfig(value = "性别")
    private String gender;
    @ExportConfig(value = "手机号")
    private String mobile;
    @ExportConfig(value = "地址")
    private String address;
    @ExportConfig(value = "身份证号")
    private String idCard;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    @ExportConfig(value = "爱好")
    private String hobby;


}
