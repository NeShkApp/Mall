package com.example.mall.databasefiles;

public class Review {
    private int groceryItemId;
    private String userName;
    private String text;
    private String date;

    public Review() {
    }

    public Review(int groceryItemId, String userName, String text, String date) {
        this.groceryItemId = groceryItemId;
        this.userName = userName;
        this.text = text;
        this.date = date;
    }

    public int getGroceryItemId() {
        return groceryItemId;
    }

    public String getUserName() {
        return userName;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public void setGroceryItemId(int groceryItemId) {
        this.groceryItemId = groceryItemId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
