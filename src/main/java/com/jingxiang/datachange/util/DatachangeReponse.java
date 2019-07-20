package com.jingxiang.datachange.util;

import org.apache.http.HttpResponse;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class DatachangeReponse extends HashMap<String, Object> {
    private static final long serialVersionUID = -8713837118340960775L;

    public DatachangeReponse success(HttpStatus status) {
        this.put("code", status.OK);
        return this;
    }
    public DatachangeReponse error(HttpStatus status){
        this.put("code",status.INTERNAL_SERVER_ERROR);
        return this;
    }
    public DatachangeReponse code(HttpStatus status){
        this.put("code",status.value());
        return this;
    }
    public DatachangeReponse data(Object data){
        this.put("data",data);
        return this;
    }
    public DatachangeReponse message(String message){
        this.put("message",message);
        return this;
    }
}
