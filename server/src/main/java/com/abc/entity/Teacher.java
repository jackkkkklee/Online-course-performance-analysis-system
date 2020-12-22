package com.abc.entity;



import lombok.Data;

import java.util.List;
@Data
public class Teacher
{
    private String tid;
    private String teacherName;
    private String tsex;
    private int tage;

    private List<Course> courses;



}
