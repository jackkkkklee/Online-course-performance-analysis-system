package com.abc.vo;

public class StudentClassVo {
    boolean hasCourse;
    String CourseName;
    public StudentClassVo(boolean hasCourse, String courseName) {
        this.hasCourse = hasCourse;
        CourseName = courseName;
    }

    public boolean isHasCourse() {
        return hasCourse;
    }

    public void setHasCourse(boolean hasCourse) {
        this.hasCourse = hasCourse;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

}
