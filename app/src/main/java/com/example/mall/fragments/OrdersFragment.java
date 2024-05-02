package com.example.mall.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mall.classes.Order;
import com.example.mall.R;
import com.example.mall.Utils;
import com.example.mall.adapters.OrderAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OrdersFragment extends Fragment {
    private static final String TAG = "OrdersFragment";
    private OrderAdapter adapter;
    private RecyclerView recyclerView;
    private TextView txtOrderUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_orders_fragment, container, false);
        initViews(view);
        txtOrderUser.setText("Your orders");
        adapter = new OrderAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<Order> orderItems = Utils.getAllOrdersItems(getActivity());
        if(null!=orderItems) {
            if (orderItems.size() > 0) {
                Comparator<Order> orderItemsComparator = new Comparator<Order>() {
                    @Override
                    public int compare(Order o1, Order o2) {
                        return o1.getId() - o2.getId();
                    }
                };
                Comparator<Order> reverseComparator = Collections.reverseOrder(orderItemsComparator);
                Collections.sort(orderItems, reverseComparator);
                adapter.setOrderItems(orderItems);
            }
        }

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recViewOrders);
        txtOrderUser = view.findViewById(R.id.txtOrderUser);
    }
}
