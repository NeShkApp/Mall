package com.example.mall;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.mall.databasefiles.GroceryItem;

import java.util.ArrayList;

@Entity(tableName = "order_items")
public class Order implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @TypeConverters(GroceryItemConverter.class)
    private ArrayList<GroceryItem> items;
    private String address;
    private String zipCode;
    private String phoneNumber;
    private String email;
    private double totalPrice;
    private String paymentMethod;
    private boolean success;
    private String date;
    private String time;



    public Order(ArrayList<GroceryItem> items, String address, String zipCode, String phoneNumber, String email, double totalPrice, String paymentMethod, boolean success, String date, String time) {
        this.items = items;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.success = success;
        this.date = date;
        this.time = time;
    }

//    @Ignore
//    public Order(ArrayList<GroceryItem> items, String address, String zipCode, String phoneNumber, String email, double totalPrice, String paymentMethod, boolean success, String date) {
////        this.id = Utils.getOrderId();
//        this.items = new ArrayList<>();
//        this.address = address;
//        this.zipCode = zipCode;
//        this.phoneNumber = phoneNumber;
//        this.email = email;
//        this.totalPrice = totalPrice;
//        this.paymentMethod = paymentMethod;
//        this.success = success;
//        //new added
//        this.date = date;
//    }

    @Ignore
    public Order() {
    }

    @Ignore
    protected Order(Parcel in) {
        id = in.readInt();
//        items = in.createTypedArrayList(GroceryItem.CREATOR);
        address = in.readString();
        zipCode = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
        totalPrice = in.readDouble();
        paymentMethod = in.readString();
        success = in.readByte() != 0;
        date = in.readString();
        time = in.readString();
    }

    @Ignore
    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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


    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
//        parcel.writeTypedList(items);
        parcel.writeString(address);
        parcel.writeString(zipCode);
        parcel.writeString(phoneNumber);
        parcel.writeString(email);
        parcel.writeDouble(totalPrice);
        parcel.writeString(paymentMethod);
        parcel.writeByte((byte) (success ? 1 : 0));
        parcel.writeString(date);
        parcel.writeString(time);
    }
}
