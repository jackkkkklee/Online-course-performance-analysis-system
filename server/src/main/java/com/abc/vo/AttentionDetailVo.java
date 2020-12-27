package com.abc.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttentionDetailVo {
    private  int attentionValue;
    private  boolean yawnStatus;
    private  boolean sleepChance;
    private  boolean hasFace;
    private  boolean isSmoking;
    private  boolean isUsingPhone;
    private  int unClassRelatedItem;


}
