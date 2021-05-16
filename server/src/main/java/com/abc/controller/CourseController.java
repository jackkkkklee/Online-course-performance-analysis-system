package com.abc.controller;

import com.abc.annotation.PermInfo;
import com.abc.entity.Course;
import com.abc.service.CourseService;
import com.abc.service.StudentService;
import com.abc.util.MyTimeUtils;
import com.abc.vo.CourseVO;
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
import java.util.LinkedList;
import java.util.List;

@PermInfo(value = "课程模块", pval = "a:course")
@RestController
@RequestMapping("/course")
public class CourseController {
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    CourseService courseService;
    @Autowired
    StudentService studentService;

    @PostMapping("/delete_course_by_cid")
    public Json deleteCourseByCid(@RequestBody String body){
        String oper = "delete course by cid";
        log.info("{}, body: {}",oper,body);
        JSONObject jsonObj = JSON.parseObject(body);
        String cid  = jsonObj.getString("cid");
        courseService.deleteCourseByCidService(cid);
        return Json.succ();
    }

    @PostMapping("/add_course")
    public Json queryOngoingStudentCourse(@RequestBody String body){
        String oper = "delete course by cid";
        log.info("{}, body: {}",oper,body);
        JSONObject jsonObj = JSON.parseObject(body);

        String cid  = jsonObj.getString("cid");
        String course  = jsonObj.getString("cid");
        String startTime  = jsonObj.getString("startTime");
        String endTime  = jsonObj.getString("endTime");

        Course newCourse = new Course();
        newCourse.setCid(cid);
        newCourse.setTid("tea000");
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
        return Json.succ();
    }
    //查询所有课程
    @PostMapping("/select_all_courses")
    public Json SelectAllCourses(@RequestBody String body){
        String oper = "select_all_courses";
        log.info("{}, body: {}",oper,body);
        List<Course> courses = courseService.selectAllCoursesService();
        List<String> start=new LinkedList<>();
        List<String> end=new LinkedList<>();
        for (Course c:courses)
        {
            start.add(MyTimeUtils.convertToHHmmFormat(c.getStartTime()));
            end.add(MyTimeUtils.convertToHHmmFormat(c.getEndTime()));
        }
        List<CourseVO> courseVOS=new LinkedList<>();
        int index=0;
        for (Course course:courses){
            CourseVO courseVO=new CourseVO();
            courseVO.setCourse(course.getCourse());
            courseVO.setCid(course.getCid());
            courseVO.setTid(course.getTid());
            courseVO.setStartTime(start.get(index));
            courseVO.setEndTime(end.get(index));
            index++;
            courseVOS.add(courseVO);
        }
        return Json.succ(oper,"courses",courseVOS);
    }

    //更新课程
    @PostMapping("/update_course_by_cid")
    public Json UpdateCourseByCid(@RequestBody String body){
        String oper = "update_course_by_cid";
        log.info("{}, body: {}",oper,body);
        JSONObject jsonObj = JSON.parseObject(body);
        String cid  = jsonObj.getString("cid");
        String startTimeStr  = jsonObj.getString("startTime");
        String endTimeStr  = jsonObj.getString("endTime");
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date startTime = MyTimeUtils.StringToDate(startTimeStr);
        Date endTime= MyTimeUtils.StringToDate(endTimeStr);

        courseService.updateCourse(cid,startTime,endTime);

        return Json.succ();
    }
    @PostMapping("/select_course_by_pid")
    public Json selectCourseByPid(@RequestBody String body){
        String oper = "select Course By Pid";
        log.info("{}, body: {}",oper,body);
        JSONObject jsonObj = JSON.parseObject(body);
        String pid  = jsonObj.getString("pid");
        String sid = studentService.selectSidByPidService(pid);
        List<Course> courses = courseService.selectCoursesBySidService(sid);
        return Json.succ(oper,"sid",sid).data("courseList",courses);
    }

}