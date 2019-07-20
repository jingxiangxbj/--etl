package com.jingxiang.datachange.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestClientHelper {

    private static RestHighLevelClient client;

    public static RestHighLevelClient getClient(){
        if(client == null){
            synchronized (RestClientHelper.class){
                if(client == null){
                    client = new RestHighLevelClient(RestClient.builder(new HttpHost(Constant.es_ip,Constant.es_port,"http")));
                }
            }
        }
        return client;
    }

}
