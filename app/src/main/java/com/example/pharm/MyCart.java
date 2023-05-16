package com.example.pharm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyCart extends AppCompatActivity {
    private ArrayList<CartItem> cartItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyCartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        recyclerView = findViewById(R.id.myCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//        // Get the cart items from the Intent
        cartItems = getIntent().getParcelableArrayListExtra("cartItems");


        // Set up the CartAdapter with the cart items
        cartAdapter = new MyCartAdapter(this, cartItems);
        recyclerView.setAdapter(cartAdapter);
    }



}
