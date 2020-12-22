package com.abc.entity;

import lombok.Data;

import java.util.Date;
@Data
public class Course
{
    private String tid;
    private String cid;
    private String course;
    private Date startTime;
    private Date endTime;



}
