package com.jingxiang.datachange.service;

import com.jingxiang.datachange.entity.Tmodel;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    @JmsListener(destination = "queue1")
    public void consumer(Tmodel content){
        System.out.println("recive message from queue1 [" + content + "]");
    }
}
