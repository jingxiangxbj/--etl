package com.jingxiang.datachange.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.abel533.echarts.Grid;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Position;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Pie;
import com.github.pagehelper.Page;
import com.jingxiang.datachange.config.RestClientHelper;
import com.jingxiang.datachange.entity.BarChar;
import com.jingxiang.datachange.entity.ETLDetailInfo;
import com.jingxiang.datachange.entity.TimeEnum;
import com.jingxiang.datachange.repository.LogInfoRepository;
import com.jingxiang.datachange.service.BaseService;
import com.jingxiang.datachange.service.LogInfoService;
import com.jingxiang.datachange.util.IndexNameUtils;
import com.jingxiang.datachange.util.MonitorDataflowUtils;
import com.jingxiang.datachange.util.QueryRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;


@SuppressWarnings("ALL")
@Service
public class LogInfoServiceImpl implements LogInfoService {




    @Autowired
    private LogInfoRepository logInfoRepository;

//    @Autowired
   // private UserInfoRepository userInfoRepository;

    @Autowired
    private IndexNameUtils indexNameUtils;



    public Option findLogUseForBarChar11111(String stage, String status,String times) throws ParseException, IOException {
        Option option = new Option();
        RestHighLevelClient client = RestClientHelper.getClient();
//        List<List<Map<String,Object>>> lists = new ArrayList<>();
        TimeEnum timeTest = TimeEnum.getTimeEnum(times);
        long currentTimeMillis = System.currentTimeMillis();
      // long currentTimeMillis = 1556964000000l;
        Aggregations aggregations =null;
        if("MONTH".equals(times)){
            String[] indexName = indexNameUtils.getIndexName(MonitorDataflowUtils.getMonthTime(1), currentTimeMillis,stage);
            aggregations = logInfoRepository.findLogDataUseForBarChar(client,indexName,MonitorDataflowUtils.getMonthTime(1), currentTimeMillis,  status, timeTest.getmDateHistogramInterval());
        }else {
            long starttime = currentTimeMillis-timeTest.getmDValue();
            //String[] indexName = indexNameUtils.getIndexName(starttime, currentTimeMillis,stage);
            //aggregations = logInfoRepository.findLogDataUseForBarChar(client,indexName,starttime, currentTimeMillis, code, stage, status, timeTest.getmDateHistogramInterval());
            aggregations = logInfoRepository.findLogDataUseForBarChar(client,new String[]{stage},starttime, currentTimeMillis,  status, timeTest.getmDateHistogramInterval());

        }
        List<String>  listTime =  new ArrayList<>();
        List<Long> countList = new ArrayList<>();
        if (aggregations!=null) {
            ParsedDateHistogram parsedDateHistogram = (ParsedDateHistogram) aggregations.asMap().get("count_time");
            Calendar calendar =Calendar.getInstance();
            option.title("数据"+stage+"阶段").tooltip(Trigger.axis).legend("SUCCESS");
            //横轴为值轴
//                    option.xAxis(new ValueAxis().boundaryGap(0d, 0.01));
            //创建类目轴
            CategoryAxis category = new CategoryAxis();
            //柱状数据
            Bar bar = new Bar("柱状图");

            for (Object o : parsedDateHistogram.getBuckets()) {
                ParsedDateHistogram.ParsedBucket timebucket = (ParsedDateHistogram.ParsedBucket) o;

                DateTime key = (DateTime) timebucket.getKey();
                long timeMillis=key.getMillis();
                calendar.setTimeInMillis(timeMillis);
                String time = MonitorDataflowUtils.getSubTime(calendar.getTime(),timeTest.getmDateHistogramInterval());
                listTime.add(time);
                Aggregations stageaggregations = timebucket.getAggregations();
                if (stageaggregations!=null) {
                    stageaggregations.getAsMap();
                    Terms statusTerms = stageaggregations.get("count_status");
                    List<Map<String, Object>> list = new ArrayList<>();
                    if (statusTerms != null) {
                        if (statusTerms.getBuckets().size() > 0) {
                            for (Terms.Bucket statusbucket : statusTerms.getBuckets()) {
                                Aggregations sumaggregation = statusbucket.getAggregations();
                                Map<String, Object> map = new HashMap<>();
                                map.put("time", time);
//                                map.put("SUCCESS", 0);
//                                map.put("FAIL", 0);
                                String statuss = (String) statusbucket.getKey();
//                                System.out.println(statuss);
                                long count = statusbucket.getDocCount();

                                map.put(statuss, count);
//                                map.put("timeMillis", timeMillis);
//                                if (statusTerms != null) {
//                                    for (Terms.Bucket statusbucket : statusTerms.getBuckets()) {
//                                        Aggregations sumaggregation = statusbucket.getAggregations();
//                                        ParsedSum sumTerms = (ParsedSum) sumaggregation.getAsMap().get("sum_datasize");
//                                        if (sumTerms != null) {
//                                            map.put(status, statusbucket.getDocCount());
//                                countList.add(statusbucket.getDocCount());
                                list.add(map);
                            }
                        } else {
                            Map<String, Object> map = new HashMap<>();
//                            map.put("time", time);
                            map.put(status, 0);
//                            map.put("FAIL", 0);
//                           map.put("status", status);
//                            map.put("timeMillis", timeMillis);
                           map.put("time",time);
//                           map.put(status,0);
                            list.add(map);
//                            countList.add(0l);
                        }
                    }
                    System.out.println(list);
//                    lists.add(list);

                    //循环数据
                    for (Map<String, Object> objectMap : list) {
                        //设置类目
                        category.data(objectMap.get("time"));
                        //类目对应的柱状图
                        bar.data(objectMap.get(status));
                    }
                    Grid grid = new Grid();
                    grid.setLeft(200);
                    option.setGrid(grid);
                    //设置类目轴
                    option.yAxis(category);
                    //设置数据
                    option.series(bar);
                    //图表距离左侧距离设置180，关于grid可以看ECharts的官方文档
//                    option.grid().x(200);
                }

            }
        }
        return option;
    }






    @Override
    public Option findLogUseForBarChar(String stage, String status,String times) throws ParseException, IOException {
        RestHighLevelClient client = RestClientHelper.getClient();
        TimeEnum timeTest = TimeEnum.getTimeEnum(times);
        //long currentTimeMillis = 1559471453305l;
        long currentTimeMillis = System.currentTimeMillis();
        Aggregations aggregations =null;
        if("MONTH".equals(times)){
            String[] indexName = indexNameUtils.getIndexName(MonitorDataflowUtils.getMonthTime(1), currentTimeMillis,stage);
            aggregations = logInfoRepository.findLogDataUseForBarChar(client,indexName,MonitorDataflowUtils.getMonthTime(1), currentTimeMillis,  status, timeTest.getmDateHistogramInterval());
        }else {
            long starttime = currentTimeMillis-timeTest.getmDValue();
                aggregations = logInfoRepository.findLogDataUseForBarChar(client,new String[]{stage},starttime, currentTimeMillis,  status, timeTest.getmDateHistogramInterval());

        }
        List<String>  listTime =  new ArrayList<>();
        List<Long> countList = new ArrayList<>();
        Option option = new Option();
        CategoryAxis category = new CategoryAxis();
        //柱状数据
        Bar bar1 = new Bar("success");
        Bar bar2 = new Bar("fail");
        List<Map<String, Object>> list = new ArrayList<>();
        if (aggregations!=null) {
            ParsedDateHistogram parsedDateHistogram = (ParsedDateHistogram) aggregations.asMap().get("count_time");
            Calendar calendar =Calendar.getInstance();
            option.title("数据转换阶段").tooltip(Trigger.axis);
            category.axisLabel().interval(0);
            category.axisLabel().rotate(45);
//            option.legend("success", "fail");
            option.yAxis(new ValueAxis().boundaryGap(0d, 0.01));
            for (Object o : parsedDateHistogram.getBuckets()) {
                ParsedDateHistogram.ParsedBucket timebucket = (ParsedDateHistogram.ParsedBucket) o;
                DateTime key = (DateTime) timebucket.getKey();
                long timeMillis=key.getMillis();
                calendar.setTimeInMillis(timeMillis);
                String time = MonitorDataflowUtils.getSubTime(calendar.getTime(),timeTest.getmDateHistogramInterval());
                listTime.add(time);
                Aggregations stageaggregations = timebucket.getAggregations();
                if (stageaggregations!=null) {
                    stageaggregations.getAsMap();
                    Terms statusTerms = stageaggregations.get("count_status");
                    if (statusTerms != null) {
                        if (statusTerms.getBuckets().size() > 0) {
                            for (Terms.Bucket statusbucket : statusTerms.getBuckets()) {
                                Aggregations sumaggregation = statusbucket.getAggregations();
                                Map<String, Object> map = new HashMap<>();
                                map.put("time", time);
                                map.put("SUCCESS", 0);
                                map.put("FAIL", 0);
                                String statuss = (String) statusbucket.getKey();
                                long count = statusbucket.getDocCount();
                                map.put(statuss, count);
                                list.add(map);
                            }
                        } else {
                            Map<String, Object> map = new HashMap<>();
                            map.put("time",time);
                            map.put("SUCCESS", 0);
                            map.put("FAIL", 0);
                            list.add(map);
                        }
                    }
                }

            }
            for (Map<String, Object> objectMap : list) {
                //设置类目
                category.data(objectMap.get("time"));

                //类目对应的柱状图
                bar1.data(objectMap.get("SUCCESS"));
                bar2.data(objectMap.get("FAIL"));
            }
            //设置类目轴
            option.xAxis(category);
            //设置数据
            option.series(bar1,bar2);
            //图表距离左侧距离设置180，关于grid可以看ECharts的官方文档
            option.grid().y(100);
                   option.grid().bottom("20%");

        }
        return option;
    }



    public List<ETLDetailInfo> findLogDateByCondition(ETLDetailInfo etlDetailInfo) throws IOException {
        RestHighLevelClient client = RestClientHelper.getClient();
        long timeMillis = 1556964000000l;
         //long endTime = System.currentTimeMillis();
        String stage = etlDetailInfo.getStage();
        String status = etlDetailInfo.getStatus();
//        if ("WEEK".equals(timeDimension) || "MONTH".equals(timeDimension)) {
//            endTime = timeMillis + (24 * 3600000);
//        } else if ("DAY".equals(timeDimension) || "SIXHOUR".equals(timeDimension)) {
//            endTime = timeMillis + 3600000;
//        } else {
          long  endTime = timeMillis + 3600000;
//        }
       // String[] indexName = indexNameUtils.getIndexName(timeMillis, endTime,stage);
        List<ETLDetailInfo> etlDetailInfos = logInfoRepository.getLogDataByCondition(client, new String[]{stage}, status, timeMillis, endTime);

//        Calendar calendar = Calendar.getInstance();
//        for (etlInfo log:etlDetailInfos
//                ) {
//            long logtime = log.getCallTime();
//            calendar.setTime(logtime);
//            calendar.add(Calendar.HOUR,8);
//            Date time = calendar.getTime();
//            log.setCallTime(time);
//        }
        return etlDetailInfos;
    }



//    @Override
//    public Map<String, etlInfo> findErrorLogData() throws IOException {
//        RestHighLevelClient client = RestClientHelper.getClient();
//String[] stages = {"extract","transform","load"};
//        for (String stage:stages
//             ) {
//            String[] indexName = indexNameUtils.getIndexName(Calendar.getInstance().getTimeInMillis() - 1800000, Calendar.getInstance().getTimeInMillis(),stage);
//            TimeEnum timeTest = TimeEnum.getTimeEnum("HALFHOUR");
//
//            long currentTimeMillis = System.currentTimeMillis();
//            long starttime = currentTimeMillis-timeTest.getmDValue();
//
//            Aggregations aggregations = logInfoRepository.findErrorLogDate(client,indexName, starttime, currentTimeMillis, "status.keyword");
//            if (aggregations!=null) {
//                Terms terms = aggregations.get("count_status");
//                if (terms!=null) {
//                    for (Terms.Bucket bucket3 : terms.getBuckets()
//                            ) {
//                        String status = (String) bucket3.getKey();
//                        ParsedSum sumData = (ParsedSum) bucket3.getAggregations().getAsMap().get("sum_data");
//                        double value = sumData.getValue();
//                    }
//                }
//            }
//        }
//
//        return null;
//
//    }
//

}
