package com.example.mall;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.example.mall.activities.OrderItemActivity;
import com.example.mall.classes.Order;
import com.example.mall.databasefiles.GroceryItem;
import com.example.mall.databasefiles.ShopDatabase;
import com.example.mall.databasefiles.ShopModel;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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

    public static void addOrder(Context context, Order order){
        Log.d("AddOrder", "Adding order: ID - " + order.getId() + ", Address - " + order.getAddress() + ", Total Price - " + order.getTotalPrice());
        ShopDatabase.getInstance(context).orderItemDao().insert(order);
    }

//    public static void addOrder(Context context, Order order){
//        Log.d("AddOrder", "Adding order: ID - " + order.getId() + ", Address - " + order.getAddress() + ", Total Price - " + order.getTotalPrice());
//
//        ArrayList<GroceryItem> items = order.getItems();
//
//        for (GroceryItem item : items) {
//            Log.d("AddOrder", "Item: Name - " + item.getName() + ", Quantity - " + item.getAvailableAmount() + ", Price - " + item.getPrice());
//        }
//
//        ShopDatabase.getInstance(context).orderItemDao().insert(order);
//    }





    public static ArrayList<GroceryItem> getAllCartItems(Context context){
        return (ArrayList<GroceryItem>) ShopDatabase.getInstance(context).cartItemDao().getAllCartItems();
    }

    public static ArrayList<Order> getAllOrdersItems(Context context) {
        List<Order> orders = ShopDatabase.getInstance(context).orderItemDao().getAllItems();
        if (orders != null && !orders.isEmpty()) {
//            for (Order order : orders) {
//                Log.d("GetAllOrders", "Order ID: " + order.getId() + ", Address: " + order.getAddress() + ", Total Price: " + order.getTotalPrice() + "Date:" + order.getDate());
//            }
        } else {
            Log.d("GetAllOrders", "No orders found");
        }
        return new ArrayList<>(orders);
    }

    //shops
    public static ArrayList<ShopModel> getAllShopItems(Context context){
        return (ArrayList<ShopModel>) ShopDatabase.getInstance(context).shopItemDao().getAllItems();
    }

    public static ShopModel getShopById(Context context, int id) {
        return ShopDatabase.getInstance(context).shopItemDao().getItemById(id);
    }


    public static ArrayList<Review> getReviewsById(Context context, int itemId) {
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
        ShopDatabase.getInstance(context).groceryItemDao().changeUserPoint(item.getId(), newPoints);
        Log.d(TAG, "changeUserPoint: Changed user point to: " + newPoints);
    }

    //new
    public static void changeSalePrice(Context context, int id, double newSalePrice){
        ShopDatabase.getInstance(context).groceryItemDao().setSalePrice(id, newSalePrice);
        Log.d(TAG, "changeSalePrice: Changed sale price to: " + newSalePrice);
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

    public static String getCurrentNumericDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-YYYY");
        return sdf.format(calendar.getTime());
    }

    public static String getCurrentSymbolicDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }

    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }

    public static ArrayList<GroceryItem> getGroceryItemsById(OrderItemActivity context, int id) {
        return ShopDatabase.getInstance(context).orderItemDao().getItemById(id).getItems();
    }

    //Photo saving
    public static Uri getImageUriFromImageView(ImageView imageView) {
        // Отримати URI обраного зображення з ImageView
        Uri imageUri = null;
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            imageUri = getImageUriFromBitmap(bitmap, imageView.getContext().getContentResolver());
        }
        return imageUri;
    }

    public static Uri getImageUriFromBitmap(Bitmap bitmap, ContentResolver contentResolver) {
        // Отримати URI зображення з Bitmap
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "Image", null);
        return Uri.parse(path);
    }

    public static void addSales(Context context){
        Random random = new Random();
        String text = "";
        ArrayList<GroceryItem> allItems = Utils.getAllItems(context);
        // Мінімум 50% продуктів отримують знижку
        int numItemsToDiscount = (int) Math.ceil(allItems.size() * 0.5);
        ArrayList<GroceryItem> itemsToDiscount = new ArrayList<>();

        while (itemsToDiscount.size() < numItemsToDiscount) {
            int randomIndex = random.nextInt(allItems.size());
            GroceryItem item = allItems.get(randomIndex);
            if (!itemsToDiscount.contains(item)) {
                itemsToDiscount.add(item);
            }
        }

        for (GroceryItem item : itemsToDiscount) {
            double salePrice = Math.round((item.getPrice() - item.getPrice() * 0.2) * 100.0) / 100.0;
            Utils.changeSalePrice(context, item.getId(), salePrice);
        }
        ArrayList<GroceryItem> newAllItems = Utils.getAllItems(context);
        for (GroceryItem item : newAllItems) {
            text += "Name: " + item.getName() + " ,Price: " + item.getPrice() + " ,Sale Price: " + item.getSalePrice() + "\n";
        }
        Log.d(TAG, "addSales: " + text);
    }

    public static void cancelSales(Context context){
        ArrayList<GroceryItem> allItems = Utils.getAllItems(context);
        for(GroceryItem item: allItems){
            Utils.changeSalePrice(context, item.getId(), 0);
        }
        Log.d(TAG, "cancelSales: sales canceled");
    }

}
