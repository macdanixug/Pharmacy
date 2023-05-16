package com.example.pharm;

import static com.example.pharm.Register.RESULT_OK;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class PharmacyUpdateProduct extends AppCompatActivity {
    private EditText drug_name,description,child,old,price;
    private ImageView drug_image;
    private FirebaseAuth mAuth;
    private Uri imageUri;
    private Button edit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_update_product);

        mAuth=FirebaseAuth.getInstance();

        drug_name = findViewById(R.id.drug_name);
        description = findViewById(R.id.drug_description);
        child = findViewById(R.id.child);
        old = findViewById(R.id.old);
        price = findViewById(R.id.price);
        drug_image = findViewById(R.id.drug_image);
        edit=findViewById(R.id.edit_myDrug);


        //        Getting text from intent
        Intent retrieve=getIntent();
        String Name=retrieve.getStringExtra("drug");
        String Description=retrieve.getStringExtra("description");
        String Child= retrieve.getStringExtra("child");
        String Old= retrieve.getStringExtra("old");
        String Price= retrieve.getStringExtra("price");
        String ImageURL= retrieve.getStringExtra("imageUrl");
        String key=retrieve.getStringExtra("key");

//        Loading them to text fields
        drug_name.setText(Name);
        description.setText(Description);
        child.setText(Child);
        old.setText(Old);
        price.setText(Price);

        Picasso.get().load(ImageURL).into(drug_image);

//        Picking Image from gallery
        drug_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open gallery to select image
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

//        Edit Button
        // Edit Button
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the updated values from EditText fields
                String updatedName = drug_name.getText().toString().trim();
                String updatedDescription = description.getText().toString().trim();
                String updatedChild = child.getText().toString().trim();
                String updatedOld = old.getText().toString().trim();
                String updatedPrice = price.getText().toString().trim();

                // Check if all fields are filled
                if (updatedName.isEmpty() || updatedDescription.isEmpty() || updatedChild.isEmpty()
                        || updatedOld.isEmpty() || updatedPrice.isEmpty() || imageUri == null) {
                    Toast.makeText(PharmacyUpdateProduct.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Update product information in the Firebase Realtime Database
                    DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("drugs");
                    String productKey = key;

                    StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("drug_images").child(productKey + ".jpg");

                    // Upload the new image to Firebase Storage
                    storageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get the new image URL from Firebase Storage
                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String updatedImageURL = uri.toString();
                                    String drugId = productsRef.push().getKey();

                                    // Create a new Product object with the updated values
                                    Drug updatedProduct = new Drug(drugId,productKey,updatedName, updatedDescription, updatedChild, updatedOld, updatedPrice, updatedImageURL);

                                    // Update the product in the Firebase Realtime Database
                                    productsRef.child(productKey).setValue(updatedProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(PharmacyUpdateProduct.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                                                finish(); // Finish the activity
                                            } else {
                                                Toast.makeText(PharmacyUpdateProduct.this, "Failed to update product", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PharmacyUpdateProduct.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
//End of Edit Button
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            drug_image.setImageURI(imageUri);
        }
    }
}