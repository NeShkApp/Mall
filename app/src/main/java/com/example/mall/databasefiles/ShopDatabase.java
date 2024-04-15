package com.example.mall.databasefiles;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mall.activities.MapActivity;
import com.example.mall.classes.Order;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Database(entities = {GroceryItem.class, CartItem.class, Order.class, ShopModel.class}, version = 1)
public abstract class ShopDatabase extends RoomDatabase {
    public abstract GroceryItemDao groceryItemDao();
    public abstract CartItemDao cartItemDao();
    public abstract OrderItemDao orderItemDao();
    public abstract ShopModelDao shopItemDao();

    private static ShopDatabase instance;

    public static synchronized ShopDatabase getInstance(Context context){
        if(null==instance){
            instance = Room.databaseBuilder(context, ShopDatabase.class, "shop_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(initialCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback initialCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new InitialAsyncTask(instance).execute();

        }
    };

    private static class InitialAsyncTask extends AsyncTask<Void, Void, Void>{

        private GroceryItemDao groceryItemDao;
        private ShopModelDao shopModelDao;
        public InitialAsyncTask(ShopDatabase db) {
            this.groceryItemDao = db.groceryItemDao();
            this.shopModelDao = db.shopItemDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
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

            for(GroceryItem g: allItems){
                groceryItemDao.insert(g);
            }

            //adding the shops into db

            ArrayList<ShopModel> shops = new ArrayList<>();

            ShopModel shop1 = new ShopModel(50.73048126207275, 25.30057816952877, "8:00 - 20:00", "8:00 - 16:00", "closed");
            shops.add(shop1);
            ShopModel shop2 = new ShopModel(50.72663708797687, 25.296426649614478, "8:00 - 18:00", "8:00 - 19:00", "8:00 - 15:00");
            shops.add(shop2);
            ShopModel shop3 = new ShopModel(50.7298631722636, 25.295937454789044, "8:00 - 21:00", "8:00 - 16:00", "closed");
            shops.add(shop3);
            ShopModel shop4 = new ShopModel(50.72420122206805, 25.29090972435486, "8:00 - 18:00", "8:00 - 18:00", "8:00 - 15:00");
            shops.add(shop4);
            ShopModel shop5 = new ShopModel(50.721245972386185, 25.311202678485042, "8:00 - 20:00", "8:00 - 16:00", "closed");
            shops.add(shop5);
            ShopModel shop6 = new ShopModel(50.726210664407134, 25.311262835944962, "8:00 - 18:00", "8:00 - 18:00", "8:00 - 15:00");
            shops.add(shop6);
            ShopModel shop7 = new ShopModel(50.7240939003531, 25.31936316902425, "8:00 - 21:00", "8:00 - 19:00", "closed");
            shops.add(shop7);
            ShopModel shop8 = new ShopModel(50.724969627266134, 25.304730059563568, "8:00 - 18:00", "8:00 - 16:00", "8:00 - 15:00");
            shops.add(shop8);
            ShopModel shop9 = new ShopModel(50.746125, 25.335278, "8:00 - 18:00", "8:00 - 18:00", "closed");
            shops.add(shop9);
            ShopModel shop10 = new ShopModel(50.734577, 25.316472, "8:00 - 18:00", "8:00 - 16:00", "8:00 - 15:00");
            shops.add(shop10);
            ShopModel shop11 = new ShopModel(50.724359, 25.301849, "8:00 - 21:00", "8:00 - 18:00", "8:00 - 15:00");
            shops.add(shop11);
            ShopModel shop12 = new ShopModel(50.738755, 25.323579, "20:00 - 18:00", "8:00 - 16:00", "closed");
            shops.add(shop12);

            for(ShopModel s: shops){
                shopModelDao.insert(s);
            }

            return null;
        }
    }
}
