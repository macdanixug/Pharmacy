package com.example.pharm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PharmacyOrdersFragment extends Fragment {
    private RecyclerView recyclerView;
    private viewOrderAdapter cartAdapter;
    private List<CartItem> cartItems;
    private TextView user_count_textview;
    private AlertDialog dialog;
    private DatabaseReference cartItemsRef;


    public PharmacyOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pharmacy_orders, container, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setTitle("Loading Orders");
        builder.setMessage("Please wait...");
        dialog = builder.create();
        dialog.show();

        recyclerView = view.findViewById(R.id.products);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartItems = new ArrayList<>();
        cartAdapter = new viewOrderAdapter(cartItems);
        recyclerView.setAdapter(cartAdapter);

        dialog.dismiss();

        return view;

        }

}