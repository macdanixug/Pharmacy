package com.example.pharm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharm.CartItem;
import com.example.pharm.MyCartAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyCart extends AppCompatActivity {
    private ArrayList<CartItem> cartItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyCartAdapter cartAdapter;
    private TextView total;
    private Button checkoutButton;
    private DatabaseReference cartItemsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        total = findViewById(R.id.total);
        checkoutButton = findViewById(R.id.checkoutButton);

        recyclerView = findViewById(R.id.myCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartItems = getCartItemsFromSharedPrefs();
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        cartAdapter = new MyCartAdapter(this, cartItems, getSharedPreferences("CartPreferences", Context.MODE_PRIVATE), total);
        recyclerView.setAdapter(cartAdapter);

        // Calculate the total cost
        double totalCost = cartAdapter.calculateTotalCost();
        total.setText("Ugx: " + totalCost);

        cartItemsRef = FirebaseDatabase.getInstance().getReference("Orders");

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // Check if cart is empty
                    if (cartItems.isEmpty()) {
                        Toast.makeText(MyCart.this, "Your cart is empty.\n" +
                                " Please Add atleast one product", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Show confirmation dialog for checkout
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyCart.this);
                    builder.setTitle("Order");
                    builder.setMessage("Do you wish to proceed to order the products added to cart?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Process the order
                            sendCartItemsToDatabase();
                            clearCartItemsInSharedPrefs();
                            cartItems.clear();
                            cartAdapter.notifyDataSetChanged();
                            total.setText("Ugx: 0");
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Toast.makeText(MyCart.this, "Cancelled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    // User is not logged in, handle accordingly
                    AlertDialog.Builder builder4 = new AlertDialog.Builder(MyCart.this);
                    builder4.setTitle("Please Wait.......");
                    builder4.setMessage("Checking System Capabilities");

                    AlertDialog dialog = builder4.create();
                    dialog.show();

                    Intent intent = new Intent(MyCart.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

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


    private void sendCartItemsToDatabase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference orderRef = cartItemsRef.push();

            // Set user_id and timestamp for the order
            orderRef.child("user_id").setValue(userId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            String timestamp = sdf.format(new Date());
            orderRef.child("timestamp").setValue(timestamp);

            double totalAmount = 0.0;
            DatabaseReference productsRef = orderRef.child("products");

            for (CartItem item : cartItems) {
                DatabaseReference productRef = productsRef.push();

                productRef.child("name").setValue(item.getName());
                productRef.child("price").setValue(item.getPrice());
                productRef.child("quantity").setValue(item.getQuantity());

                totalAmount += item.getPrice() * item.getQuantity();
            }

            // Set the total amount for the order
            orderRef.child("total_amount").setValue(totalAmount);

            clearCartItemsInSharedPrefs();
            cartItems.clear();
            cartAdapter.notifyDataSetChanged();
            total.setText("Ugx: 0");
        }
    }


    private void clearCartItemsInSharedPrefs() {
        // Replace "CartPreferences" with your desired preference name
        SharedPreferences sharedPreferences = getSharedPreferences("CartPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("cart_items");
        editor.apply();
    }
}
