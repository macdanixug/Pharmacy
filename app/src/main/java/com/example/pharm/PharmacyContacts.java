package com.example.pharm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PharmacyContacts extends AppCompatActivity {
    TextView whatsap, facebook, number, email, twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_contacts);

        facebook = findViewById(R.id.facebook);
        whatsap = findViewById(R.id.whatsap);
        number = findViewById(R.id.call);
        twitter = findViewById(R.id.twitter);
        email = findViewById(R.id.gmail);

        // Retrieve the pharmacy name from the intent
        Intent retrieve = getIntent();
        String pharmacyName = retrieve.getStringExtra("pharmacy").trim();

        Log.d("TAG", "Pharmacy Name: "+pharmacyName);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        Query pharmaciesQuery = databaseReference.orderByChild("pharmacy").equalTo(pharmacyName);
        pharmaciesQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PharmacyRegistration pharmacy = snapshot.getValue(PharmacyRegistration.class);
                        if (pharmacy != null) {
                            String Email = pharmacy.getEmail();
                            String Whatsap = pharmacy.getWhatsap();
                            String Facebook = pharmacy.getFacebook();
                            String Twitter = pharmacy.getTwitter();
                            String Phone = pharmacy.getPhone();
                            email.setText(Email);
                            whatsap.setText(Whatsap);
                            facebook.setText(Facebook);
                            number.setText(Phone);
                            twitter.setText(Twitter);

                            email.setAutoLinkMask(Linkify.EMAIL_ADDRESSES);
                            email.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}