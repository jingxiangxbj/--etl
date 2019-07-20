package com.jingxiang.datachange.repository.impl;/**
 * Created by Administrator on 2018/11/22.
 */

import com.jingxiang.datachange.config.RestClientHelper;
import com.jingxiang.datachange.repository.EsInfoRepository;
import com.jingxiang.datachange.util.IndexNameUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.ExtendedBounds;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;


@Repository
public class EsInfoRepositoryImpl implements EsInfoRepository {

    @Autowired
    private IndexNameUtils indexNameUtils;

    @Override
    public Response getClusterStats() throws IOException {
        RestHighLevelClient client = RestClientHelper.getClient();
        Response response = null;
        try {
            response = client.getLowLevelClient().performRequest("GET", "/_cluster/stats?human=true");
        } catch (IOException e) {
            e.printStackTrace();
        }
      //  client.close();
        return response;
    }

    @Override
    public Response getClusterHealth() throws IOException {
        RestHighLevelClient client = RestClientHelper.getClient();
        Response response = null;
        try {
            response = client.getLowLevelClient().performRequest("GET", "/_cluster/health?pretty");
        } catch (IOException e) {
            e.printStackTrace();
        }
   //     client.close();
        return response;
    }



    @Override
    public Response getIndexStore() throws IOException {
        RestHighLevelClient client = RestClientHelper.getClient();
        Response response = null;
        try {
            List<String> indexList = indexNameUtils.getIndexList(client);
            Object[] indices = indexList.toArray();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < indices.length; i++) {
                stringBuilder.append(indices[i] + ",");
            }
            String s = stringBuilder.toString();
            String index = s.substring(0, s.length() - 1);
            String urlIndexInfo = "/_cat/indices?format=json&h=status,index,pri,docs.count,docs.deleted,store.size&index=" + index;
            response = client.getLowLevelClient().performRequest("GET", urlIndexInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
      //  client.close();
        return response;
    }

    /**
     * 功能描述: 获取当前一段时间索引数据历史变化情况
     *
     * @auther: xiongbaojing
     * @date: 2018-11-22 下午 01:25
     */
    @Override
    public Aggregations getIndexDocsChange( String[] indices, long startTime, long endTime, DateHistogramInterval dateHistogramInterval) throws IOException {
        RestHighLevelClient client = RestClientHelper.getClient();
        BoolQueryBuilder queryBuilders = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("callTime").gte(startTime).lte(endTime));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(0);
        searchSourceBuilder.query(queryBuilders);
        searchSourceBuilder.aggregation(AggregationBuilders.dateHistogram("count_time").field("callTime").dateHistogramInterval(dateHistogramInterval).format("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").minDocCount(0).extendedBounds(new ExtendedBounds(startTime, endTime)));
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(indices);
        searchRequest.types("doc");
        searchRequest.source(searchSourceBuilder);
        Aggregations aggregations = null;
        try {
            SearchResponse searchResponse = client.search(searchRequest);
            aggregations = searchResponse.getAggregations();
        } catch (IOException e) {
            e.printStackTrace();
        }
     //   client.close();
        return aggregations;
    }

}