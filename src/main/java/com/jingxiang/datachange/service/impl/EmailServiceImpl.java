package com.jingxiang.datachange.service.impl;

import com.jingxiang.datachange.entity.Email;
import com.jingxiang.datachange.service.BaseService;
import com.jingxiang.datachange.service.EmailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailServiceImpl extends BaseService<Email> implements EmailService {

    @Override
    public List<Email> findAllEmail(Email email) {
        try {
            Example example = new Example(Email.class);
            if (StringUtils.isNotBlank(email.getUser())) {
                example.createCriteria().andCondition("email_name=", email.getUser());
            }
            example.setOrderByClause("createtime DESC");//SELECT  id,user,toEmail,fromEmail,title,content,createtime  FROM email   order by create_time LIMIT 10
            return this.selectByExample(example);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }
}
