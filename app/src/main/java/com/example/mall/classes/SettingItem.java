package com.example.mall.classes;

import android.widget.ImageView;

public class SettingItem {
    private int id;
    private String text;
    private int imageId;

    public SettingItem(int id, String text, int imageId) {
        this.id = id;
        this.text = text;
        this.imageId = imageId;
    }

    public SettingItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
