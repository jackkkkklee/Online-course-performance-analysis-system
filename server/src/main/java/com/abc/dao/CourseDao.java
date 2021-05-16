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

    public void addCourse(Course course);
    //删除课程
    public void delete(@Param("cid") String cid, @Param("tid")String tid, @Param("startTime")Date startTime ,@Param("endTime") Date endTime);
    //修改课程
    public void update(@Param("cid") String cid,@Param("startTime") Date startTime,@Param("endTime") Date endTime);
    //为课程修改授课老师
    public void updateTeacherForCourse(@Param("cid") String cid,@Param("tid") String tid);

    //查单个课程
    public Course selectOne(String cid, String tid, Data startTime,Date endTime);
    //查所有课程
    public List<Course> selectAll();
    List<Student> selectStudentByCourse(String cid);


    //根据课程cid删除课程方法
    public void deleteCourseByCid(String Cid);
    //根据tid查询老师课程
    public List<Course> selectCourseByTid(String tid);
    //根据tid清空老师课程
    void clearTeacherCourse(String tid);
    //根据sid查询课程
    public List<Course> selectCoursesBySid(String sid);


}
