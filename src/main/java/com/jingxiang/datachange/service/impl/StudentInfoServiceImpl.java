package com.jingxiang.datachange.service.impl;

import com.github.abel533.echarts.Label;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.style.ItemStyle;
import com.jingxiang.datachange.entity.StudentInfoResult;
import com.jingxiang.datachange.entity.StudentResultExt;
import com.jingxiang.datachange.repository.EtlRepository;
import com.jingxiang.datachange.service.BaseService;
import com.jingxiang.datachange.service.StudentInfoService;
import com.jingxiang.datachange.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentInfoServiceImpl  implements StudentInfoService {
    @Autowired
    private EtlRepository etlRepository;
    @Override
    public Option countHobby() {
        Option option = new Option();

        List<StudentResultExt> studentResultExts = etlRepository.countHobby();
        //标题
        Title title = new Title();
        title.setText("学生兴趣爱好分析");
        option.setTitle(title);
        option.legend("读书", "追剧","打游戏","听歌");
        //提示框
        Tooltip tooltip = new Tooltip();
        tooltip.setTrigger(Trigger.item);
        tooltip.formatter("{a} <br/>{b} : {c} ({d}%)");
        option.setTooltip(tooltip);

        //饼图数据
        Pie pie = new Pie();
        //循环数据
        for (StudentResultExt studentResultExt: studentResultExts) {
            String hobby = studentResultExt.getHobby();
            //饼图数据
            pie.data(new PieData(hobby, studentResultExt.getCountHobby()));
        }
        //饼图的圆心和半径
        pie.center(400,300).radius(160);
        //设置数据
        option.series(pie);
        return option;
    }

    @Override
    public List<StudentInfoResult> findAllStudentResult(StudentInfoResult studentInfo) {
        try {
            List<StudentInfoResult> allStudentResult = etlRepository.findAllStudentResult(studentInfo);
            return allStudentResult;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
