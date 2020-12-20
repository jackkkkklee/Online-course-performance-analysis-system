package com.abc.controller;

import com.abc.annotation.PermInfo;
import com.abc.service.AttentionService;
import com.abc.service.impl.AttentionServiceImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PermInfo(value = "注意力分析模块", pval = "a:analysis")
@RestController
@RequestMapping("/analysis")
public class AttentionAnalysisController {

    @Autowired
    AttentionService attentionService;
    @PostMapping
    public void AttentionAnalysis(@RequestBody String body) {
        JSONObject jsonObj = JSON.parseObject(body);
        String image = jsonObj.getString("image");
        String sid = jsonObj.getString("sid");
        String imageName = jsonObj.getString("image_name");
        int attention = attentionService.finalAssessment(image, AttentionServiceImpl.FULL_MODE, sid);
        System.out.println(attention);
        // 存到数据库


    }

}
