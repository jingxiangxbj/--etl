package com.jingxiang.datachange.controller;

import com.github.abel533.echarts.Option;
import com.jingxiang.datachange.entity.ETLDetailInfo;
import com.jingxiang.datachange.entity.MessageData;
import com.jingxiang.datachange.service.LogInfoService;
import com.jingxiang.datachange.util.ResponseBo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;


@Controller
public class LogInfoController extends BaseController{

    @Autowired
    private LogInfoService logInfoService;

    @RequestMapping("etlInfo")
    @RequiresPermissions("etlInfo:barchar")
    public String index(){
        return "system/monitor/barchar";
    }


    @RequestMapping(method = RequestMethod.POST,value = "etlInfo/barchar")
    @RequiresPermissions("etlInfo:barchar")
   @ResponseBody
  //  public ResponseBo LogUseForBarChar(@RequestParam(value = "stage",required = false) String stage, @RequestParam(value = "status",required = false) String status)   {
    public ResponseBo LogUseForBarChar()   {

            try {
         Option option = logInfoService.findLogUseForBarChar("transform","ALL","HOUR");
              //  Option option = logInfoService.findLogUseForBarChar(stage,status,"HOUR");

                return  ResponseBo.ok(option);
        } catch (Exception e) {
            e.printStackTrace();
           return   ResponseBo.error();
        }
    }

    @RequestMapping(method = RequestMethod.POST,value = "etlInfo/list")
    @ResponseBody
    public List<ETLDetailInfo> getData(ETLDetailInfo etlDetailInfo) throws  IOException {
        List<ETLDetailInfo> etlDetailInfos = logInfoService.findLogDateByCondition(etlDetailInfo);
        return etlDetailInfos;

    }


    @RequestMapping("etlInfoDetail")
    @RequiresPermissions("etlInfo:list")
    public String index1(){
        return "system/monitor/etlInfo";
    }

}

