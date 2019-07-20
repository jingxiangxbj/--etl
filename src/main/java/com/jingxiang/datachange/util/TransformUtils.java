package com.jingxiang.datachange.util;

import com.jingxiang.datachange.config.EsClientConfig;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName InterfaceLogInfo
 * @Description TODO
 * @Date 2018-12-04 上午 09:47
 * @Version 1.0
 **/
@Component
public class TransformUtils {

    @Autowired
    private EsClientConfig esClientConfig;
    private Long callTime;
    private String status;
    private String detail;
    private int id;
    private String studentInfo;
    private String studentInfoResult;
    private String stage;

    public void start(String status, String stage, String detail, int id, String studentInfo, String studentInfoResult, RestHighLevelClient client) throws IOException {
        callTime = Calendar.getInstance().getTimeInMillis();
        this.id = id;
        this.studentInfo = studentInfo;
        this.studentInfoResult = studentInfoResult;
        this.status = status;
        this.stage = stage;
        this.detail = detail;
//        String indexName = getIndexName(callTime);
//        createIndex(client,indexName);
          printLog(client);
    }

    public void success( RestHighLevelClient client) throws IOException {
        this.status = "SUCCESS";
        this.detail = "访问成功";
        printLog(client);
    }

    public void fail(String detail, RestHighLevelClient client) throws IOException {
        this.status = "FAIL";
        this.detail = detail;
        printLog(client);
    }

    /**
     * 功能描述: 根据时间毫秒数来判断数据是属于几月份的索引
     *
     * @param: [millis]
     * @return: java.lang.String
     * @date: 2018-12-11 下午 07:17
     */
    private String getIndexName(Long millis) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String indexName = "transform-" + format.format(millis);
        return indexName;
    }

    /**
     * 功能描述: 创建索引
     *
     * @param: [client, indexName]
     * @return: boolean
     * @date: 2018-12-11 下午 07:18
     */
    private boolean createIndex(RestHighLevelClient client, String indexName) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.settings(Settings.builder()
                .put("index.number_of_shards", 5)
                .put("index.number_of_replicas", 1)
        );
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("doc");
            {
                builder.startObject("properties");
                {

                    builder.startObject("detail");
                    {
                        builder.field("type", "keyword");
                    }
                    builder.endObject();
                    builder.startObject("callTime");
                    {
                        builder.field("type", "date");
                    }
                    builder.endObject();
                    builder.startObject("status");
                    {
                        builder.field("type", "keyword");
                    }
                    builder.endObject();
                    builder.startObject("stage");
                    {
                        builder.field("type", "keyword");
                    }
                    builder.endObject();
                    builder.startObject("studentInfo");
                    {
                        builder.field("type", "keyword");
                    }
                    builder.endObject();
                    builder.startObject("studentInfoResult");
                    {
                        builder.field("type", "keyword");
                    }
                    builder.endObject();
                    builder.startObject("id");
                    {
                        builder.field("type", "integer");
                    }
                    builder.endObject();
                    builder.startObject("uuid");
                    {
                        builder.field("type", "keyword");
                    }
                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        request.mapping("doc", builder);
        CreateIndexResponse createIndexResponse = client.indices().create(request);

        return createIndexResponse.isAcknowledged();
    }

    /**
     * 功能描述: 向索引种插入数据
     *
     * @param: [client]
     * @return: void
     * @date: 2018-12-11 下午 07:19
     */
    private void printLog(RestHighLevelClient client) throws IOException {
        try {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("callTime", callTime);
            map.put("stage", stage);
            map.put("status", status);
            map.put("detail", detail);
            map.put("id", id);
            map.put("studentInfo", studentInfo);
            map.put("studentInfoResult", studentInfoResult);
            String indexName = this.getIndexName(callTime);
            Response response = client.getLowLevelClient().performRequest("HEAD", "/transform");
//        int statusCode = response.getStatusLine().getStatusCode();
//        if (statusCode == 404) {
//            //触发创建索引的逻辑
//            boolean statu = this.createIndex(client, "transform");
//            if (statu == true) {
//                Indices indices = new Indices();
//                indices.setOriginal_name(indexName);
//                indices.setPresent_name(indexName);
//              //  indicesService.add(indices);
//            }
//        }
            IndexRequest indexRequest = new IndexRequest("transform", "doc").source(map);
            IndexResponse indexResponse = client.index(indexRequest);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
          //  client.close();
        }
    }


}
