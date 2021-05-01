package com.abc.service.impl;

import com.abc.dao.StudentDao;
import com.abc.entity.Course;
import com.abc.service.StudentService;
import com.abc.vo.StudentClassVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentDao studentDao;


    @Override
    public StudentClassVo queryOngoingStudentCourse(String sid) {
        Date now = new Date();
        Course course =studentDao.selectStudentCourseByDate(sid,now);
        if(course==null){
            return  new StudentClassVo(false,null);
        }
        return new StudentClassVo(true,course.getCourse());

    }

    @Override
    public List<Course> queryAllCourse(String sid) {
        return studentDao.selectStudentCourse(sid);

    }


    @Override
    public String selectSidByPidService(String pid) {
        return studentDao.selectSidByPid(pid);
    }

    @Override
    public Double countAverageAttentionValue(String cid) {
        Integer countStu = studentDao.countStudentNumber(cid);
        Double countOfAllAV = studentDao.valueOfAllStu(cid);
        Double Average = countOfAllAV/countStu/12;
        return Average;
    }

    @Override
    public List<String> selectAllSidService() {
        return studentDao.selectAllSid();
    }

    @Override
    public void clearCourseForStudentService(String sid) {
        studentDao.clearCourseForStudent(sid);
    }

    @Override
    public void addCourseForStudentService(String sid, String cid) {
        studentDao.addCourseForStudent(sid,cid);
    }

}
