package com.jingxiang.datachange.repository;

import org.elasticsearch.client.Response;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;

import java.io.IOException;


public interface EsInfoRepository {


    public Response getClusterStats() throws IOException;

    public Response getClusterHealth() throws IOException;



    public Response getIndexStore() throws IOException;



    Aggregations getIndexDocsChange(String[] indices, long startTime, long endTime, DateHistogramInterval dateHistogramInterval) throws IOException;

}
