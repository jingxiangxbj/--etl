package com.jingxiang.datachange.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsClientConfig {
    @Value("${elasticsearch.es_ip}")
    private String ip;

    @Value("${elasticsearch.es_port}")
    private Integer port;


    public RestHighLevelClient getClient(String username, String password) {

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(
                username, password
        ));

        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(ip, port, "http")).
                setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom().setConnectionRequestTimeout(5 * 60 * 1000).setSocketTimeout(5 * 60 * 1000);

                        return httpAsyncClientBuilder.setDefaultRequestConfig(requestConfigBuilder.build()).setDefaultCredentialsProvider(credentialsProvider);
                    }
                }).setMaxRetryTimeoutMillis(90000000);
        RestHighLevelClient highLevelClient = new RestHighLevelClient(restClientBuilder);
        return highLevelClient;

    }

}
