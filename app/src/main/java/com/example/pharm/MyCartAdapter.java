package com.example.pharm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyViewHolder> {
    private ArrayList<CartItem> cartItems;
    private Context context;

    public MyCartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems= (ArrayList<CartItem>) cartItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.cartItemName.setText(cartItem.getProductName());
        holder.cartItemQuantity.setText("1");
        holder.cartItemPrice.setText("Ugx: " + String.valueOf(cartItem.getPrice()));

        String imageUri=cartItem.getImageUrl();
        if (imageUri != null) {
            Picasso.get().load(imageUri).into(holder.itemImage);
        } else {
            holder.itemImage.setImageResource(R.drawable.out); // Set a placeholder image
        }


        // Remove item button
        holder.remove_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    removeItem(position);
                }
            }
        });

//        Reduce Quantity
        holder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    CartItem cartItem = cartItems.get(position);
                    int currentQuantity = cartItem.getQuantity();
                    if (currentQuantity > 1) {
                        cartItem.setQuantity(currentQuantity - 1);
                        holder.cartItemQuantity.setText(String.valueOf(cartItem.getQuantity()));
                    } else {
                        Toast.makeText(context, "Minimum quantity reached", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


//        Increase Quantity
        holder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    CartItem cartItem = cartItems.get(position);
                    int currentQuantity = cartItem.getQuantity();
                    if (currentQuantity < 10) {
                        cartItem.setQuantity(currentQuantity + 1);
                        holder.cartItemQuantity.setText(String.valueOf(cartItem.getQuantity()));
                    } else {
                        Toast.makeText(context, "Maximum quantity reached", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView cartItemName;
        public TextView cartItemQuantity;
        public TextView cartItemPrice;
        public ImageView itemImage;
        public ImageView remove_item;
        public Button reduce, increase;

        public MyViewHolder(View view) {
            super(view);
            cartItemName = view.findViewById(R.id.cart_item_name);
            cartItemQuantity = view.findViewById(R.id.cart_item_quantity);
            cartItemPrice = view.findViewById(R.id.cart_item_price);
            itemImage = view.findViewById(R.id.cart_item_image);
            remove_item = view.findViewById(R.id.remove_item);
            reduce=view.findViewById(R.id.reduce);
            increase=view.findViewById(R.id.increase);

        }
    }

    // Remove item from cart
    public void removeItem(int index) {
        if (index < 0 || index >= cartItems.size()) {
            return; // index out of bounds
        }

        cartItems.remove(index);
        notifyItemRemoved(index);
    }

    // Remove item from cart by item object
    public void removeItem(CartItem item) {
        int index = cartItems.indexOf(item);
        if (index >= 0) {
            removeItem(index);
        }
    }




}
