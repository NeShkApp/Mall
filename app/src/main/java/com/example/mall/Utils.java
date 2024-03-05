package com.example.mall;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String TAG = "Utils";

    private static Integer ID = 0;
    private static Integer ORDER_ID = 0;
    private static final String ALL_ITEMS_KEY = "all_items";
    public static final String CART_ITEMS_KEY = "cart_items";
    private static final String DB_NAME = "fake_database";
    private static Gson gson = new Gson();
    private static Type groceryListType = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
    public static void initSharedPreferences(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
        if(null==allItems){
            initAllItems(context);
        }
//        initAllItems(context);
    }

    public static void deleteFromCart(Context context, GroceryItem item){
        ArrayList<GroceryItem> cartItems = getAllCartItems(context);
        if(null!= cartItems){
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for(GroceryItem i: cartItems){
                if(i.getId() != item.getId()){
                    newItems.add(i);
                }

            }
            SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(CART_ITEMS_KEY);
            editor.putString(CART_ITEMS_KEY, gson.toJson(newItems));
            editor.commit();
        }
    }

    public static ArrayList<String> getCategories(Context context){
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if(null!=allItems){
            ArrayList<String> categories = new ArrayList<>();
            for(GroceryItem item: allItems){
                boolean doesExist = false;
                for(String s: categories){
                    if(item.getCategory().equals(s)){
                        doesExist = true;
                    }
                }
                if(!doesExist){
                    categories.add(item.getCategory());
                }
            }
            return categories;
        }
        return null;
    }

    public static ArrayList<GroceryItem> searchItemsByName(Context context, String text){
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if(null!=allItems){
            ArrayList<GroceryItem> items = new ArrayList<>();
            for(GroceryItem i: allItems){
                if(i.getName().toLowerCase().contains(text.toLowerCase())){
                    items.add(i);
                }
            }
            return items;
        }
        return null;
    }
    public static void addItemToCart(Context context, GroceryItem item){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> cartItems = gson.fromJson(sharedPreferences.getString(CART_ITEMS_KEY, null), groceryListType);
        if(null==cartItems){
            cartItems = new ArrayList<>();
        }
        cartItems.add(item);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CART_ITEMS_KEY);
        editor.putString(CART_ITEMS_KEY, gson.toJson(cartItems));
        editor.commit();
    }

    public static ArrayList<GroceryItem> getAllCartItems(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> cartItems = gson.fromJson(sharedPreferences.getString(CART_ITEMS_KEY, null), groceryListType);
        return cartItems;
    }

    public static ArrayList<Review> getReviewsById (Context context, int itemId) {
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            for (GroceryItem i : allItems) {
                if (i.getId() == itemId) {
                    ArrayList<Review> reviews = i.getReviews();
                    return reviews;
                }
            }
        }
        return null;
    }

    private static void initAllItems(Context context) {
        ArrayList<GroceryItem> allItems = new ArrayList<>();
        GroceryItem milk = new GroceryItem(
                "Milk",              // name
                "UHT milk 3.2 %",  // description
                "https://mlekpol.com.pl/wp-content/uploads/2018/08/Laciate_1L_3.2.jpg",          // imageUrl
                "Drink",             // category
                2.99,                // price
                8                 // availableAmount
        );
        allItems.add(milk);
        GroceryItem iceCream = new GroceryItem(
                "IceCream",              // name
                "Cold misterious white mass",  // description
                "https://t0.gstatic.com/licensed-image?q=tbn:ANd9GcSRA1VvVWshDqdk-IbClyNwWoUAjMnFQ5KQLb8eH0hu4gCCvfvqMEcQqOF-CTro2maW",          // imageUrl
                "Food",             // category
                4.49,                // price
                11                 // availableAmount
        );
        allItems.add(iceCream);

        GroceryItem lego = new GroceryItem(
                "Lego Creator",              // name
                "The best present for you baby",  // description
                "https://www.lego.com/cdn/cs/set/assets/bltd8807580a3e088cc/10270_alt3.jpg",          // imageUrl
                "Toys",             // category
                149.99,                // price
                5                 // availableAmount
        );
        allItems.add(lego);

        GroceryItem leatherStuff = new GroceryItem(
                "Leather stuff",              // name
                "The best present for you slave",  // description
                "https://nonickel.com/cdn/shop/products/staff-favorite-belt-set-by-nickel-smart-nonickel-com-buckle-brown_278.jpg?v=1597349879",          // imageUrl
                "Clothes",             // category
                119.49,                // price
                6                 // availableAmount
        );
        allItems.add(leatherStuff);

        GroceryItem cereal = new GroceryItem(
                "Cereal",                        // name
                "Healthy breakfast option",      // description
                "https://britishshop.pl/userdata/public/gfx/13285/Kelloggs-Plain-Wheats-Cereal-500g.jpg",  // imageUrl
                "Food",                          // category
                3.99,                            // price
                15                               // availableAmount
        );
        allItems.add(cereal);

        GroceryItem apples = new GroceryItem(
                "Apples",                        // name
                "Fresh and juicy apples",        // description
                "https://domf5oio6qrcr.cloudfront.net/medialibrary/11525/0a5ae820-7051-4495-bcca-61bf02897472.jpg",  // imageUrl
                "Food",                          // category
                1.99,                            // price
                20                               // availableAmount
        );
        allItems.add(apples);

        GroceryItem tShirt = new GroceryItem(
                "T-Shirt",                       // name
                "Casual cotton t-shirt",         // description
                "https://www.serafinandrzejak.com/wp-content/uploads/S22_0067M-scaled.jpg",  // imageUrl
                "Clothes",                       // category
                19.99,                           // price
                10                               // availableAmount
        );
        allItems.add(tShirt);

        GroceryItem puzzle = new GroceryItem(
                "Puzzle",                       // name
                "Challenging puzzle game",      // description
                "https://puzzlepremium.pl/pol_pl_Puzzle-8000-el-Miasto-marzen-9455_1.jpg",  // imageUrl
                "Toys",                         // category
                99.99,                          // price
                21                          // availableAmount
        );
        allItems.add(puzzle);

        SharedPreferences sharedPreferences = context.getSharedPreferences (DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString (ALL_ITEMS_KEY, gson.toJson (allItems));
        editor.commit();

    }

    public static ArrayList<GroceryItem> getAllItems(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
        return allItems;

    }

    public static void addReview(Context context, Review review){
        SharedPreferences sharedPreferences = context.getSharedPreferences (DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if(null!=allItems){
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for(GroceryItem i: allItems){
                if(i.getId() == review.getGroceryItemId()){
                    ArrayList<Review> reviews = i.getReviews();
                    reviews.add(review);
                    i.setReviews(reviews);
                    newItems.add(i);
                }else{
                    newItems.add(i);
                }
            }

            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItems));
            editor.commit();
        }
    }

    public static Integer getID() {
        ID++;
        return ID;
    }

    public static void clearSharedPreferences(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static void changeRate(Context context, int itemId, int newRate){
        SharedPreferences sharedPreferences = context.getSharedPreferences (DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
        if(null!=allItems){
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for(GroceryItem i: allItems){
                if(i.getId() == itemId){
                    i.setRate(newRate);
                    newItems.add(i);
                }else{
                    newItems.add(i);
                }
            }

            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItems));
            editor.commit();
        }
    }

    public static ArrayList<GroceryItem> getItemsByCategory(Context context, String category) {
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if(null!=allItems){
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for(GroceryItem item: allItems){
                if(item.getCategory().equals(category)){
                    newItems.add(item);
                }
            }
            return newItems;
        }
        return null;
    }

    public static Integer getOrderId() {
        ORDER_ID++;
        return ORDER_ID;
    }

    public static void clearCartItems(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CART_ITEMS_KEY);
        editor.commit();

    }

    public static void increasePopularityPoint(Context context, GroceryItem item, int point){
        ArrayList<GroceryItem> allItems = Utils.getAllItems(context);
        if(null!=allItems) {
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i : allItems) {
                if(i.getId() == item.getId()){
                    i.setPopularityPoint(i.getPopularityPoint() + point);
                    newItems.add(i);
                }else{
                    newItems.add(i);
                }
            }

            SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(ALL_ITEMS_KEY);
            Gson gson = new Gson();
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItems));
            editor.commit();
        }
    }

    public static void changeUserPoint(Context context, GroceryItem item, int points){
        Log.d(TAG, "changeUserPoint: changed "+ item.getName() + " user point for " + points);
        ArrayList<GroceryItem> allItems = Utils.getAllItems(context);
        if(null!=allItems){
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for(GroceryItem i:allItems){
                if(i.getId() == item.getId()){
                    i.setUserPoint(i.getUserPoint() + points);
                }
                newItems.add(i);
            }
            SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItems));
            editor.commit();
        }
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
