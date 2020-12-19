package com.abc.controller;

import com.abc.annotation.PermInfo;
import com.abc.entity.Performance;
import com.abc.service.StudentService;
import com.abc.util.MyTimeUtils;
import com.abc.vo.Json;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@PermInfo(value = "学生模块", pval = "a:student")
@RestController
@RequestMapping("/student")
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    StudentService studentService;
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
        List<Performance> performanceList=studentService.queryStudentConcentrationByTimeAndClass(sid,cid,startTime,endTime);
        // 以 前端需要的格式返回json
        Json json=new Json();
        String[] time = new String[performanceList.size()];
        Integer[] attention_value = new Integer[performanceList.size()];
        for(int i=0;i<performanceList.size();i++){
            time[i] = MyTimeUtils.convertToHHmmFormat(performanceList.get(i).getTimeOffset());
            attention_value[i] = performanceList.get(i).getAttention_value();
        }


        return Json.succ(oper, "time", time).data("attention_value",attention_value);
    }
}
