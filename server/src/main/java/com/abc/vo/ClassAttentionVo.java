package com.abc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor

public class ClassAttentionVo implements Cloneable {
    private int attention_value;

    public int getAttention_value() {
        return attention_value;
    }

    public void setAttention_value(int attention_value) {
        this.attention_value = attention_value;
    }

<<<<<<< HEAD




    private Date timeOffset;

=======
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
    public Date getTimeOffset() {
        return timeOffset;
    }

    public void setTimeOffset(Date timeOffset) {
        this.timeOffset = timeOffset;
    }

<<<<<<< HEAD





    private String cid;

=======
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

<<<<<<< HEAD

=======
    private Date timeOffset;
    private String cid;
>>>>>>> 94e9354fcaae95008cfd4b6ce723bde324ba883e

    public ClassAttentionVo() {
        this.timeOffset=new Date();
    }

    @Override
    public ClassAttentionVo clone() throws CloneNotSupportedException {
        ClassAttentionVo classAttentionVo=(ClassAttentionVo) super.clone();
        classAttentionVo.timeOffset=(Date) this.timeOffset.clone();
        return classAttentionVo;
    }
}
