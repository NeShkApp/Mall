package com.example.mall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FirstCartFragment extends Fragment implements CartAdapter.DeleteItem, CartAdapter.TotalPrice {

    private RecyclerView recyclerView;
    private TextView txtSum, txtNoItems;
    private Button btnNext;
    private RelativeLayout itemsRelLayout;
    private CartAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_cart_fragment, container, false);
        initViews(view);
        adapter = new CartAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<GroceryItem> cartItems = Utils.getAllCartItems(getActivity());
        if(null!=cartItems){
            if(cartItems.size()>0) {
                adapter.setCartItems(cartItems);
            }else{
                txtNoItems.setVisibility(View.VISIBLE);
                itemsRelLayout.setVisibility(View.GONE);
            }
        }else{
            txtNoItems.setVisibility(View.GONE);
            itemsRelLayout.setVisibility(View.VISIBLE);
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.cartContainer, new SecondCartFragment());
                transaction.commit();
            }
        });


        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById (R.id.recViewCart);
        txtSum = view.findViewById(R.id.txtCartSum);
        txtNoItems = view.findViewById (R.id.txtNoItemCart);
        btnNext = view.findViewById(R.id.btnNextCart);
        itemsRelLayout = view.findViewById (R.id.itemsRelLay);
    }

    @Override
    public void onDeleteResult(GroceryItem item) {
        Utils.deleteFromCart(getActivity(), item);
        ArrayList<GroceryItem> cartItems = Utils.getAllCartItems(getActivity());
        if(null!=cartItems){
            if(cartItems.size()>0) {
                adapter.setCartItems(cartItems);
            }else{
                txtNoItems.setVisibility(View.VISIBLE);
                itemsRelLayout.setVisibility(View.GONE);
            }
        }else{
            txtNoItems.setVisibility(View.GONE);
            itemsRelLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void getTotalPrice(double price) {
        txtSum.setText(price + "$");
    }
}
