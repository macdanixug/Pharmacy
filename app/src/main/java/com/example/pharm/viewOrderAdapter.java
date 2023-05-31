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
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class viewOrderAdapter extends RecyclerView.Adapter<viewOrderAdapter.ViewHolder> {
    private DatabaseReference productsRef;

    public viewOrderAdapter(DatabaseReference productsRef) {
        this.productsRef = productsRef;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView productQuantityTextView;
        TextView productPriceTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productQuantityTextView = itemView.findViewById(R.id.productQuantityTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_order_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DatabaseReference orderProductsRef = productsRef.child("Order");

        orderProductsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StringBuilder productNameBuilder = new StringBuilder();
                StringBuilder productQuantityBuilder = new StringBuilder();
                StringBuilder productPriceBuilder = new StringBuilder();

                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    String name = productSnapshot.child("name").getValue(String.class);
                    int quantity = productSnapshot.child("quantity").getValue(Integer.class);
                    double price = productSnapshot.child("price").getValue(Double.class);
                }

                holder.productNameTextView.setText(productNameBuilder.toString().trim());
                holder.productQuantityTextView.setText(productQuantityBuilder.toString().trim());
                holder.productPriceTextView.setText(productPriceBuilder.toString().trim());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public int getItemCount() {

        return 0;
    }
}
