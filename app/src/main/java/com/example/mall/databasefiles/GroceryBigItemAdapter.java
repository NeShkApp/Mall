package com.example.mall.databasefiles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mall.activities.GroceryItemActivity;
import com.example.mall.R;
import com.example.mall.databasefiles.GroceryItem;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class GroceryBigItemAdapter extends RecyclerView.Adapter<GroceryBigItemAdapter.ViewHolder>{

    private ArrayList<GroceryItem> items = new ArrayList<>();
    private Context context;
    public GroceryBigItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_item_big, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtName.setText(items.get(position).getName());
        holder.txtPrice.setText(String.valueOf(items.get(position).getPrice()) + "$");
        //new
        if (items.get(position).getSalePrice() != 0.0){
            holder.txtCrossPrice.setVisibility(View.VISIBLE);
            holder.txtSalePrice.setVisibility(View.VISIBLE);
            holder.txtSalePrice.setText(items.get(position).getSalePrice() + " $");
        }else{
            holder.txtCrossPrice.setVisibility(View.INVISIBLE);
            holder.txtSalePrice.setVisibility(View.INVISIBLE);
        }
        //new
        Glide.with(context)
                .asBitmap()
                .load(items.get(position).getImageUrl())
                .into(holder.img);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GroceryItemActivity.class);
                intent.putExtra("incoming_key", items.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<GroceryItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtPrice, txtName, txtCrossPrice, txtSalePrice;
        private ImageView img;
        private MaterialCardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtPrice = itemView.findViewById(R.id.bigProductPrice);
            this.txtName = itemView.findViewById(R.id.txtBigProductName);
            this.img = itemView.findViewById(R.id.bigProductImage);
            //new
            this.txtCrossPrice = itemView.findViewById(R.id.bigProductCrossPrice);
            this.txtSalePrice = itemView.findViewById(R.id.bigProductSalePrice);
            //new
            this.parent = itemView.findViewById(R.id.parent);
        }
    }

}
