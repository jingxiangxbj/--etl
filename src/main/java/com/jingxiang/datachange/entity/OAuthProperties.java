package com.jingxiang.datachange.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
//对应application.yml中，oauth下参数
@ConfigurationProperties(prefix = "oauth")
@Data
public class OAuthProperties {
    //获取applicaiton.yml下qq下所有的参数
    private QQProperties qq = new QQProperties();

}
