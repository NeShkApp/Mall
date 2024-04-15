package com.example.mall.databasefiles;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mall.classes.Order;

import java.util.List;

@Dao
public interface OrderItemDao {

    @Insert
    void insert(Order order);

//    @Query("SELECT order_items.id, order_items.address, order_items.zipCode, order_items.phoneNumber, order_items.email, order_items.totalPrice, order_items.paymentMethod, order_items.success, order_items.date, order_items.price, grocery_items.availableAmount, grocery_items.rate, grocery_items.userPoint, grocery_items.popularityPoint FROM order_items INNER JOIN grocery_items ON order_items.items = grocery_items.id")
//    List<Order> getAllItems();

    @Query("SELECT * FROM order_items")
    List<Order> getAllItems();

    @Query("select * from order_items where id =:id")
    Order getItemById(int id);

//    @Query("SELECT * FROM order_items where id =:id")
//    GroceryItem getOrderById(int id);

}
