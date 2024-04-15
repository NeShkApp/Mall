package com.example.mall.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mall.classes.Order;
import com.example.mall.R;
import com.example.mall.Utils;
import com.example.mall.adapters.OrderAdapter;
import com.example.mall.databasefiles.GroceryItem;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

import okhttp3.internal.Util;

public class SettingsFragment extends Fragment {
    private static final String TAG = "SettingsFragment";
    private Button btnSet, btnUnset;
    private TextView txtHiUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings_fragment, container, false);
        initViews(view);

        setStartText();
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.cancelSales(getActivity());
                Utils.addSales(getActivity());
                setStartText();
            }
        });
        btnUnset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.cancelSales(getActivity());
                setStartText();
            }
        });
        return view;
    }

    private void setStartText() {
        String testText = "";
        ArrayList<GroceryItem> groceryItems = Utils.getAllItems(getActivity());
        for(GroceryItem item: groceryItems){
            testText+="Name: "+ item.getName() + " ,Price: " + item.getPrice() + " ,Sale Price: " + item.getSalePrice() + "\n";
        }
        txtHiUser.setText(testText);
    }

    private void initViews(View view) {
        btnSet = view.findViewById(R.id.btnSetSales);
        btnUnset = view.findViewById(R.id.btnUnsetSales);
        txtHiUser = view.findViewById(R.id.txtHiUser);
    }
}