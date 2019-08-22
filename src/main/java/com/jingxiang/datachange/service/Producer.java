package com.jingxiang.datachange.service;

import com.jingxiang.datachange.entity.Tmodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    @Autowired
    private JmsTemplate jmsTemplate;
    private int count = 0;
@Scheduled(fixedRate = 1000)
    public void send(){
jmsTemplate.convertAndSend("queue1",new Tmodel(count++));
    }
}
