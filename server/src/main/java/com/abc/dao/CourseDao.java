package com.abc.dao;

import com.abc.entity.Course;
import com.abc.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface CourseDao
{
    //添加课程
    public void add(Course course);
    //删除课程
    public void delete(String cid);
    //修改课程
    public void update(Course course);
    //查单个课程
    public Course selectOne(String cid);
    //查所有课程
    public List<Course> selectAll();
    //根据老师id查询课程
    public List<Course> selectByTeacherId(String tid);
}
