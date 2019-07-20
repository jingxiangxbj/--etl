package com.jingxiang.datachange.service;

import com.github.abel533.echarts.Option;
import com.github.pagehelper.Page;
import com.jingxiang.datachange.entity.ETLDetailInfo;
import com.jingxiang.datachange.util.QueryRequest;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;


public interface LogInfoService {



    Option findLogUseForBarChar(String stage, String status, String times) throws ParseException, IOException;

    List<ETLDetailInfo> findLogDateByCondition(ETLDetailInfo etlDetailInfo) throws IOException;


  //  Map<String,etlInfo> findErrorLogData() throws IOException;



}
