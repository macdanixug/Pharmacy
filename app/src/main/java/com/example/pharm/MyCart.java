package com.example.pharm;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharm.CartItem;
import com.example.pharm.MyCartAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyCart extends AppCompatActivity {
    private ArrayList<CartItem> cartItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyCartAdapter cartAdapter;
    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        total = findViewById(R.id.total);

        recyclerView = findViewById(R.id.myCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve the cart items from Shared Preferences
        cartItems = getCartItemsFromSharedPrefs();

        // Set up the CartAdapter with the cart items
        cartAdapter = new MyCartAdapter(this, cartItems, getSharedPreferences("CartPreferences", Context.MODE_PRIVATE), total);
        recyclerView.setAdapter(cartAdapter);

        // Calculate the total cost
        double totalCost = cartAdapter.calculateTotalCost();
        total.setText("Ugx: " + totalCost);
    }

    // Retrieve cart items from Shared Preferences
    private ArrayList<CartItem> getCartItemsFromSharedPrefs() {
        // Replace "CartPreferences" with your desired preference name
        SharedPreferences sharedPreferences = getSharedPreferences("CartPreferences", Context.MODE_PRIVATE);
        String jsonCartItems = sharedPreferences.getString("cart_items", "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<CartItem>>() {}.getType();
        return gson.fromJson(jsonCartItems, type);
    }
}
