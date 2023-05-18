package com.example.pharm;

import android.app.AlertDialog; // changed to use AlertDialog from app package
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerHomeFragment extends Fragment {
    private DatabaseReference databaseReference;
    private List<Drug> myDrugs;
    private RecyclerView drugs;
    private ValueEventListener eventListener;
    private CustomerHomeAdapter adapter;
    private AlertDialog dialog;

    public CustomerHomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_customer_home, container, false);

        // add view.findViewById to find view in inflated layout
        drugs=view.findViewById(R.id.drugs);
        drugs.setHasFixedSize(true);
        // Setting its Layout
        drugs.setLayoutManager(new LinearLayoutManager(getContext())); // pass context instead of this

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); // pass context instead of this
        builder.setCancelable(false);
        builder.setTitle("Loading Data");
        builder.setMessage("Please wait......");
        dialog = builder.create(); // assign to class level variable
        dialog.show();

        myDrugs = new ArrayList<>();

        adapter = new CustomerHomeAdapter(getContext(), myDrugs); // pass context instead of this
        drugs.setAdapter(adapter);

        // Retrieving all users with role pharmacy
        databaseReference = FirebaseDatabase.getInstance().getReference("drugs");
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myDrugs.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    Drug drugs = itemSnapshot.getValue(Drug.class);

                    myDrugs.add(drugs);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });


        return view; // return the inflated view
    }

}
