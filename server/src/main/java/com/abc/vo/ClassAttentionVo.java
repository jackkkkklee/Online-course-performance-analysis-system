package com.abc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassAttentionVo implements Cloneable {
    private int attention_value;
    private Date timeOffset;
    private String cid;

    @Override
    public ClassAttentionVo clone() throws CloneNotSupportedException {
        ClassAttentionVo classAttentionVo=(ClassAttentionVo) super.clone();
        classAttentionVo.timeOffset=(Date) this.timeOffset.clone();
        return classAttentionVo;
    }
}
