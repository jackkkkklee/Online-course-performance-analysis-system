package com.abc.entity;



import java.util.List;

public class Teacher
{
    private int tid;
    private String teacherName;
    private String tsex;
    private int tage;

    private List<Course> courses;

    @Override
    public String toString() {
        return "Teacher{" +
                "tid=" + tid +
                ", teacherName='" + teacherName + '\'' +
                ", tsex='" + tsex + '\'' +
                ", tage=" + tage +
                ", courses=" + courses +
                '}';
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTsex() {
        return tsex;
    }

    public void setTsex(String tsex) {
        this.tsex = tsex;
    }

    public int getTage() {
        return tage;
    }

    public void setTage(int tage) {
        this.tage = tage;
    }
}
