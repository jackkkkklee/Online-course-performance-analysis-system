package com.abc.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class Course
{
    private String tid;
    private String cid;
    private String course;
    private Date startTime;
    private Date endTime;
    public Course(String tid, String cid, String course, Date startTime, Date endTime) {
        this.tid = tid;
        this.cid = cid;
        this.course = course;
        this.startTime = startTime;
        this.endTime = endTime;
    }





}
