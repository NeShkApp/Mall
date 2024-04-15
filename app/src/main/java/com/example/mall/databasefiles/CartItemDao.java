package com.example.mall.databasefiles;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CartItemDao {
    @Query("INSERT INTO cart_items (grocery_item_id) values (:id)")
    void insert(int id);

    @Query("select grocery_items.id, grocery_items.name," +
            " grocery_items.description, grocery_items.imageUrl," +
            " grocery_items.category, grocery_items.price," +
            " grocery_items.availableAmount, grocery_items.rate, " +
            "grocery_items.userPoint, grocery_items.popularityPoint, " +
            "grocery_items.reviews, grocery_items.salePrice from grocery_items " +
            "INNER JOIN cart_items on cart_items.grocery_item_id = grocery_items.id")
    List<GroceryItem> getAllCartItems();

    @Query("delete from cart_items where grocery_item_id =:id")
    void deleteFromCart(int id);

    @Query("delete from cart_items")
    void deleteAllCart();
}
