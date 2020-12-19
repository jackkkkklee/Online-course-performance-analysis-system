package com.abc.entity;



import java.time.LocalDate;
import java.util.Date;

public class Performance
{
    private int cid;
    private int sid;
    private Date timeOffset;
    private int attention_value;
    private int expression_value;

    @Override
    public String toString() {
        return "SysPerformance{" +
                "cid=" + cid +
                ", sid=" + sid +
                ", timeOffset=" + timeOffset +
                ", attention_value=" + attention_value +
                ", expression_value=" + expression_value +
                '}';
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
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
}
