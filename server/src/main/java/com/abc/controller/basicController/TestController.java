package com.abc.controller.basicController;

import com.abc.annotation.APICallLimiter;
import com.abc.annotation.PermInfo;
import com.abc.dao.CourseDao;
import com.abc.dao.PerformanceDao;
import com.abc.dao.StudentDao;
import com.abc.dao.TeacherDao;
import com.abc.entity.Course;
import com.abc.entity.Teacher;
import com.abc.service.AttentionService;
import com.abc.service.impl.AttentionServiceImpl;
import com.abc.util.Assessment;
import com.abc.util.MyTimeUtils;
import org.apache.shiro.authz.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * created by CaiBaoHong at 2018/4/17 14:16<br>
 */
@PermInfo(value = "测试模块模块", pval = "a:test")
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    AttentionService attentionService;
    // 由于ShiroConfig中配置了该路径可以匿名访问，所以这接口不需要登录就能访问
    @GetMapping("/hello")
    public String hello() {
        return "hello spring boot";
    }

    // 如果ShiroConfig中没有配置该路径可以匿名访问，所以直接被登录过滤了。
    // 如果配置了可以匿名访问，那这里在没有登录的时候可以访问，但是用户登录后就不能访问
    @RequiresGuest
    @GetMapping("/guest")
    public String guest() {
        return "@RequiresGuest";
    }

    @RequiresAuthentication
    @GetMapping("/authn")
    public String authn() {
        return "@RequiresAuthentication";
    }

    @RequiresUser
    @GetMapping("/user")
    public String user() {
        return "@RequiresUser";
    }

    @RequiresPermissions("a:mvn:install")
    @GetMapping("/mvnInstall")
    public String mvnInstall() {
        return "mvn:install";
    }

    @RequiresPermissions("a:gradleBuild")
    @PermInfo("构建gradle")
    @GetMapping("/gradleBuild")
    public String gradleBuild() {
        return "gradleBuild";
    }


    @RequiresRoles("js")
    @GetMapping("/js")
    public String js() {
        return "js programmer";
    }


    @RequiresRoles("python")
    @GetMapping("/python")
    public String python() {
        return "python programmer";
    }

    @Autowired
    TeacherDao teacherDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    PerformanceDao performanceDao;
    @Autowired
    StudentDao studentDao;
    @GetMapping("/testAop")
    public String testAop(){
//        attentionService.basicAssessment("1","1");
//        Teacher te = teacherDao.selectOne("tea001");
//
//        System.out.println(te.getTeacherName());
//       List<Course>  courseList=teacherDao.selectCourseByTeacher("tea001");
    try{
         //courseDao.add(new Course("tea001","class001","大学英语", MyTimeUtils.getSystemTime(new Date("Wed Dec 23 22:04:16 CST 2020")),MyTimeUtils.getSystemTime(new Date("Wed Dec 23 22:20:00 CST 2020"))));
//        courseDao.delete("class001","tea001",MyTimeUtils.StringToDate("2020-12-24 12:04:00"),MyTimeUtils.StringToDate("2020-12-23 22:20:00"));
//        courseDao.selectAll();
//        performanceDao.selectOne("class001","stu001",MyTimeUtils.StringToDate("2020-12-19 20:00:00"));
//        performanceDao.selectAll("class001","stu001");
          System.out.println(studentDao.selectStudentCourse("stu001"));
    }catch (Exception e){
         e.printStackTrace();
    }

//        System.out.println(courseList.get(0).getCourse());
        return  "hello world";
    }

}
