package com.example.mall.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mall.classes.Order;
import com.example.mall.R;
import com.example.mall.databasefiles.GroceryItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class OrderItemActivity extends AppCompatActivity {
    private static final String TAG = "OrderItemActivity";
    private SharedPreferences preferences;

    private TextView txtOrderUserAddress, txtOrderPrices, txtOrderUserNumber,
            txtOrderUserEmail, txtOrderUserPayment, txtOrderDate, txtOrderTime,
            txtMainOrderDate, txtOrderTotalPrice, txtOrderItems, txtOrderUserName;

    private ImageView imgCross;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item);

        initViews();
        preferences = getSharedPreferences("user_data", MODE_PRIVATE);

        Intent intent = getIntent();
        if (null != intent) {
            String jsonOrder = intent.getStringExtra("incoming_order_key");
            if (null != jsonOrder) {
                Gson gson = new Gson();
                Type type = new TypeToken<Order>() {
                }.getType();
                Order order = gson.fromJson(jsonOrder, type);
                if (null != order) {
                    txtOrderUserName.setText(preferences.getString("user_name", ""));
                    txtOrderUserAddress.setText(order.getAddress());
                    txtOrderUserNumber.setText(order.getPhoneNumber());
                    txtOrderUserEmail.setText(order.getEmail());
                    txtOrderUserPayment.setText(order.getPaymentMethod());
                    txtOrderDate.setText(order.getDate());
                    txtMainOrderDate.setText(order.getDate());
                    txtOrderTime.setText(order.getTime());
                    txtOrderTotalPrice.setText(order.getTotalPrice() + " $");

                    ArrayList<GroceryItem> items = order.getItems();
                    String itemsText = "";
                    for (GroceryItem i : items) {
                        itemsText += "\n\n" + i.getName();
                    }
                    txtOrderItems.setText(itemsText);
                    itemsText = "";
                    for (GroceryItem i : items) {
                        if(i.getSalePrice()==0.0) {
                            itemsText += "\n\n" + i.getPrice() + " $";
                        }else{
                            itemsText += "\n\n" + i.getSalePrice() + " $";
                        }
                    }
                    txtOrderPrices.setText(itemsText);
                }

            }

            imgCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

        }
    }

    private void initViews() {
        txtOrderUserAddress = findViewById(R.id.txtOrderUserAddress);
        txtOrderUserNumber = findViewById(R.id.txtOrderUserNumber);
        txtOrderUserEmail = findViewById(R.id.txtOrderUserEmail);
        txtOrderUserPayment = findViewById(R.id.txtOrderUserPayment);
        txtOrderDate = findViewById(R.id.txtOrderDate);
        txtOrderTime = findViewById(R.id.txtOrderTime);
        txtOrderItems = findViewById(R.id.txtOrderItems);
        txtMainOrderDate = findViewById(R.id.txtMainOrderDate);
        txtOrderTotalPrice = findViewById(R.id.txtOrderTotalPrice);
        imgCross = findViewById(R.id.imgCross);
        txtOrderPrices = findViewById(R.id.txtOrderPrices);
        txtOrderUserName = findViewById(R.id.txtOrderUserName);
    }
}