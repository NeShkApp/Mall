package com.example.mall.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mall.Order;
import com.example.mall.R;
import com.example.mall.Utils;
import com.example.mall.adapters.OrderAdapter;
import com.example.mall.classes.SettingItem;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {
    private OrderAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings_fragment, container, false);
        initViews(view);
        adapter = new OrderAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<Order> orders = Utils.getAllOrdersItems(getActivity());
        if(null!=orders) {
            if (orders.size() > 0) {
                adapter.setOrderItems(orders);
            }
        }
        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recViewSettings);

    }
}