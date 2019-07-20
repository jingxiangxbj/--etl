package com.jingxiang.datachange.repository.impl;

import com.alibaba.fastjson.JSON;
import com.jingxiang.datachange.entity.ETLDetailInfo;
import com.jingxiang.datachange.repository.LogInfoRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.ExtendedBounds;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LogInfoRepositoryImpl implements LogInfoRepository {


    @Override
    public Aggregations findErrorLogDate(RestHighLevelClient client,String[] index, long starttime, long endtime, String status){

        SearchRequest searchRequest = new SearchRequest(index).types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.size(0);

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        queryBuilder.must(QueryBuilders.rangeQuery("callTime").gte(starttime).lte(endtime));

        searchSourceBuilder.query(queryBuilder)
                .aggregation(AggregationBuilders.terms("count_status").field(status));//不同状态数量

        Aggregations aggregations=null;
        try {
            SearchResponse searchResponse = client.search(searchRequest.source(searchSourceBuilder));
            client.close();
            aggregations= searchResponse.getAggregations();
        } catch (IOException e) {
            String ename =e.getClass().getName();
            if (ename.equals("java.net.ConnectException")){

            }
        }
        return aggregations;
    }


    @Override
    public List<ETLDetailInfo> getLogDataByCondition(RestHighLevelClient client, String[] index, String status, long startTime, long endTime) {
        BoolQueryBuilder queryBuilder =QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("status",status)).must(QueryBuilders.rangeQuery("callTime").gte(startTime).lte(endTime));
        if(!"ALL".equals(status)){
            queryBuilder.must(QueryBuilders.matchQuery("status", status));
        }
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        searchRequest.types("doc");
        searchRequest.source(sourceBuilder);
        List<ETLDetailInfo> list = new ArrayList();
        try {
            SearchResponse searchResponse = client.search(searchRequest);
            for (SearchHit searchHit : searchResponse.getHits()) {
                String source = searchHit.getSourceAsString();
                ETLDetailInfo etlDetailInfo = JSON.parseObject(source, ETLDetailInfo.class);
                list.add(etlDetailInfo);
            }
          //  client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  list;
    }





    @Override
    public Aggregations findLogDataUseForBarChar(RestHighLevelClient client,String[] index, long starttime,long endtime,String status,DateHistogramInterval dateHistogramInterval) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("callTime").gte(starttime).lte(endtime));
        if (!"ALL".equals(status)){
            queryBuilder.must(QueryBuilders.matchQuery("status", status));
        }
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.size(0);
        sourceBuilder.query(queryBuilder);
        sourceBuilder.aggregation(AggregationBuilders.dateHistogram("count_time").field("callTime").dateHistogramInterval(dateHistogramInterval).format("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .minDocCount(0).extendedBounds(new ExtendedBounds(starttime,endtime))
                        .subAggregation(AggregationBuilders.terms("count_status").field("status.keyword")));
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        searchRequest.types("doc");
        searchRequest.source(sourceBuilder);
        Aggregations aggregations=null;
        try {
            SearchResponse searchResponse = client.search(searchRequest);
            aggregations=searchResponse.getAggregations();
           // client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return aggregations;
    }

}
