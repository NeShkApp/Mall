package com.example.mall.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mall.classes.Order;
import com.example.mall.R;
import com.example.mall.activities.OrderItemActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    private ArrayList<Order> orders = new ArrayList<>();
    private Context context;
    private Fragment fragment;

    public OrderAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
    }

    public void setOrderItems(ArrayList<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtOrderDate.setText(String.valueOf(orders.get(position).getDate()));
        holder.txtOrderPrice.setText(orders.get(position).getTotalPrice()+" $");
        holder.txtOrderTime.setText(String.valueOf(orders.get(position).getTime()));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderItemActivity.class);

                Gson gson = new Gson();
                String jsonOrder = gson.toJson(orders.get(position));
                intent.putExtra("incoming_order_key", jsonOrder);
                context.startActivity(intent);
            }
        });
//        holder.image.setImageResource(orders.get(position).getImageId());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
//        private ImageView image;
        private TextView txtOrderDate, txtOrderPrice, txtOrderTime;

        private MaterialCardView parent;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderDate = itemView.findViewById(R.id.textOrderDate);
            txtOrderPrice = itemView.findViewById(R.id.txtOrderPrice);
            txtOrderTime = itemView.findViewById(R.id.txtOrderTime);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
