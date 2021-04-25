package com.abc.controller;

import com.abc.annotation.APICallLimiter;
import com.abc.annotation.PermInfo;
import com.abc.entity.Course;
import com.abc.service.AttentionService;
import com.abc.service.EmotionService;
import com.abc.service.StudentService;
import com.abc.service.AttentionService;
import com.abc.service.EmotionService;

import com.abc.service.impl.AttentionServiceImpl;
import com.abc.util.MyTimeUtils;
import com.abc.vo.AttentionDetailVo;
import com.abc.vo.EmotionVo;
import com.abc.vo.Json;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.Date;
import java.util.HashMap;


@PermInfo(value = "注意力分析模块", pval = "a:analysis")
@RestController
@RequestMapping("/analysis")
public class AttentionAnalysisController {

    @Autowired
    AttentionService attentionService;
    @Autowired
    EmotionService emotionService;

    @Autowired
    StudentService studentService;

    @PostMapping
    public Json AttentionAnalysis(@RequestBody String body) {
        String oper = "AttentionAnalysis";

        JSONObject jsonObj = JSON.parseObject(body);
        String image = jsonObj.getString("image");
        String sid = jsonObj.getString("sid");
        String cid = jsonObj.getString("cid");
        Boolean showData = jsonObj.getBoolean("show_data");
        String mode = jsonObj.getString("mode");
        //时间命名
        Date image_date = jsonObj.getDate("image_name");
        AttentionDetailVo attentionDetailVo=null;
        int attention=0;
        try{
             attentionDetailVo= attentionService.finalAssessment(image, mode, sid,image_date);
             if (attentionDetailVo==null)
                 return Json.fail(oper);
             attention=attentionDetailVo.getAttentionValue();
        }catch (Exception e){
            e.printStackTrace();
        }
        //用hashmap存储1分钟以内的attention_value或者使用内存数据库


        System.out.println("专注度:"+attention);
        System.out.println("cid"+cid);
        // 将专注度存到数据库
        attentionService.add(cid,sid,attention,image_date,image_date);
        //将表情存到数据库
        if(attentionDetailVo.getExpressValue()!=null){
            EmotionVo emotionVo=new EmotionVo();
            System.out.println(attentionDetailVo.getExpressValue());
            emotionService.addEmotion(cid,sid, EmotionVo.convertToNumber(attentionDetailVo.getExpressValue()),image_date);
        }


        if(showData){

            return Json.succ(oper,"attentionDetailVo",attentionDetailVo);
        }

        return Json.succ(oper);
    }
//    @PostMapping("/emotion")
//    public Json EmotionAnalysis(@RequestBody String body){
//        String oper = "EmotionAnalysis";
//
//        JSONObject jsonObj = JSON.parseObject(body);
//        String image = jsonObj.getString("image");
//        String imageName = jsonObj.getString("image_name");
//
//
//
//
//
//        return  null;
//
//    }




//    @PostMapping("/selectAttentionValue_student")
//    public Json selectAttentionValueByStudent(@RequestBody String body){
//        String oper = "select AttentionValue By Student";
//
//        JSONObject jsonObj = JSON.parseObject(body);
//        String cid  = jsonObj.getString("cid");
//        String sid  = jsonObj.getString("sid");
//        String startTime  = jsonObj.getString("startTime");
//
//
//        List<Date> dateList=new ArrayList<>();
//        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try{
//            Date date= formatter.parse(startTime);
//            for (int i=1 ; i<=12; i++){
//                dateList.add(date);
//                long time = 5*60*1000;
//                date = new Date(date.getTime() + time);
//            }
//        }catch (ParseException e)
//        {
//            e.printStackTrace();
//        }
//        System.out.println(dateList);
//
//        List<Integer> list = attentionService.selectAllAttentionValueOfOneCourseService(cid,sid);
//
//        return Json.succ(oper,"attention_value",list).data("time",dateList).data("name",sid);
//    }

//    @PostMapping("/selectAttentionValue_parent")
//    public Json selectAttentionValueByParent(@RequestBody String body){
//        String oper = "selectAttentionValueByParent";
//        JSONObject jsonObj = JSON.parseObject(body);
//        String pid  = jsonObj.getString("pid");
//        String cid  = jsonObj.getString("cid");
//        String startTime  = jsonObj.getString("startTime");
//        String sid = studentService.selectSidByPidService(pid);
//
//        List<Date> dateList=new ArrayList<>();
//        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try{
//            Date date= formatter.parse(startTime);
//            for (int i=1 ; i<=12; i++){
//                dateList.add(date);
//                long time = 5*60*1000;
//                date = new Date(date.getTime() + time);
//            }
//        }catch (ParseException e)
//        {
//            e.printStackTrace();
//        }
//        System.out.println(dateList);
//
//        List<Integer> list = attentionService.selectAllAttentionValueOfOneCourseService(cid,sid);
//        return Json.succ(oper,"attention_value",list).data("time",dateList).data("name",sid);
//
//    }

    @PostMapping("/selectAverageAttentionValue_teacher")
    public Json selectAverageAttentionValueByTeacher(@RequestBody String body){
        String oper = "selectAverageAttentionValueByTeacher";
        JSONObject jsonObj = JSON.parseObject(body);
        String cid  = jsonObj.getString("cid");
        String startTime  = jsonObj.getString("startTime");

        Double average = studentService.countAverageAttentionValue(cid);
        return Json.succ(oper,"average_attention_value",average);
    }

}
