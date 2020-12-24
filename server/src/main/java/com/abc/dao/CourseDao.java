package com.abc.dao;

import com.abc.entity.Course;
import com.abc.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
@Mapper
@Component
public interface CourseDao
{
    //添加课程
    public void add(Course course);
    //删除课程
    public void delete(@Param("cid") String cid, @Param("tid")String tid, @Param("startTime")Date startTime ,@Param("endTime") Date endTime);
    //修改课程
    public void update(Course course);
    //查单个课程
    public Course selectOne(String cid, String tid, Data startTime,Date endTime);
    //查所有课程
    public List<Course> selectAll();
    List<Student> selectStudentByCourse(String cid);
}
