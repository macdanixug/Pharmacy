package com.example.pharm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder> {
    private ArrayList<Drug> drugs;
    private Context context;
    private ArrayList<CartItem> cartItems = new ArrayList<>();

    public ShoppingCartAdapter(Context context, List<Drug> drugs) {
        this.drugs = (ArrayList<Drug>) drugs;
        this.context = context;
    }

    @NonNull
    @Override
    public ShoppingCartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Drug Design Layout
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.available_products, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Drug myDrugs=drugs.get(position);
        holder.drug_name.setText(myDrugs.getName());
        holder.price.setText(myDrugs.getPrice());
        holder.description.setText(myDrugs.getDescription());
        holder.child.setText(myDrugs.getChild());
        holder.old.setText(myDrugs.getOld());

        String imageUri=myDrugs.getImageUrl();
        Picasso.get().load(imageUri).into(holder.drug_image);

        // Add to cart Button
        Drug drug = drugs.get(position);
        holder.add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartItem cartItem = new CartItem(drug.getName(), 1, Double.parseDouble(drug.getPrice()),drug.getImageUrl());
                cartItems.add(cartItem);
                Toast.makeText(view.getContext(), drug.getName()+"has been added to cart", Toast.LENGTH_SHORT).show();
//
//                // pass the updated cart to the MyCart activity
                Intent intent = new Intent(context, MyCart.class);
                intent.putParcelableArrayListExtra("cartItems", cartItems);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView drug_image;
        private TextView drug_name, price, description, child, old;
        private Button add_to_cart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            drug_image = itemView.findViewById(R.id.drug_image);
            drug_name = itemView.findViewById(R.id.drug_name);
            price = itemView.findViewById(R.id.price);
            description = itemView.findViewById(R.id.description);
            child = itemView.findViewById(R.id.child);
            old = itemView.findViewById(R.id.old);

            add_to_cart = itemView.findViewById(R.id.order);
        }
    }


}
