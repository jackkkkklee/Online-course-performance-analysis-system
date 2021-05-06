package com.abc.dao;

import com.abc.entity.Course;
import com.abc.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface TeacherDao
{
    //添加老师
    public void add(Teacher teacher);
    //删除老师
    public void delete(String tid);
    //修改老师
    public void update(Teacher teacher);
    //查单个老师
    public Teacher selectOne(String tid);
    //查所有老师
    public List<Teacher> selectAll();
    //查询老师所教的所有课程
    List<String> selectCourseByTeacher(String tid);
    //查询老师上的每一节课
    List<Course> selectEveryCourseByTeacher(String tid);


    //查所有老师的tid
    public List<String> selectAllTid();



}
