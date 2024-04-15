package com.example.mall.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mall.R;
import com.example.mall.Utils;
import com.example.mall.classes.Order;
import com.example.mall.databasefiles.GroceryItem;
import com.example.mall.fragments.FirstCartFragment;
import com.example.mall.fragments.ThirdCardFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SecondCartFragment extends Fragment {

    public static final String ORDER_KEY = "order";

    private EditText edtAddress, edtPhone, edtEmail;
    private Button btnBack, btnNext;
    private TextView txtWarning;
    private SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_cart_fragment, container, false);
        initViews(view);

        preferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        fillEditTextsFromPreferences();

        Bundle bundle = getArguments();
        if (null != bundle) {
            String jsonOrder = bundle.getString(ORDER_KEY);
            if(null!=jsonOrder){
                Gson gson = new Gson();
                Type type = new TypeToken<Order>(){}.getType();
                Order order = gson.fromJson(jsonOrder, type);
                if(null!=order){
                    edtAddress.setText(order.getAddress());
                    edtPhone.setText(order.getPhoneNumber());
                    edtEmail.setText(order.getEmail());
                }
            }
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.cartContainer, new FirstCartFragment());
                transaction.commit();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateData()){
                    txtWarning.setVisibility(View.GONE);

                    ArrayList<GroceryItem> cartItems = Utils.getAllCartItems(getActivity());
                    if(null!=cartItems){
                        Order order = new Order();
                        order.setItems(cartItems);
                        order.setAddress(edtAddress.getText().toString());
                        order.setEmail(edtEmail.getText().toString());
                        order.setPhoneNumber(edtPhone.getText().toString());
                        order.setTotalPrice(calculateSumPrice(cartItems));

                        Gson gson = new Gson();
                        String jsonOrder = gson.toJson(order);
                        Bundle bundle = new Bundle();
                        bundle.putString(ORDER_KEY, jsonOrder);

                        ThirdCardFragment thirdCardFragment = new ThirdCardFragment();
                        thirdCardFragment.setArguments(bundle);

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.cartContainer, thirdCardFragment);
                        transaction.commit();

                    }
                }else{
                    txtWarning.setVisibility(View.VISIBLE);
                    txtWarning.setText("Please Fill all the Blanks.");
                }
            }
        });

        return view;
    }

    private boolean validateData() {
        if(edtAddress.getText().toString().equals("")||edtEmail.getText().toString().equals("")||edtPhone.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    private void initViews(View view) {
        edtAddress = view.findViewById(R.id.edtAddress);
        edtPhone = view.findViewById(R.id.edtPhoneNumber);
        edtEmail = view.findViewById(R.id.edtEmail);
        btnBack = view.findViewById(R.id.btnBack);
        btnNext = view.findViewById(R.id.btnNext);
        txtWarning = view.findViewById(R.id.txtWarningSecond);
    }

    private double calculateSumPrice(ArrayList<GroceryItem> items){
        double price = 0;
        for(GroceryItem item: items){
            if(item.getSalePrice()==0.0) {
                price += item.getPrice();
            }else{
                price += item.getSalePrice();
            }
        }
        price = Math.round(price * 100.0)/100.0;

        return price;
    }

    private void fillEditTextsFromPreferences() {
        edtEmail.setText(preferences.getString("user_email", ""));
        edtAddress.setText(preferences.getString("user_address", ""));
        edtPhone.setText(preferences.getString("user_phone_number", ""));
    }

}
