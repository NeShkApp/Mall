package com.example.mall.databasefiles;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mall.classes.Order;

import java.util.List;

@Dao
public interface ShopModelDao {

    @Insert
    void insert(ShopModel shop);

    @Query("SELECT * FROM shop_items")
    List<ShopModel> getAllItems();

    @Query("select * from shop_items where id =:id")
    ShopModel getItemById(int id);

}
