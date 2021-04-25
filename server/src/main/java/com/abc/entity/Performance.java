package com.abc.entity;



import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class Performance
{
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Date getTimeOffset() {
        return timeOffset;
    }

    public void setTimeOffset(Date timeOffset) {
        this.timeOffset = timeOffset;
    }

    public int getAttention_value() {
        return attention_value;
    }

    public void setAttention_value(int attention_value) {
        this.attention_value = attention_value;
    }

    public int getExpression_value() {
        return expression_value;
    }

    public void setExpression_value(int expression_value) {
        this.expression_value = expression_value;
    }

    private String cid;
    private String sid;
    private Date timeOffset;
    private int attention_value;
    private int expression_value;
}
