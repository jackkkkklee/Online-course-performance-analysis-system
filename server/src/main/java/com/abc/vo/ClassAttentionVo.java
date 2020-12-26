package com.abc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor

public class ClassAttentionVo implements Cloneable {
    private int attention_value;
    private Date timeOffset;
    private String cid;

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
