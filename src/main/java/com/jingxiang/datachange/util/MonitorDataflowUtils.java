package com.jingxiang.datachange.util;

import org.apache.http.HttpEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class MonitorDataflowUtils {


    public static String getUTCDate(Long timeInMillis) {
        Date date = new Date(timeInMillis);
        String data_format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        DateFormat format = new SimpleDateFormat(data_format);
        TimeZone srcTimeZone = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone destTimeZone = TimeZone.getTimeZone("GMT");
        String s = MonitorDataflowUtils.dateTransformBetweenTimeZone(date, format, srcTimeZone, destTimeZone);
        return s;
    }

    public static String gettime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String time = sdf.format(date);
        return time;
    }

    /**
     * 功能描述: 获取当前时间的前一个月时间的毫秒数
     *
     * @param: []
     * @return: long
     */
    public static long getMonthTime(int date) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendarCurrent = Calendar.getInstance();
        calendar.set(calendarCurrent.get(Calendar.YEAR), calendarCurrent.get(Calendar.MONTH) - date, calendarCurrent.get(Calendar.DAY_OF_MONTH));
        return calendar.getTimeInMillis();
    }

    /**
     * @param: [timestr, dateHistogramInterval]
     * @return: java.lang.String
     * @Description: 根据时间聚合的类型（SixHour、Week,Month等）截取时间得到不同格式的返回
     */
    private static SimpleDateFormat monthDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dayDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
    private static SimpleDateFormat hourDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getSubTime(Date timestr, DateHistogramInterval dateHistogramInterval) {
        String time = "";
        if (dateHistogramInterval == DateHistogramInterval.MONTH) {
            time = monthDateFormat.format(timestr);

        } else if (dateHistogramInterval == DateHistogramInterval.DAY) {
            time = dayDateFormat.format(timestr);
        } else {
            time = hourDateFormat.format(timestr);
        }
        return time;
    }
public static String getDay(Date date){
    String subdate = monthDateFormat.format(date);
    return subdate;
}
    /**
     * @param: [arr1, arr2]
     * @return: java.lang.String[]
     * @Description: 求数组的交集
     */
    public static String[] intersect(String[] arr1, String[] arr2) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        LinkedList<String> list = new LinkedList<String>();
        for (String str : arr1) {
            if (!map.containsKey(str)) {
                map.put(str, Boolean.FALSE);
            }
        }
        for (String str : arr2) {
            if (map.containsKey(str)) {
                map.put(str, Boolean.TRUE);
            }
        }
        for (Map.Entry<String, Boolean> e : map.entrySet()) {
            if (e.getValue().equals(Boolean.TRUE)) {
                list.add(e.getKey());
            }
        }
        String[] result = {};
        return list.toArray(result);
    }

    /**
     * @param: [response]
     * @return: java.lang.String
     * @Description: 从response响应中取得数据返回
     */
    public static String getJsonObject(Response response) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        StringBuilder stringBuilder = new StringBuilder();
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                BufferedReader bufferedReader = null;
                try {
                    bufferedReader = new BufferedReader(
                            new InputStreamReader(entity.getContent(), "UTF-8"), entity.getContent().available());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String s = stringBuilder.toString();
                //JSONObject jsonObject = JSON.parseObject(stringBuilder.toString());
                return s;
            }
        }
        return null;
    }

    public static String dateTransformBetweenTimeZone(Date sourceDate, DateFormat formatter,
                                                      TimeZone sourceTimeZone, TimeZone targetTimeZone) {
        Long targetTime = sourceDate.getTime() - sourceTimeZone.getRawOffset() + targetTimeZone.getRawOffset();
        return MonitorDataflowUtils.getTime(new Date(targetTime), formatter);
    }

    public static String getTime(Date date, DateFormat formatter) {
        return formatter.format(date);
    }


}
