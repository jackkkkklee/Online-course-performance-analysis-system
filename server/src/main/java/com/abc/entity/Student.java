package com.abc.entity;



import lombok.Data;

import java.util.List;
@Data
public class Student
{
    private String sid;
    private String studentName;
    private int sage;
    private String ssex;

    private List<Performance> performances;

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }


}
