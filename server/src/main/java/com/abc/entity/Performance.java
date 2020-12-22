package com.abc.entity;



import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
@Data
public class Performance
{
    private String cid;
    private String sid;
    private Date timeOffset;
    private int attention_value;
    private int expression_value;


}
