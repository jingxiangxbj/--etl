package com.jingxiang.datachange.service.impl;

import com.jingxiang.datachange.entity.Email;
import com.jingxiang.datachange.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMail(Email mail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mail.getFromemail());
        simpleMailMessage.setTo(mail.getToemail());
        simpleMailMessage.setSubject(mail.getTitle());
        simpleMailMessage.setText(mail.getContent());
        try {
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
