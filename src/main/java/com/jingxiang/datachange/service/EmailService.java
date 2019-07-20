package com.jingxiang.datachange.service;

import com.jingxiang.datachange.entity.Email;

import java.util.List;

public interface EmailService extends IService<Email>{
List<Email> findAllEmail(Email email);
}
