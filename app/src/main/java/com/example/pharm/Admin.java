package com.example.pharm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Admin extends AppCompatActivity {
    private CardView add, exit, view,profile;
    private CircleImageView profilePicture;
    private TextView admin_name;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        add=findViewById(R.id.add);
        exit=findViewById(R.id.logout);
        view=findViewById(R.id.view);
        profile=findViewById(R.id.profile);
        profilePicture=findViewById(R.id.profilePicture);
        admin_name=findViewById(R.id.admin_name);

        mAuth=FirebaseAuth.getInstance();

//        Viewing Pharmacies
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin.this, AdminViewPharmacies.class));
                finish();
            }
        });

//        Adding Pharmacy
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin.this, AdminAddPharmacies.class));
                finish();
            }
        });

//        Login
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(Admin.this, "Logged Out successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Admin.this, Login.class));
                finish();
            }
        });

    }
}