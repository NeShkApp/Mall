package com.example.mall.databasefiles;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mall.Order;
import com.example.mall.Utils;

import java.util.ArrayList;

@Database(entities = {GroceryItem.class, CartItem.class, Order.class}, version = 1)
public abstract class ShopDatabase extends RoomDatabase {
    public abstract GroceryItemDao groceryItemDao();
    public abstract CartItemDao cartItemDao();
    public abstract OrderItemDao orderItemDao();

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
        public InitialAsyncTask(ShopDatabase db) {
            this.groceryItemDao = db.groceryItemDao();
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

            return null;
        }
    }
}
