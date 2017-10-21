package org.rainbow.silence_kingdom.models;

import java.sql.Date;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/10/15.
 * Time: 下午5:57.
 * Description:
 */
public class History {
    private int id;
    private int totalMinutes;
    private int ragePointThreshold;
    private float averageRagePoint;
    private int cardId;
    private byte success;
    private byte complete;
    private Date createdTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(int totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public int getRagePointThreshold() {
        return ragePointThreshold;
    }

    public void setRagePointThreshold(int ragePointThreshold) {
        this.ragePointThreshold = ragePointThreshold;
    }

    public float getAverageRagePoint() {
        return averageRagePoint;
    }

    public void setAverageRagePoint(float averageRagePoint) {
        this.averageRagePoint = averageRagePoint;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public byte getSuccess() {
        return success;
    }

    public void setSuccess(byte success) {
        this.success = success;
    }

    public byte getComplete() {
        return complete;
    }

    public void setComplete(byte complete) {
        this.complete = complete;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

}
