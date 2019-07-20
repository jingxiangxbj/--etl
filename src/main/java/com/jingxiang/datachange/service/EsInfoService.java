package com.jingxiang.datachange.service;

import com.github.abel533.echarts.Option;
import com.jingxiang.datachange.entity.CusterInfo;
import com.jingxiang.datachange.entity.StudentResultExt;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;


public interface EsInfoService {


    CusterInfo getClusterInfo() throws IOException;



    List<Map<String, Object>> getIndexStore() throws IOException;


    Option getIndexDocChange(String times) throws IOException, ParseException;


}
