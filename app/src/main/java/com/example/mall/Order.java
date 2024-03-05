package com.example.mall;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class Order {
    private int id;
    private ArrayList<GroceryItem> items;
    private String address;
    private String zipCode;
    private String phoneNumber;
    private String email;
    private double totalPrice;
    private String paymentMethod;
    private boolean success;

    public Order(ArrayList<GroceryItem> items, String address, String zipCode, String phoneNumber, String email, double totalPrice, String paymentMethod, boolean success) {
        this.id = Utils.getOrderId();
        this.items = items;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.success = success;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public ArrayList<GroceryItem> getItems() {
        return items;
    }

    public String getAddress() {
        return address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItems(ArrayList<GroceryItem> items) {
        this.items = items;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


}
