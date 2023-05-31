package com.example.pharm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class viewOrderAdapter extends RecyclerView.Adapter<viewOrderAdapter.CartViewHolder> {
    private List<CartItem> cartItems;
    private DatabaseReference cartItemsRef;
    private DatabaseReference usersRef;

    public viewOrderAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        cartItemsRef = FirebaseDatabase.getInstance().getReference("Orders");
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_order_design, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView productQuantityTextView;
        TextView productPriceTextView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productQuantityTextView = itemView.findViewById(R.id.productQuantityTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
        }

        public void bind(CartItem cartItem) {
        }
    }


}