package com.example.pharm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CustomerProfileUpdateDialog extends DialogFragment {

    private EditText mNameEditText, mPhoneEditText, mAgeEditText, mAddressEditText;
    private ImageView myImage;
    private Button mUpdateButton;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_customer, container, false);

        mNameEditText = view.findViewById(R.id.name);
        mPhoneEditText = view.findViewById(R.id.phone);
        mAgeEditText = view.findViewById(R.id.age);
        mAddressEditText = view.findViewById(R.id.address);
        myImage=view.findViewById(R.id.image);
        mUpdateButton = view.findViewById(R.id.edit_profile);

        // Get the current user and database reference
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        // Retrieve the user data from arguments
        Bundle args = getArguments();
        String name = args.getString("name");
        String phone = args.getString("phone");
        String age = args.getString("age");
        String address = args.getString("address");
        String imageURL = args.getString("uri");

        // Set the EditTexts with the user data
        mNameEditText.setText(name);
        mPhoneEditText.setText(phone);
        mAgeEditText.setText(age);
        mAddressEditText.setText(address);
        Picasso.get().load(imageURL).into(myImage);

        // Set onClickListener for the update button
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the user data from EditTexts
                String name = mNameEditText.getText().toString();
                String phone = mPhoneEditText.getText().toString();
                String age = mAgeEditText.getText().toString();
                String address = mAddressEditText.getText().toString();


                // Update the user data in the Firebase database
                mDatabase.child(currentUser.getUid()).child("name").setValue(name);
                mDatabase.child(currentUser.getUid()).child("phone").setValue(phone);
                mDatabase.child(currentUser.getUid()).child("age").setValue(age);
                mDatabase.child(currentUser.getUid()).child("address").setValue(address);


                // Notify the user that the profile has been updated
                Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                // Close the dialog
                dismiss();
            }
        });

        return view;
    }

    public static CustomerProfileUpdateDialog newInstance(String name, String phone, String age, String address, String imageUrl) {
        CustomerProfileUpdateDialog fragment = new CustomerProfileUpdateDialog();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("phone", phone);
        args.putString("age", age);
        args.putString("address", address);
        args.putString("uri", imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    public interface ProfileUpdateListener {
        void onProfileUpdated(String name, String phone, String age, String address, String imageUrl);
    }

}


