package com.example.mall;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.mall.databasefiles.GroceryItem;
import com.example.mall.databasefiles.ShopDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String TAG = "Utils";
    private static Integer ORDER_ID = 0;

    public static void deleteFromCart(Context context, GroceryItem item){
        ShopDatabase.getInstance(context).cartItemDao().deleteFromCart(item.getId());
    }

    public static ArrayList<String> getCategories(Context context){
        return (ArrayList<String>) ShopDatabase.getInstance(context).groceryItemDao().getAllCategories();
    }

    public static ArrayList<GroceryItem> searchItemsByName(Context context, String text){
        return (ArrayList<GroceryItem>) ShopDatabase.getInstance(context).groceryItemDao().getItemsBySearch("%" + text +"%");
    }
    public static void addItemToCart(Context context, GroceryItem item){
        ShopDatabase.getInstance(context).cartItemDao().insert(item.getId());
    }

    public static ArrayList<GroceryItem> getAllCartItems(Context context){
        return (ArrayList<GroceryItem>) ShopDatabase.getInstance(context).cartItemDao().getAllCartItems();
    }

    public static ArrayList<Review> getReviewsById (Context context, int itemId) {
        return ShopDatabase.getInstance(context).groceryItemDao().getItemById(itemId).getReviews();
    }

    public static ArrayList<GroceryItem> getAllItems(Context context){
        return (ArrayList<GroceryItem>) ShopDatabase.getInstance(context).groceryItemDao().getAllItems();

    }

    public static void addReview(Context context, Review review){

        GroceryItem groceryItem = ShopDatabase.getInstance(context).groceryItemDao().getItemById(review.getGroceryItemId());
        ArrayList<Review> reviews = groceryItem.getReviews();
        if(null == reviews){
            reviews = new ArrayList<>();
        }

        reviews.add(review);
        Gson gson = new Gson();
        String text = gson.toJson(reviews);
        ShopDatabase.getInstance(context).groceryItemDao().updateReviews(review.getGroceryItemId(), text);

    }

    public static void changeRate(Context context, int itemId, int newRate){
        ShopDatabase.getInstance(context).groceryItemDao().updateRate(itemId, newRate);
    }

    public static ArrayList<GroceryItem> getItemsByCategory(Context context, String category) {
        return (ArrayList<GroceryItem>) ShopDatabase.getInstance(context).groceryItemDao().getItemsByCategory(category);
    }

    public static Integer getOrderId() {
        ORDER_ID++;
        return ORDER_ID;
    }

    public static void clearCartItems(Context context){
        ShopDatabase.getInstance(context).cartItemDao().deleteAllCart();
    }

    public static void increasePopularityPoint(Context context, GroceryItem item, int point){

        int newPoints = item.getPopularityPoint() + point;
        ShopDatabase.getInstance(context).groceryItemDao().increasePopularityPoint(item.getId(), newPoints);
    }

    public static void changeUserPoint(Context context, GroceryItem item, int points){
        int newPoints = item.getUserPoint() + points;
        ShopDatabase.getInstance(context).groceryItemDao().chanweUserPoint(item.getId(), newPoints);
    }


    public static String getLicences(){
        String licences = "";
        licences+="TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION\n" + "\n" +
                "   1. Definitions.\n" +
                "\n" +
                "      \"License\" shall mean the terms and conditions for use, reproduction,\n" +
                "      and distribution as defined by Sections 1 through 9 of this document.\n" +
                "\n" +
                "      \"Licensor\" shall mean the copyright owner or entity authorized by\n" +
                "      the copyright owner that is granting the License.\n" +
                "\n" +
                "      \"Legal Entity\" shall mean the union of the acting entity and all\n" +
                "      other entities that control, are controlled by, or are under common\n" +
                "      control with that entity. For the purposes of this definition,\n" +
                "      \"control\" means (i) the power, direct or indirect, to cause the\n" +
                "      direction or management of such entity, whether by contract or\n" +
                "      otherwise, or (ii) ownership of fifty percent (50%) or more of the\n" +
                "      outstanding shares, or (iii) beneficial ownership of such entity.";
        return licences;
    }
}
