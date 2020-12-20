package com.abc.service;

import com.abc.entity.Performance;

import java.util.Date;
import java.util.List;

public interface TeacherService {
    //返回某个学生的某种课程的一段时间内的专注度
    List<Performance> queryStudentConcentrationByTimeAndCourse(String sid, String cid, Date startTime, Date endTime );
    List<Performance> queryAverageClassConcentrationByTimeAndCourse( String cid, Date startTime, Date endTime );
    //实时返回班级的表情数据
    List<Performance> queryClassEmotionInRealTime(String cid);
    //返回老师教的所有课程名
    List<String> queryCourseByTeacherId(String tid);
    //返回老师正在教的所有学生名字
    List<String> queryStudentNameByTeacher(String tid);
}