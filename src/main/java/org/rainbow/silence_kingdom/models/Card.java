package org.rainbow.silence_kingdom.models;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/10/15.
 * Time: 下午5:54.
 * Description:
 */
public class Card {
    public static final int ACQUIRED = 1;
    public static final int NOT_ACQUIRED = 0;

    private int id;
    private String cardName;
    private String imagePath;
    private String smallImagePath;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSmallImagePath() {
        return smallImagePath;
    }

    public void setSmallImagePath(String smallImagePath) {
        this.smallImagePath = smallImagePath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardName='" + cardName + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", smallImagePath='" + smallImagePath + '\'' +
                ", status=" + status +
                '}';
    }
}
