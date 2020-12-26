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

        List<Performance> performanceList =new ArrayList<>();
        List<Date> dateList=MyTimeUtils.getDateList(startTime,endTime,1);
        for(int i=0;i<dateList.size();i++){
            Performance performance =performanceDao.selectOne(cid,sid,dateList.get(i));
            //bug 可能是时间格式不对，时间没有时分
            if(performance!=null){
                performance.setTimeOffset(dateList.get(i));
                performanceList.add(performance);
            }
        }
        return  performanceList;
    }

//    @Override
//    public List<ClassAttentionVo> queryAverageClassConcentrationByTimeAndCourse(String cid, Date startTime, Date endTime) {
//        List<Performance> performanceList = (cid,startTime,endTime);
//        List<ClassAttentionVo> classAttentionVoList=new ArrayList<>();
//        for(Performance performance :performanceList){
//            classAttentionVoList.add(new ClassAttentionVo(performance.getAttention_value(),performance.getTimeOffset(),performance.getCid()));
//        }
//        return classAttentionVoList;
//        return null;
//    }

    @Override
    public List<Performance> queryClassEmotionInRealTime(String cid) {
        return null;
    }

    @Override
    public List<String> queryCourseByTeacherId(String tid) {
        List<Course> courseList=teacherDao.selectEveryCourseByTeacher(tid);
        List<String> coursesName=new ArrayList<>();
        for(Course course:courseList){
            coursesName.add(course.getCourse());
        }
        return  coursesName;
    }

    @Override
    public List<Student> queryStudentByTeacher(String tid) {
        List<Student> studentList =new ArrayList<>();
        //先查出老师教的所有课(这里以课程名相同的要排除)
        List<Course> courseList = teacherDao.selectEveryCourseByTeacher(tid);
        //查找学习每个课的学生
        for(Course course:courseList){
            List<Student> s=courseDao.selectStudentByCourse(course.getCid());
            if(s!=null)
                studentList.addAll(s);
        }

        return  studentList;

    }

    @Override
    public List<String> queryStudentIdByTeacher(String tid) {

        List<String> studentList = teacherDao.selectCourseByTeacher(tid);

        return studentList;
    }
}
