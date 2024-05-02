package com.example.mall.databasefiles;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "shop_items")
public class ShopModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private double latitude;
    private double longitude;
    private String workingHoursFromMonToFri;
    private String workingHoursSat;
    private String workingHoursSun;

    @Ignore
    public ShopModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public ShopModel(double latitude, double longitude, String workingHoursFromMonToFri, String workingHoursSat, String workingHoursSun) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.workingHoursFromMonToFri = workingHoursFromMonToFri;
        this.workingHoursSat = workingHoursSat;
        this.workingHoursSun = workingHoursSun;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getWorkingHoursFromMonToFri() {
        return workingHoursFromMonToFri;
    }

    public void setWorkingHoursFromMonToFri(String workingHoursFromMonToFri) {
        this.workingHoursFromMonToFri = workingHoursFromMonToFri;
    }

    public String getWorkingHoursSat() {
        return workingHoursSat;
    }

    public void setWorkingHoursSat(String workingHoursSat) {
        this.workingHoursSat = workingHoursSat;
    }

    public String getWorkingHoursSun() {
        return workingHoursSun;
    }

    public void setWorkingHoursSun(String workingHoursSun) {
        this.workingHoursSun = workingHoursSun;
    }
}
