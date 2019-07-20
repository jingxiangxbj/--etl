package com.jingxiang.datachange.controller;

import com.github.abel533.echarts.Option;
import com.jingxiang.datachange.entity.CusterInfo;
import com.jingxiang.datachange.entity.StudentResultExt;
import com.jingxiang.datachange.entity.User;
import com.jingxiang.datachange.service.EsInfoService;
import com.jingxiang.datachange.util.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(description = "elasticsearch监控接口")
@Controller
public class EsInfoController {


    @Autowired
    private EsInfoService esInfoService;

    @ApiOperation(value = "查找集群信息",httpMethod = "GET")
    @ResponseBody
    @GetMapping(value = "elasticsearch/cluster")
    public CusterInfo getClusterInfo() throws IOException {
        CusterInfo clusterInfo = esInfoService.getClusterInfo();
        return clusterInfo;
    }

    @ApiOperation(value = "查找索引基本信息",httpMethod = "GET")
    @ResponseBody
    @GetMapping(value = "elasticsearch/indies")
    public List<Map<String, Object>> getIndexStore() throws IOException {
        List<Map<String, Object>> indexStore = esInfoService.getIndexStore();
        return indexStore;
    }

    @RequestMapping("elasticsearch/clusterInfo")
    @RequiresPermissions("elasticsearch:clusterInfo")
    public String clusterInfo(Model model) throws IOException {

        CusterInfo custerInfo =  this.esInfoService.getClusterInfo();
        model.addAttribute("custerInfo", custerInfo);
        return "system/monitor/clusterInfo";
    }

    @RequestMapping("indexInfo")
    @RequiresPermissions("indexInfo:indexChange")
    public String index(){
        return "system/monitor/dataChange";
    }

    @ResponseBody
    @GetMapping(value = "indexInfo/indexChange")
    public ResponseBo getIndexChange() {
        try {
            Option option = esInfoService.getIndexDocChange("HALFHOUR");
            return ResponseBo.ok(option);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseBo.error();
    }

}
