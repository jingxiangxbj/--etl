package com.jingxiang.datachange.repository;

import com.jingxiang.datachange.entity.ETLDetailInfo;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;

import java.util.List;

public interface LogInfoRepository {


  Aggregations findErrorLogDate(RestHighLevelClient client, String[] index, long starttime, long endtime, String status);

  Aggregations findLogDataUseForBarChar(RestHighLevelClient client, String[] index, long starttime, long endtime, String status, DateHistogramInterval dateHistogramInterval);


  List<ETLDetailInfo> getLogDataByCondition(RestHighLevelClient client, String[] index, String status, long startTime, long endTime);

}
