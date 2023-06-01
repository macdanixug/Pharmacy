package com.example.pharm;

import android.content.Context;
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

import java.util.ArrayList;

public class viewOrderAdapter extends RecyclerView.Adapter<viewOrderAdapter.myViewHolder> {
    private Context context;
    private ArrayList<String> userIdList;
    private viewOrderAdapter.OnItemClickListener mListener;
    private DatabaseReference usersRef;

    public interface OnItemClickListener {
        void onItemClick(String userId);
    }

    public viewOrderAdapter(Context context, ArrayList<String> userIdList, DatabaseReference usersRef) {
        this.context = context;
        this.userIdList = userIdList;
        this.usersRef = usersRef;
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
    public void onBindViewHolder(@NonNull final viewOrderAdapter.myViewHolder holder, int position) {
        String userId = userIdList.get(position);

        // Retrieve the name for the corresponding user ID from the database
        usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    holder.userIdTextView.setText(name); // Set the name in the TextView
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    @Override
    public int getItemCount() {
        return userIdList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView userIdTextView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            userIdTextView = itemView.findViewById(R.id.userIdTextView);
        }
    }
}