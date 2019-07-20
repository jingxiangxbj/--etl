package com.jingxiang.datachange.repository;

import com.jingxiang.datachange.entity.StudentInfo;
import com.jingxiang.datachange.entity.StudentInfoResult;
import com.jingxiang.datachange.entity.StudentResultExt;

import java.io.IOException;
import java.util.List;

public interface EtlRepository {
    //查询所有学生信息
    List<StudentInfo> extractStudentInfo() throws IOException;

    Integer addStudent(StudentInfo studentInfo);

    Integer addStudentResult(StudentInfoResult studentInfoResult);

    //统计各爱好人数
    List<StudentResultExt> countHobby();

    List<StudentInfoResult> findAllStudentResult(StudentInfoResult studentInfo);

    void deleteStudent();

}
