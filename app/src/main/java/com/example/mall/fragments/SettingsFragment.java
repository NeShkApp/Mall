package com.example.mall.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button btnCheckNotPer;

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

        btnCheckNotPer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNotificationPermission();
            }
        });

        return view;
    }


    private void checkNotificationPermission(){
        NotificationManagerCompat manager = NotificationManagerCompat.from(getActivity());
        if(!manager.areNotificationsEnabled()){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle("Notifications are disabled")
                    .setMessage("Please enable the notifications")
                    .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }

                    })
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getActivity().getPackageName());
                            startActivity(intent);
                        }
                    });
            builder.show();
        }else{
            Toast.makeText(getActivity(), "Notifications are enabled", Toast.LENGTH_LONG).show();
        }
    }
    private void setStartText() {
        String testText = "";
        ArrayList<GroceryItem> groceryItems = Utils.getAllItems(getActivity());
        for(GroceryItem item: groceryItems){
            testText+="Name: "+ item.getName() + " ,Price: " + item.getPrice() + " ,Sale Price: " + item.getSalePrice() + "\n";
        }
        Log.d(TAG, "setStartText: " + testText);
    }

    private void initViews(View view) {
        btnSet = view.findViewById(R.id.btnSetSales);
        btnUnset = view.findViewById(R.id.btnUnsetSales);
        btnCheckNotPer = view.findViewById(R.id.btnCheckNotPer);
    }
}