package com.abc.service;

import com.abc.entity.Course;
import com.abc.vo.StudentClassVo;

import java.util.Date;
import java.util.List;

public interface StudentService {
    // 检查现在是否有课，并返回StudentClassVo(是否有课，课程名字）
    StudentClassVo queryOngoingStudentCourse(String sid);
    List<Course> queryAllCourse(String sid);

    //根据家长pid来查询学生id
    public String selectSidByPidService(String pid);
    //计算同一课程的学生注意力平均值
    public Double countAverageAttentionValue(String cid);
    //查询所有学生的sid
    public List<String> selectAllSidService();
    //清空某个学生的课程
    void clearCourseForStudentService(String sid);
    //为某个学生添加课程
    void addCourseForStudentService(String sid,String cid);

}
