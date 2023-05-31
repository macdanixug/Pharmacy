package com.example.pharm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class PharmacyOrdersFragment extends Fragment {
    private RecyclerView recyclerView;
    private viewOrderAdapter adapter;
    private List<OrderItem> orderItems;

    public PharmacyOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pharmacy_orders, container, false);

        recyclerView = view.findViewById(R.id.products);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderItems = new ArrayList<>();
        adapter = new viewOrderAdapter(orderItems);
        recyclerView.setAdapter(adapter);

        return view;

        }
}