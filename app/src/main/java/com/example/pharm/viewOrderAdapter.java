package com.example.pharm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class viewOrderAdapter extends RecyclerView.Adapter<viewOrderAdapter.ViewHolder> {
    private List<OrderItem> orderItems;

    public viewOrderAdapter(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
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
        OrderItem orderItem = orderItems.get(position);
        holder.productNameTextView.setText(orderItem.getProductName());
        holder.productQuantityTextView.setText(String.valueOf(orderItem.getProductQuantity()));
        holder.productPriceTextView.setText(String.valueOf(orderItem.getProductPrice()));
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }
}
