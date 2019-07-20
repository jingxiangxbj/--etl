package com.jingxiang.datachange.repository.impl;

import com.jingxiang.datachange.entity.StudentInfo;
import com.jingxiang.datachange.entity.StudentInfoResult;
import com.jingxiang.datachange.entity.StudentResultExt;
import com.jingxiang.datachange.mapper.EtlMapper;
import com.jingxiang.datachange.repository.EtlRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EtlRespositoryImpl implements EtlRepository {
    @Resource
    private EtlMapper etlMapper;

    @Override
    public List<StudentInfo> extractStudentInfo() throws IOException {
        return etlMapper.findAllStudent();
    }

    @Override
    public Integer addStudent(StudentInfo studentInfo) {
        return etlMapper.addStudent(studentInfo);
    }

    @Override
    public Integer addStudentResult(StudentInfoResult studentInfoResult) {
        return etlMapper.addStudentResult(studentInfoResult);
    }

    @Override
    public List<StudentResultExt> countHobby() {
        return etlMapper.countHobby();
    }

    @Override
    public List<StudentInfoResult> findAllStudentResult(StudentInfoResult studentInfoResult) {
        List<StudentInfoResult>  studentResult = etlMapper.findStudentResult(studentInfoResult);
            return studentResult;
    }

    @Override
    public void deleteStudent() {
        etlMapper.deleteStudent();
    }


}
