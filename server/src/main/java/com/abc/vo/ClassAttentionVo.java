package com.abc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ClassAttentionVo {
    private int attention_value;
    private Date timeOffset;
    private String cid;
}
