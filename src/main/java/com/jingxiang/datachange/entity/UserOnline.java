package com.jingxiang.datachange.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
@Data
public class UserOnline implements Serializable{
	
	private static final long serialVersionUID = 3828664348416633856L;
	
	// session id
    private String id;
    // 用户id
    private String userId;
    // 用户名称
    private String username;
	// 用户主机地址
    private String host;
    // 用户登录时系统IP
    private String systemHost;
    // 状态
    private String status;

    private long loginCount;
    // session创建时间
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTimestamp;
    // session最后访问时间
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastAccessTime;
    // 超时时间
    private Long timeout;
    // 所在地
    private String location;

}
