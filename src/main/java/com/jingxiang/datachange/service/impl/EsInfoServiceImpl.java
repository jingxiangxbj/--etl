package com.jingxiang.datachange.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Line;
import com.jingxiang.datachange.entity.CusterInfo;
import com.jingxiang.datachange.entity.StudentResultExt;
import com.jingxiang.datachange.entity.TimeEnum;
import com.jingxiang.datachange.mapper.EtlMapper;
import com.jingxiang.datachange.repository.EsInfoRepository;
import com.jingxiang.datachange.service.EsInfoService;
import com.jingxiang.datachange.util.IndexNameUtils;
import com.jingxiang.datachange.util.MonitorDataflowUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class EsInfoServiceImpl implements EsInfoService {
    @Autowired
    private EsInfoRepository esInfoRepository;
    @Autowired
    private IndexNameUtils indexNameUtils;

    public CusterInfo getClusterInfo() throws IOException {
//        Map<String, Object> clusterMap = new LinkedHashMap<>();
        CusterInfo custerInfo = new CusterInfo();
        Response responseStats = esInfoRepository.getClusterStats();
        String jsonObjectStats = MonitorDataflowUtils.getJsonObject(responseStats);
        JSONObject jsonObject = JSON.parseObject(jsonObjectStats);
        JSONObject nodes = jsonObject.getJSONObject("nodes");
        JSONObject osMem = nodes.getJSONObject("os").getJSONObject("mem");
        JSONObject cpu = nodes.getJSONObject("process").getJSONObject("cpu");
        JSONObject clusterMem = nodes.getJSONObject("jvm").getJSONObject("mem");
        JSONObject clusterIndices = jsonObject.getJSONObject("indices");
        JSONObject indicesDocs = clusterIndices.getJSONObject("docs");
        Response responseHealth = esInfoRepository.getClusterHealth();
        String jsonObjectHealth = MonitorDataflowUtils.getJsonObject(responseHealth);
        JSONObject health = JSON.parseObject(jsonObjectHealth);
        custerInfo.setClusterName((String) health.get("cluster_name"));
        custerInfo.setNodesCount((Integer) health.get("number_of_nodes"));
        custerInfo.setIndicesCount((Integer) clusterIndices.get("count"));
        custerInfo.setClusterStatus((String) health.get("status"));
        custerInfo.setOsmemUsed((String) osMem.get("used"));
        custerInfo.setOsmemTotal((String) osMem.get("total"));
        custerInfo.setOsmemFree((String) osMem.get("free"));
        custerInfo.setOsmemFreePercent((Integer) osMem.get("free_percent"));
        custerInfo.setOsmemUsedPercent((Integer) osMem.get("used_percent"));
        custerInfo.setEsmemUsed((String) clusterMem.get("heap_used"));
        custerInfo.setEsmemTotal((String) clusterMem.get("heap_max"));
        custerInfo.setCpuPercent((Integer) cpu.get("percent"));
        custerInfo.setDocsCount((Integer) indicesDocs.get("count"));
//        System.out.println(custerInfo);
        return custerInfo;
    }


    public List<Map<String, Object>> getIndexStore() throws IOException {
        List<Map<String, Object>> indexInfo = new ArrayList<>();
        Response responseStats = esInfoRepository.getIndexStore();
        String indicesStats = MonitorDataflowUtils.getJsonObject(responseStats);
        JSONArray jsonArrayIndices = JSON.parseArray(indicesStats);
        for (int i = 0; i < jsonArrayIndices.size(); i++) {
            Map<String, Object> indexs = new LinkedHashMap<>();
            JSONObject jsonIndex = jsonArrayIndices.getJSONObject(i);
            indexs.put("index", jsonIndex.get("index"));
            indexs.put("status", jsonIndex.get("status"));
            indexs.put("pri", jsonIndex.get("pri"));
            indexs.put("docsCount", jsonIndex.get("docs.count"));
            indexs.put("docsDeleted", jsonIndex.get("docs.deleted"));
            indexs.put("storeSize", jsonIndex.get("store.size"));
            indexInfo.add(indexs);
        }
        return indexInfo;
    }


    @Override
    public Option getIndexDocChange(String times) throws IOException, ParseException {
        List<Map<String, Object>> lists = new ArrayList<>();
        TimeEnum timeTest = TimeEnum.getTimeEnum(times);
        Aggregations aggregations = null;
       // long currentTimeMillis = 1559471453305l;
        long currentTimeMillis = System.currentTimeMillis();
        long dValue = timeTest.getmDValue();
        if ("HALFHOUR".equals(times) || "SIXHOUR".equals(times) || "DAY".equals(times) || "WEEK".equals(times)) {
            long starttime = currentTimeMillis - dValue;
            // String[] indexName = indexNameUtils.getIndexName(xname, xpassword, starttime, currentTimeMillis);
            aggregations = esInfoRepository.getIndexDocsChange(new String[]{"transform"}, starttime, currentTimeMillis, timeTest.getmDateHistogramInterval());
        } else {
            long starttime = currentTimeMillis - timeTest.getmDValue();
            //  String[] indexName = indexNameUtils.getIndexName(xname, xpassword, MonitorDataflowUtils.getMonthTime((int) dValue), currentTimeMillis);
            aggregations = esInfoRepository.getIndexDocsChange(new String[]{"transform"}, starttime, currentTimeMillis, timeTest.getmDateHistogramInterval());
        }
        ParsedDateHistogram parsedDateHistogram = (ParsedDateHistogram) aggregations.getAsMap().get("count_time");
        Calendar calendar = Calendar.getInstance();
        for (Histogram.Bucket timeBucket : parsedDateHistogram.getBuckets()) {
            DateTime key = (DateTime) timeBucket.getKey();
            calendar.setTimeInMillis(key.getMillis());
            String time2 = MonitorDataflowUtils.getSubTime(calendar.getTime(), timeTest.getmDateHistogramInterval());
            //   long timeMillis = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(timeBucket.getKeyAsString()).getTime();
            long docCount = timeBucket.getDocCount();
            Map<String, Object> map = new HashMap<>();
            map.put("time", time2);
            map.put("docCount", docCount);
            lists.add(map);
        }
        Option option = new Option();
        option.title("索引数据变化情况");
        option.tooltip().trigger(Trigger.axis);
        option.legend("转换");
        option.toolbox().show(true).feature(Tool.mark,
                Tool.dataView,
                new MagicType(Magic.line, Magic.bar, Magic.stack, Magic.tiled),
                Tool.restore,
                Tool.saveAsImage).padding(20);
        option.calculable(true);
        CategoryAxis category = new CategoryAxis();
        category.axisLabel().interval(0);
        category.axisLabel().rotate(45);
        Line l1 = new Line("转换");
        for (Map<String, Object> objectMap : lists) {
            //设置类目
            category.data(objectMap.get("time"));
            l1.data(objectMap.get("docCount"));
        }
        option.xAxis(category);
        option.yAxis(new ValueAxis().boundaryGap(0d, 0.01));
        l1.smooth(true).itemStyle().normal().areaStyle().typeDefault();
        l1.data();
        option.series(l1);
        option.grid().containLabel(true);
        option.grid().y(100);
//        option.grid().left("10%");
//        option.grid().right("30px");
//        option.grid().bottom("20%");
        return option;
    }


}

