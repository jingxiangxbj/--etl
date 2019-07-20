package com.jingxiang.datachange.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jingxiang.datachange.config.RestClientHelper;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class IndexNameUtils {

    public String[] getIndexName( long starttime, long endtime,String stage) throws IOException {
        String[] str = {};
        List<String> list = new ArrayList();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM");
        Calendar calendarSt = Calendar.getInstance();
        Calendar calendarEt = Calendar.getInstance();
        long tmp = 0;
        if (endtime < starttime) {
            tmp = starttime;
            starttime = endtime;
            endtime = tmp;
        }
        calendarSt.setTimeInMillis(starttime);
        calendarSt.set(Calendar.DAY_OF_MONTH, 1);
        calendarSt.set(Calendar.HOUR_OF_DAY, 0);
        calendarSt.set(Calendar.MINUTE, 0);
        calendarSt.set(Calendar.SECOND, 0);
        calendarSt.set(Calendar.MILLISECOND, 0);

        calendarEt.setTimeInMillis(endtime);
        calendarEt.set(Calendar.DAY_OF_MONTH, 1);
        calendarEt.set(Calendar.HOUR_OF_DAY, 0);
        calendarEt.set(Calendar.MINUTE, 0);
        calendarEt.set(Calendar.SECOND, 0);
        calendarEt.set(Calendar.MILLISECOND, 0);
        while (calendarSt.getTimeInMillis() <= calendarEt.getTimeInMillis()) {//判断是否到结束日期
            String str2 = "log-dataflow-"+stage + simpleDateFormat.format(calendarSt.getTime());
            list.add(str2);
            calendarSt.add(Calendar.MONTH, 1);//进行当前日期月份加1
        }
        String[] dValueIndexName = list.toArray(str);
        RestHighLevelClient client = RestClientHelper.getClient();
        List<String> indexList = getIndexList(client);
        String[] arr = {};
        String[] array = indexList.toArray(arr);
        String[] indexName = MonitorDataflowUtils.intersect(array, dValueIndexName);
        return indexName;
    }


    public List<String> getIndexList(RestHighLevelClient client) throws IOException {
        List<String> list = new ArrayList<>();
        try {
            Response response = client.getLowLevelClient().performRequest("GET", "_mapping");
            String res = MonitorDataflowUtils.getJsonObject(response);
            JSONObject jsonObject = JSON.parseObject(res);
            Set<String> keys = jsonObject.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                if (!next.substring(0, 1).equals(".")) {
                    list.add(next);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //client.close();
        return list;
    }
}
