package com.example.mall;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    public interface TotalPrice{
        void getTotalPrice(double price);
    }

    public interface DeleteItem{
        void onDeleteResult(GroceryItem item);
    }
    private DeleteItem deleteItem;
    private TotalPrice totalPrice;

    private ArrayList<GroceryItem> cartItems = new ArrayList<>();
    private Context context;
    private Fragment fragment;

    public CartAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
    }

    public void setCartItems(ArrayList<GroceryItem> cartItems) {
        this.cartItems = cartItems;
        calculateSumPrice();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtItemName.setText(cartItems.get(position).getName());
        holder.txtItemPrice.setText(cartItems.get(position).getPrice() + "$");
        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setMessage("Are you sure?")
                        .setTitle("Deleting the item")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    deleteItem = (DeleteItem) fragment;
                                    deleteItem.onDeleteResult(cartItems.get(position));
                                }catch (ClassCastException e){
                                    e.printStackTrace();
                                }
                            }
                        });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    private void calculateSumPrice(){
        double price = 0;
        for(GroceryItem item: cartItems){
            price+=item.getPrice();
        }
        price = Math.round(price * 100.0)/100.0;

        try{
            totalPrice = (TotalPrice) fragment;
            totalPrice.getTotalPrice(price);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtItemName, txtItemPrice, txtDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.txtItemNameCart);
            txtItemPrice = itemView.findViewById(R.id.txtItemPrice);
            txtDelete = itemView.findViewById(R.id.txtDelete);
        }
    }
}
