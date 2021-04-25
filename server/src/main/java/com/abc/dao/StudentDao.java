package com.abc.dao;

import com.abc.entity.Course;
import com.abc.entity.Student;
import com.abc.entity.Teacher;

import jnr.ffi.annotations.In;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
@Mapper
@Component
public interface StudentDao
{
    //添加学生
    public void add(Student student);
    //删除学生
    public void delete(String sid);
    //修改学生
    public void update(Student student);
    //查单个学生
    public Student selectOne(String sid);
    //查所有学生
    public List<Student> selectAll();
    //查询学生的所有课程
    List<Course> selectStudentCourse(String sid);
    //查询学生课程by date
    Course selectStudentCourseByDate(@Param("sid") String sid, @Param("date") Date date);


    //根据家长pid来查询学生id
    public String selectSidByPid(String pid);

    //计算同一门课的学生数量
    public Integer countStudentNumber(String cid);
    //求所有学生一节课的专注度值之和
    public Double valueOfAllStu(String cid);

    //查询所有学生的sid
    public List<String> selectAllSid();

    //清空某个学生的课程
    void clearCourseForStudent(String sid);
    //为某个学生添加课程
    void addCourseForStudent(String sid,String cid);

}
