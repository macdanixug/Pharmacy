package com.example.pharm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Loading Data");
        builder.setMessage("Please wait......");
        AlertDialog dialog = builder.create();
        dialog.show();

        // Retrieve the pharmacy name from the intent
        Intent retrieve = getIntent();
        String pharmacyName = retrieve.getStringExtra("pharmacy").trim();

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
                            twitter.setText("@"+Twitter);

                            dialog.dismiss();

                            whatsap.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String whatsapHandle = pharmacy.getTwitter();
                                    if (whatsapHandle != null){
                                        String phoneNumber = pharmacy.getWhatsap();
                                        String whatsappLink = "https://wa.me/" + phoneNumber;

                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(whatsappLink));
                                        PackageManager packageManager = getPackageManager();
                                        if (intent.resolveActivity(packageManager) != null) {
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(PharmacyContacts.this, "No app found to handle the request", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else {
                                        whatsap.setText("Whatsapp Number");
                                    }
                                }
                            });

                            Pattern pattern = Pattern.compile("\\+?[0-9]+");
                            Linkify.addLinks(whatsap, pattern, "https://wa.me/");
                            whatsap.setMovementMethod(LinkMovementMethod.getInstance());

                            twitter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String twitterHandle = pharmacy.getTwitter();
                                    if (twitterHandle != null) {
                                        try {
                                            String encodedTwitterHandle = URLEncoder.encode(twitterHandle, "UTF-8");
                                            String twitterLink = "https://twitter.com/" + encodedTwitterHandle;

                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterLink));
                                            startActivity(intent);
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    else {
                                        twitter.setText("Twitter Handle");
                                    }

                                }
                            });

                            facebook.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String facebookHandle = pharmacy.getFacebook();
                                    if (facebookHandle !=null){
                                        try {
                                            String encodedFacebookHandle = URLEncoder.encode(facebookHandle, "UTF-8");
                                            String facebookLink = encodedFacebookHandle;

                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookLink));
                                            startActivity(intent);
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    else{
                                        facebook.setText("No facebook handle");
                                    }
                                }
                            });

                            email.setAutoLinkMask(Linkify.EMAIL_ADDRESSES);
                            email.setMovementMethod(LinkMovementMethod.getInstance());

                            twitter.setAutoLinkMask(Linkify.WEB_URLS);
                            twitter.setMovementMethod(LinkMovementMethod.getInstance());

                            facebook.setAutoLinkMask(Linkify.WEB_URLS);
                            facebook.setMovementMethod(LinkMovementMethod.getInstance());

                            number.setAutoLinkMask(Linkify.PHONE_NUMBERS);
                            number.setMovementMethod(LinkMovementMethod.getInstance());


                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dialog.dismiss();
            }
        });
    }
}