package com.abc.dao;

import com.abc.entity.Course;
import com.abc.entity.Student;
import com.abc.entity.Teacher;
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

}
