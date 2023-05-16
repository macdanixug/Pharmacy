package com.example.pharm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private EditText email,pwd;
    private Button login;
    private TextView reset, account;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.email);
        pwd=findViewById(R.id.password);
        login=findViewById(R.id.login);
        reset=findViewById(R.id.forgot_password);
        account=findViewById(R.id.create_account);

        mAuth=FirebaseAuth.getInstance();

//        Reset Password
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, ForgotPassword.class));
            }
        });

//        Create account
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

//        Logging in a user
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email=email.getText().toString();
                String Password=pwd.getText().toString();
                if(Email.isEmpty()){
                    email.setError("Email is Required*");
                    email.requestFocus();
                    return;
                }else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    email.setError("Valid Email Required*");
                    email.requestFocus();
                    return;
                }else if(Password.isEmpty()){
                    pwd.setError("Password Required*");
                    pwd.requestFocus();
                    return;
                }else if (Password.length()<4) {
                    pwd.setError("Password Should be greater than 4 characters*");
                    pwd.requestFocus();
                    return;
                }else{

                    mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());

                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String role = dataSnapshot.child("role").getValue(String.class);
                                        if (role.equals("admin")) {
                                            // grant access to database
                                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                            Intent admin=new Intent(Login.this,Admin.class);
                                            startActivity(admin);
                                            finish();
                                        } else if(role.equals("customer")) {
                                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                            Intent customer=new Intent(Login.this,Customer.class);
                                            startActivity(customer);
                                            finish();
                                        } else if (role.equals("pharmacy")) {
                                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                            Intent pharmacy=new Intent(Login.this,Pharmacy.class);
                                            startActivity(pharmacy);
                                            finish();
                                        } else{
                                            Toast.makeText(Login.this, "Login Failed try again", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        // handle error
                                    }
                                });
                            }else{

                                Toast.makeText(Login.this, "Login Failed try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
            }
        });


    }

}