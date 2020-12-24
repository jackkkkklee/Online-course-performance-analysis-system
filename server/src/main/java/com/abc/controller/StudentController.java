package com.abc.controller;

import com.abc.annotation.PermInfo;
import com.abc.entity.Course;
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

import java.util.List;

@PermInfo(value = "学生模块", pval = "a:student")
@RestController
@RequestMapping("/student")
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    StudentService studentService;
    @PostMapping("/has_course")
    public Json queryOngoingStudentCourse(@RequestBody String body){
        String oper = "get ongoing course";
        log.info("{}, body: {}",oper,body);
        JSONObject jsonObj = JSON.parseObject(body);
        String sid  = jsonObj.getString("sid");
        StudentClassVo studentClassVo=studentService.queryOngoingStudentCourse(sid);
        return Json.succ(oper,"result",studentClassVo);
    }
    @PostMapping("/query_all_course")
    public Json queryStudentAllCourse(@RequestBody String body){
        String oper = "queryStudentAllCourse";
        log.info("{}, body: {}",oper,body);
        JSONObject jsonObj = JSON.parseObject(body);
        String sid  = jsonObj.getString("sid");
        List<Course> courseList=studentService.queryAllCourse(sid);
        return Json.succ(oper,"courses",courseList);
    }

}
