package com.jingxiang.datachange.service;

import com.github.abel533.echarts.Option;
import com.jingxiang.datachange.entity.StudentInfoResult;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface StudentInfoService {
    Option countHobby();
    List<StudentInfoResult> findAllStudentResult(StudentInfoResult studentInfo);
}
