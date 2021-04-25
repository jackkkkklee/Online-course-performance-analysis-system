package com.abc.vo;

import lombok.Data;
import lombok.NoArgsConstructor;


public class AttentionDetailVo {

    public AttentionDetailVo() {
    }
    private  String expressValue;
    private  int attentionValue;
    private  boolean yawnStatus;
    private  boolean sleepChance;
    private  boolean hasFace;
    private  boolean smoking;
    private  boolean usingPhone;
    private  int unClassRelatedItem;

    public String getExpressValue() {
        return expressValue;
    }

    public void setExpressValue(String expressValue) {
        this.expressValue = expressValue;
    }



    public int getAttentionValue() {
        return attentionValue;
    }

    public void setAttentionValue(int attentionValue) {
        this.attentionValue = attentionValue;
    }

    public boolean isYawnStatus() {
        return yawnStatus;
    }

    public void setYawnStatus(boolean yawnStatus) {
        this.yawnStatus = yawnStatus;
    }

    public boolean isSleepChance() {
        return sleepChance;
    }

    public void setSleepChance(boolean sleepChance) {
        this.sleepChance = sleepChance;
    }

    public boolean isHasFace() {
        return hasFace;
    }

    public void setHasFace(boolean hasFace) {
        this.hasFace = hasFace;
    }

    public boolean isSmoking() {
        return smoking;
    }

    public void setSmoking(boolean smoking) {
        this.smoking = smoking;
    }

    public boolean isUsingPhone() {
        return usingPhone;
    }

    public void setUsingPhone(boolean usingPhone) {
        this.usingPhone = usingPhone;
    }

    public int getUnClassRelatedItem() {
        return unClassRelatedItem;
    }

    public void setUnClassRelatedItem(int unClassRelatedItem) {
        this.unClassRelatedItem = unClassRelatedItem;
    }



}
