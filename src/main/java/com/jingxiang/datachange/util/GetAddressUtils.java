package com.jingxiang.datachange.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class GetAddressUtils {
    private GetAddressUtils() {
    }

    private static Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static String getCityNameByTaoBaoAPI(String ip) {
        String url = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip;
        String cityName = "";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        try {
            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                String strResult = EntityUtils.toString(response.getEntity());
                try {
                    JSONObject jsonResult = JSON.parseObject(strResult);
                    System.out.println(JSON.toJSONString(jsonResult, true));
                    JSONObject dataJson = jsonResult.getJSONObject("data");
                    cityName = dataJson.getString("city");
                    System.out.println(JSON.toJSONString(jsonResult, true));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;
    }
}
