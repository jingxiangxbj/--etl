package com.jingxiang.datachange.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by xslde on 2018/7/21
 * QQ第三方登陆参数类
 */
@Data
public class QQProperties {
@Value("${oauth.qq.client_id}")
    private String client_id;
@Value("${oauth.qq.client_secret}")
    private String client_secret;
@Value("${oauth.qq.redirect_uri")
    private String redirect_uri;
@Value("${oauth.qq.code_callback_uri}")
    private String code_callback_uri;
@Value("${oauth.qq.access_token_callback_uri}")
    private String access_token_callback_uri;
@Value("${oauth.qq.openid_callback_uri}")
    private String openid_callback_uri;
@Value("${oauth.qq.user_info_callback_uri}")
    private String user_info_callback_uri;

}
