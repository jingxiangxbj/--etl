package com.jingxiang.datachange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.spring.annotation.MapperScan;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@ComponentScan
@EnableScheduling
@MapperScan(basePackages = "com.jingxiang.datachange.mapper")
public class DatachangeApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DatachangeApplication.class);
    }

    public static void main(String[] args) {

        SpringApplication.run(DatachangeApplication.class, args);


    }
}
