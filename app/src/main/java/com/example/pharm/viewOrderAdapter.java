package com.example.pharm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class viewOrderAdapter extends RecyclerView.Adapter<viewOrderAdapter.myViewHolder> {
    private Context context;
    private ArrayList<String> userIdList;
    private viewOrderAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(String userId);
    }

    public viewOrderAdapter(Context context, ArrayList<String> userIdList) {
        this.context = context;
        this.userIdList = userIdList;
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
        String userId = userIdList.get(position);
        holder.userIdTextView.setText(userId);
    }

    @Override
    public int getItemCount() {
        return userIdList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView productQuantityTextView;
        TextView productPriceTextView;
        TextView userIdTextView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productQuantityTextView = itemView.findViewById(R.id.productQuantityTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            userIdTextView = itemView.findViewById(R.id.userIdTextView);
        }
    }
}
