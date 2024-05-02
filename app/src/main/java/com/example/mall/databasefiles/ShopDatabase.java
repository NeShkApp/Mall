package com.example.mall.databasefiles;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mall.Utils;
import com.example.mall.activities.MapActivity;
import com.example.mall.adapters.CartAdapter;
import com.example.mall.classes.Order;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Database(entities = {GroceryItem.class, CartItem.class, Order.class, ShopModel.class}, version = 1)
public abstract class ShopDatabase extends RoomDatabase{
    private static final String TAG = "ShopDatabase";
    public abstract GroceryItemDao groceryItemDao();
    public abstract CartItemDao cartItemDao();
    public abstract OrderItemDao orderItemDao();
    public abstract ShopModelDao shopItemDao();

    private static ShopDatabase instance;

    public static synchronized ShopDatabase getInstance(Context context){
//        if(null==instance){
//            instance = Room.databaseBuilder(context, ShopDatabase.class, "shop_db")
//                    .fallbackToDestructiveMigration()
//                    .allowMainThreadQueries()
//                    .addCallback(initialCallback)
//                    .build();
//        }
        instance = Room.databaseBuilder(context, ShopDatabase.class, "shop_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .addCallback(initialCallback)
                .build();
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


            //grocery
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("GroceryItems");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for (DataSnapshot child : children) {

                        GroceryItem groceryItem = child.getValue(GroceryItem.class);
                        groceryItemDao.insert(groceryItem);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "Failed to read Grocery value.", databaseError.toException());
                }
            });
            //stores

            DatabaseReference ref2 = database.getReference("Stores");
            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for (DataSnapshot child : children) {

                        ShopModel shopModel = child.getValue(ShopModel.class);
                        shopModelDao.insert(shopModel);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "Failed to read Stores value.", databaseError.toException());
                }
            });


//            ArrayList<ShopModel> shops = new ArrayList<>();
//
//            //lutsk
//
//            ShopModel shop1 = new ShopModel(50.73048126207275, 25.30057816952877, "8:00 - 20:00", "8:00 - 16:00", "closed");
//            shops.add(shop1);
//            ShopModel shop2 = new ShopModel(50.72663708797687, 25.296426649614478, "8:00 - 18:00", "8:00 - 19:00", "8:00 - 15:00");
//            shops.add(shop2);
//            ShopModel shop3 = new ShopModel(50.7298631722636, 25.295937454789044, "8:00 - 21:00", "8:00 - 16:00", "closed");
//            shops.add(shop3);
//            ShopModel shop4 = new ShopModel(50.72420122206805, 25.29090972435486, "8:00 - 18:00", "8:00 - 18:00", "8:00 - 15:00");
//            shops.add(shop4);
//            ShopModel shop5 = new ShopModel(50.721245972386185, 25.311202678485042, "8:00 - 20:00", "8:00 - 16:00", "closed");
//            shops.add(shop5);
//            ShopModel shop6 = new ShopModel(50.726210664407134, 25.311262835944962, "8:00 - 18:00", "8:00 - 18:00", "8:00 - 15:00");
//            shops.add(shop6);
//            ShopModel shop7 = new ShopModel(50.7240939003531, 25.31936316902425, "8:00 - 21:00", "8:00 - 19:00", "closed");
//            shops.add(shop7);
//            ShopModel shop8 = new ShopModel(50.724969627266134, 25.304730059563568, "8:00 - 18:00", "8:00 - 16:00", "8:00 - 15:00");
//            shops.add(shop8);
//            ShopModel shop9 = new ShopModel(50.746125, 25.335278, "8:00 - 18:00", "8:00 - 18:00", "closed");
//            shops.add(shop9);
//            ShopModel shop10 = new ShopModel(50.734577, 25.316472, "8:00 - 18:00", "8:00 - 16:00", "8:00 - 15:00");
//            shops.add(shop10);
//            ShopModel shop11 = new ShopModel(50.724359, 25.301849, "8:00 - 21:00", "8:00 - 18:00", "8:00 - 15:00");
//            shops.add(shop11);
//            ShopModel shop12 = new ShopModel(50.738755, 25.323579, "20:00 - 18:00", "8:00 - 16:00", "closed");
//            shops.add(shop12);
//
//            //czestochowa
//
//            ShopModel shop13 = new ShopModel(50.801769, 19.138557, "8:00 - 20:00", "8:00 - 16:00", "closed");
//            shops.add(shop13);
//
//            ShopModel shop14 = new ShopModel(50.806637, 19.144664, "8:00 - 20:00", "8:00 - 16:00", "closed");
//            shops.add(shop14);



//            for(ShopModel s: shops){
//                shopModelDao.insert(s);
//            }

            return null;
        }

    }


}
