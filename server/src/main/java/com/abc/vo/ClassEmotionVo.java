package com.abc.vo;

import lombok.Data;


public class ClassEmotionVo {
    private int expression_value;
    private String cid ;

    public int getExpression_value() {
        return expression_value;
    }

    public void setExpression_value(int expression_value) {
        this.expression_value = expression_value;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
