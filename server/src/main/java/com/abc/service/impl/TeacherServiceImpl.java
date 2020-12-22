package com.abc.service.impl;

import com.abc.dao.CourseDao;
import com.abc.dao.PerformanceDao;
import com.abc.dao.StudentDao;
import com.abc.dao.TeacherDao;
import com.abc.entity.Course;
import com.abc.entity.Performance;
import com.abc.entity.Student;
import com.abc.service.TeacherService;
import com.abc.util.MyTimeUtils;
import com.abc.vo.ClassAttentionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    PerformanceDao performanceDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    TeacherDao teacherDao;
    @Override
    public List<Performance> queryStudentConcentrationByTimeAndCourse(String sid, String cid, Date startTime, Date endTime) {
        //mock 模拟
//          List<Performance> performanceList =new ArrayList<>();
//        Performance performance=new Performance();
//        performance.setTimeOffset(new Date());
//        performance.setAttention_value(100);
//        performanceList.add(performance);
//        return performanceList;
        List<Performance> performanceList =new ArrayList<>();
        List<Date> dateList=MyTimeUtils.getDateList(startTime,endTime,1);
        for(int i=0;i<dateList.size();i++){
            performanceList.add(performanceDao.selectOne(cid,sid,dateList.get(i)));
        }
        return  performanceList;
    }

    @Override
    public List<ClassAttentionVo> queryAverageClassConcentrationByTimeAndCourse(String cid, Date startTime, Date endTime) {
        List<Performance> performanceList = performanceDao.selectAllAverageByCourseAndTime(cid,startTime,endTime);
        List<ClassAttentionVo> classAttentionVoList=new ArrayList<>();
        for(Performance performance :performanceList){
            classAttentionVoList.add(new ClassAttentionVo(performance.getAttention_value(),performance.getTimeOffset(),performance.getCid()));
        }
        return classAttentionVoList;
    }

    @Override
    public List<Performance> queryClassEmotionInRealTime(String cid) {
        return null;
    }

    @Override
    public List<String> queryCourseByTeacherId(String tid) {
        List<Course> courseList=courseDao.selectByTeacherId(tid);
        List<String> coursesName=new ArrayList<>();
        for(Course course:courseList){
            coursesName.add(course.getCourse());
        }
        return  coursesName;
    }

    @Override
    public List<String> queryStudentNameByTeacher(String tid) {
//        List<Student> studentList;
//        List<String> studentName=new ArrayList<>();
//        for(Student student:studentList){
//            studentName.add(student.getStudentName());
//        }
//        return  studentName;
return null;
    }




}
