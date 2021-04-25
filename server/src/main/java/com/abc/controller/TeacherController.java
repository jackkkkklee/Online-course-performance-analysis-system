package com.abc.controller;

import com.abc.annotation.PermInfo;
import com.abc.dao.PerformanceDao;
import com.abc.entity.Course;
import com.abc.entity.Performance;
import com.abc.service.AttentionService;
import com.abc.service.TeacherService;
import com.abc.util.MyTimeUtils;
import com.abc.vo.ClassAttentionVo;
import com.abc.vo.Json;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@PermInfo(value = "老师模块", pval = "a:teacher")
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private static final Logger log = LoggerFactory.getLogger(TeacherController.class);
    @Autowired
    TeacherService teacherService;
    @Autowired
    AttentionService attentionService;
    //queryStudentCourse()

    @PostMapping("/query_concentration")
    Json queryStudentConcentration(@RequestBody String body){
        String oper = "queryStudentConcentration";
        log.info("{}, body: {}",oper,body);
        JSONObject jsonObj = JSON.parseObject(body);
        String sid = jsonObj.getString("sid");
        String cid = jsonObj.getString("cid");
        Date startTime= jsonObj.getDate ("startTime");
        Date endTime = jsonObj.getDate("endTime");
        List<Performance> performanceList= teacherService.queryStudentConcentrationByTimeAndCourse(sid,cid,startTime,endTime);
        // 以 前端需要的格式返回json

        String[] time = new String[performanceList.size()];
        int[] attention_value = new int[performanceList.size()];
        for(int i=0;i<performanceList.size();i++){
            //排除掉找不到成绩的时间点
            if(performanceList.get(i)!=null){
                time[i] = MyTimeUtils.convertToHHmmFormat(performanceList.get(i).getTimeOffset());
                attention_value[i] = performanceList.get(i).getAttention_value();
            }
        }
        Json json=Json.succ(oper, "time", time).data("attention_value",attention_value);

        System.out.println(json);
        return json;
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid",value = "课程id",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "startTime",value = "开始时间",paramType = "query",dataType = "Date",required = true),
            @ApiImplicitParam(name = "endTime",value = "结束时间",paramType = "query",dataType = "Date",required = true)
    })
    @PostMapping("/query_class_concentration")
    Json queryClassConcentration(@RequestBody String body) throws CloneNotSupportedException, ParseException {
        String oper = "queryStudentConcentration";
        log.info("{}, body: {}",oper,body);
        JSONObject jsonObj = JSON.parseObject(body);
        String cid = jsonObj.getString("cid");
        Date startTime= jsonObj.getDate ("startTime");
        Date endTime = jsonObj.getDate("endTime");
//        List<ClassAttentionVo> classAttentionVos= teacherService.queryAverageClassConcentrationByTimeAndCourse(cid,startTime,endTime);
        List<ClassAttentionVo> classAttentionVos = attentionService.selectAllAverageByCourseAndTime(cid,startTime,endTime);
        // 以 前端需要的格式返回json
        if(classAttentionVos==null)
            return Json.fail(oper,"no_result");
        String[] time = new String[classAttentionVos.size()];
        int[] attention_value = new int[classAttentionVos.size()];

        for(int i=0;i<classAttentionVos.size();i++){
            time[i] = MyTimeUtils.convertToHHmmFormat(classAttentionVos.get(i).getTimeOffset());
            attention_value[i] = classAttentionVos.get(i).getAttention_value();
        }
       Json json=Json.succ(oper, "time", time).data("attention_value",attention_value);
        System.out.println(json);
        return json;
    }


    @PostMapping("/query_courses")
    Json queryCoursesOfTeacher(@RequestBody String body){
        String oper = "queryCoursesOfTeacher";
        log.info("{}, body: {}",oper,body);
        JSONObject jsonObj = JSON.parseObject(body);
        String tid = jsonObj.getString("tid");

        List<String> courses = teacherService.queryCourseByTeacherId(tid);
        Json json=Json.succ(oper,"courses",courses);
        System.out.println(json);
        return json;
    }
    @PostMapping("/query_student")
    Json queryStudentFromTeacher(@RequestBody String body){
        String oper = "queryStudentFromTeacher";
        log.info("{}, body: {}",oper,body);
        JSONObject jsonObj = JSON.parseObject(body);
        String tid = jsonObj.getString("tid");

        List<String> studentId= teacherService.queryStudentIdByTeacher(tid);
        return Json.succ(oper,"studentNames",studentId);
    }
    @Autowired
    PerformanceDao performanceDao;
    @PostMapping("/testAop")
    public String testAop(@RequestBody String body){
        JSONObject jsonObj = JSON.parseObject(body);
        Date date = jsonObj.getDate("startTime");
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
            Performance performance1=performanceDao.selectOne("class001","stu001",MyTimeUtils.StringToDate("2020-12-19 10:00:00"));
            Performance performance2=performanceDao.selectOne("class001","stu001",date);
            //int v=performanceDao.selectOne11("class001","stu002");
            System.out.println(performance2.getAttention_value());

        }catch (Exception e){
            e.printStackTrace();
        }

//        System.out.println(courseList.get(0).getCourse());
        return  "hello world";
    }
}
