package com.example.pharm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class viewOrderAdapter extends RecyclerView.Adapter<viewOrderAdapter.myViewHolder> {
    private Context context;
    private ArrayList<CartItem> list;
    private viewOrderAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(CartItem item);
    }

    public viewOrderAdapter(Context context, List<CartItem> list) {
        this.context = context;
        this.list = (ArrayList<CartItem>) list;
    }


    public void setOnItemClickListener(viewOrderAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public viewOrderAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_order_design, parent, false);
        return new viewOrderAdapter.myViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull viewOrderAdapter.myViewHolder holder, int position) {
        CartItem model = list.get(position);
        if (model != null) {
            holder.productNameTextView.setText(model.getName());
            holder.productQuantityTextView.setText(String.valueOf(model.getQuantity()));
            holder.productPriceTextView.setText(String.valueOf(model.getPrice()));

            Log.d("TAG", "Product Name: " + model.getName());
            Log.d("TAG", "Price: " + model.getPrice());
            Log.d("TAG", "Quantity: " + model.getQuantity());
        } else {
            // Handle the case when the model is null
            Log.d("TAG", "Null model at position: " + position);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView productQuantityTextView;
        TextView productPriceTextView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productQuantityTextView = itemView.findViewById(R.id.productQuantityTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);

        }
    }

}
