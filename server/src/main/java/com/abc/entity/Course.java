package com.abc.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class Course
{
    private String tid;
    private String cid;
    private String course;
    private Date startTime;
    private Date endTime;
    public Course() {}
    public Course(String tid, String cid, String course, Date startTime, Date endTime) {
        this.tid = tid;
        this.cid = cid;
        this.course = course;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
