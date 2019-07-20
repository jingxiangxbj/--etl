package com.jingxiang.datachange.mapper;

import com.jingxiang.datachange.entity.StudentInfo;
import com.jingxiang.datachange.entity.StudentInfoResult;
import com.jingxiang.datachange.entity.StudentResultExt;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EtlMapper {
    @Select("select * from student")
    List<StudentInfo> findAllStudent();

    @Insert("insert into student (name,age,birth,gender,mobile,address,idCard,createtime,hobby) values(#{name},#{age},#{birth},#{gender},#{mobile},#{address},#{idCard},now(),#{hobby})")
    Integer addStudent(StudentInfo studentInfo);

    @Delete("delete from student")
    void deleteStudent();

    @Insert("insert into studentinfoResult (name,age,birth,gender,mobile,address,idCard,createtime,hobby) values (#{name},#{age},#{birth},#{gender},#{mobile},#{address},#{idCard},now(),#{hobby})")
    Integer addStudentResult(StudentInfoResult studentInfoResult);

    @Select("select hobby,count(hobby) as countHobby from studentinfoResult group by hobby")
    List<StudentResultExt> countHobby();

//    @Select("select * from studentinfoResult where name = #{name} and gender = #{gender}")
    @Select({"<script>"
            + "SELECT * FROM studentinfoResult "
            + "WHERE 1=1"
            + "    <if test = 'name != null'> AND name = #{name} </if>"
            + "    <if test = 'gender != null'> AND gender = #{gender} </if>"
            + "order by createtime desc"
            +"</script>"})
    List<StudentInfoResult> findStudentResult(StudentInfoResult studentInfoResult);

//    @Select("select * from studentinfoResult")
//    List<StudentInfoResult> findAllStudentResult();
}
