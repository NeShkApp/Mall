package com.example.mall.databasefiles;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mall.Review;

import java.util.List;

@Dao
public interface GroceryItemDao {
    @Insert
    void insert(GroceryItem groceryItem);

    @Query("select * from grocery_items")
    List<GroceryItem> getAllItems();

    @Query("update grocery_items set rate =:newRate where id=:id")
    void updateRate(int id, int newRate);

    @Query("select * from grocery_items where id =:id")
    GroceryItem getItemById(int id);

    @Query("update grocery_items set reviews =:reviews where id=:id")
    void updateReviews(int id, String reviews);

    @Query("select distinct category from grocery_items")
    List<String> getAllCategories();

    @Query("select * from grocery_items where name LIKE :text")
    List<GroceryItem> getItemsBySearch(String text);

    @Query("select * from grocery_items where category=:text")
    List<GroceryItem> getItemsByCategory(String text);

    @Query("update grocery_items set popularityPoint =:newPoints where id=:id")
    void increasePopularityPoint(int id, int newPoints);

    @Query("update grocery_items set userPoint=:newPoints where id=:id")
    void chanweUserPoint(int id, int newPoints);

}
