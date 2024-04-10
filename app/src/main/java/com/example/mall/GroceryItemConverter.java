package com.example.mall;

import androidx.room.TypeConverter;

import com.example.mall.databasefiles.GroceryItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GroceryItemConverter {
    @TypeConverter
    public String groceryItemsToJson(ArrayList<GroceryItem> groceryItems) {
        Gson gson = new Gson();
        return gson.toJson(groceryItems);
    }

    @TypeConverter
    public ArrayList<GroceryItem> jsonToGroceryItems(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
        return gson.fromJson(json, type);
    }

}
