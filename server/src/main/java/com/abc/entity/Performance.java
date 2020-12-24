package com.abc.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Performance
{
    private String cid;
    private String sid;
    private Date timeOffset;
    private int attention_value;
    private int expression_value;
}
