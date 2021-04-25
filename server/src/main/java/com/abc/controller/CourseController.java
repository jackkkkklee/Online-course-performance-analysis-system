package com.abc.controller;

import com.abc.annotation.PermInfo;
import com.abc.entity.Course;
import com.abc.service.CourseService;
import com.abc.service.StudentService;
import com.abc.vo.Json;
import com.abc.vo.StudentClassVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@PermInfo(value = "课程模块", pval = "a:course")
@RestController
@RequestMapping("/course")
public class CourseController {
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    CourseService courseService;

    @PostMapping("/delete_course_by_cid")
    public void deleteCourseByCid(@RequestBody String body){
        String oper = "delete course by cid";
        log.info("{}, body: {}",oper,body);
        JSONObject jsonObj = JSON.parseObject(body);
        String cid  = jsonObj.getString("cid");
        courseService.deleteCourseByCidService(cid);
    }

    @PostMapping("/add_course")
    public void queryOngoingStudentCourse(@RequestBody String body){
        String oper = "delete course by cid";
        log.info("{}, body: {}",oper,body);
        JSONObject jsonObj = JSON.parseObject(body);

        String cid  = jsonObj.getString("cid");
        String tid  = jsonObj.getString("tid");
        String course  = jsonObj.getString("course");
        String startTime  = jsonObj.getString("startTime");
        String endTime  = jsonObj.getString("endTime");

        Course newCourse = new Course();
        newCourse.setCid(cid);
        newCourse.setTid(tid);
        newCourse.setCourse(course);

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            newCourse.setStartTime(formatter.parse(startTime));
            newCourse.setEndTime(formatter.parse(endTime));
        }catch (ParseException e)
        {
            e.printStackTrace();
        }

        courseService.addCourseService(newCourse);
    }
    //查询所有课程
    @PostMapping("/select_all_courses")
    public Json SelectAllCourses(@RequestBody String body){
        String oper = "select_all_courses";
        log.info("{}, body: {}",oper,body);
        List<Course> courses = courseService.selectAllCoursesService();
        return Json.succ(oper,"courses",courses);
    }

    //更新课程
    @PostMapping("/update_course_by_cid")
    public void UpdateCourseByCid(@RequestBody String body){
        String oper = "update_course_by_cid";
        log.info("{}, body: {}",oper,body);
        JSONObject jsonObj = JSON.parseObject(body);
        String cid  = jsonObj.getString("cid");
        String startTimeStr  = jsonObj.getString("startTime");
        String endTimeStr  = jsonObj.getString("endTime");
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = null;
        Date endTime= null;
        try{
            startTime = formatter.parse(startTimeStr);
            endTime = formatter.parse(endTimeStr);
        }catch (ParseException e)
        {
            e.printStackTrace();
        }
        courseService.updateCourse(cid,startTime,endTime);
    }

}