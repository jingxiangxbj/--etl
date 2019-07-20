package com.jingxiang.datachange.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.jingxiang.datachange.config.ExportConfig;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
@Data
public class StudentInfo implements Serializable {
    private static final long serialVersionUID = -4852732617765810959L;
    /**
     * 学生ID
     */
    @Id
    private Integer id;
    /**
     * 姓名
     */
    @ExportConfig(value = "姓名")
    private String name;
    /**
     * 年龄
     */
    @ExportConfig(value = "年龄")
    private Integer age;
    /**
     * 出生日期
     */
    @ExportConfig(value = "出生日期")
    private String birth;
    /**
     * 性别
     */
    @ExportConfig(value = "性别")
    private String gender;
    /**
     * 电话
     */
    @ExportConfig(value = "电话")
    private String mobile;
    /**
     * 地址
     */
    @ExportConfig(value = "地址")
    private String address;
    /**
     * 身份证号
     */
    @ExportConfig(value = "身份证号")
    private String idCard;
    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    /**
     * 爱好
     */
    @ExportConfig(value = "爱好")
    private String hobby;

    @Override
    public String toString() {
        return "StudentInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", birth=" + birth +
                ", gender='" + gender + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", idCard='" + idCard + '\'' +
                ", hobby='" + hobby + '\'' +
                '}';
    }
}
