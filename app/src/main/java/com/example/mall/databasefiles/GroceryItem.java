package com.example.mall.databasefiles;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.mall.Review;

import java.util.ArrayList;

@Entity(tableName = "grocery_items")
public class GroceryItem implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;
    private String imageUrl;
    private String category;
    private double price;
    //new
    private double salePrice;
    private int availableAmount;
    private int rate;
    private int userPoint;
    private int popularityPoint;
    @TypeConverters(ReviewsConverter.class)
    private ArrayList<Review> reviews;

    public GroceryItem(String name, String description, String imageUrl, String category, double price, int availableAmount, int rate, int userPoint, int popularityPoint, ArrayList<Review> reviews, double salePrice) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.price = price;
        this.availableAmount = availableAmount;
        this.rate = rate;
        this.userPoint = userPoint;
        this.popularityPoint = popularityPoint;
        this.reviews = reviews;
        //new
        this.salePrice = salePrice;
    }

    @Ignore
    public GroceryItem(String name, String description, String imageUrl, String category, double price, int availableAmount) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.price = price;
        this.salePrice = 0;
        this.availableAmount = availableAmount;
        this.rate = 0;
        this.userPoint =0;
        this.popularityPoint = 0;
        this.reviews = new ArrayList<>();
    }

    @Ignore
    protected GroceryItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        imageUrl = in.readString();
        category = in.readString();
        price = in.readDouble();
        availableAmount = in.readInt();
        rate = in.readInt();
        userPoint = in.readInt();
        popularityPoint = in.readInt();
        //new
        salePrice = in.readDouble();
    }

    @Ignore
    public static final Creator<GroceryItem> CREATOR = new Creator<GroceryItem>() {
        @Override
        public GroceryItem createFromParcel(Parcel in) {
            return new GroceryItem(in);
        }

        @Override
        public GroceryItem[] newArray(int size) {
            return new GroceryItem[size];
        }
    };

    @Ignore
    @Override
    public String toString() {
        return "GroceryItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", availableAmount=" + availableAmount +
                ", rate=" + rate +
                ", userPoint=" + userPoint +
                ", popularityPoint=" + popularityPoint +
                ", reviews=" + reviews +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    //new

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }
    //new

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailableAmount(int availableAmount) {
        this.availableAmount = availableAmount;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setUserPoint(int userPoint) {
        this.userPoint = userPoint;
    }

    public void setPopularityPoint(int popularityPoint) {
        this.popularityPoint = popularityPoint;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public int getRate() {
        return rate;
    }

    public int getUserPoint() {
        return userPoint;
    }

    public int getPopularityPoint() {
        return popularityPoint;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
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
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(imageUrl);
        parcel.writeString(category);
        parcel.writeDouble(price);
        parcel.writeInt(availableAmount);
        parcel.writeInt(rate);
        parcel.writeInt(userPoint);
        parcel.writeInt(popularityPoint);
        //new
        parcel.writeDouble(salePrice);
    }
}
