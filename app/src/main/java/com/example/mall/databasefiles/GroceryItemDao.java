package com.example.mall.databasefiles;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GroceryItemDao {
    @Insert
    void insert(GroceryItem groceryItem);

    @Query("select * from grocery_items")
    List<GroceryItem> getAllItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<GroceryItem> items);

    @Query("SELECT * FROM grocery_items")
    LiveData<List<GroceryItem>> getAllLiveGroceryItems();

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
    void changeUserPoint(int id, int newPoints);

    @Query("update grocery_items set price=:newPrice where id=:id")
    void changeUpdatePrice(int id, double newPrice);

    @Query("update grocery_items set salePrice=:newPrice where id=:id")
    void setSalePrice(int id, double newPrice);


}
