package com.jingxiang.datachange.service;

import com.jingxiang.datachange.entity.StudentInfo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service

public interface OperateService {
   void transformStudentInfo() throws IOException;
    List<StudentInfo> extractStudentInfo() throws IOException;
}
