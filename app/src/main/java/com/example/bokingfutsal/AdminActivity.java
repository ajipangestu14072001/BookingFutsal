package com.example.bokingfutsal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.airbnb.lottie.L;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
private RelativeLayout relativeLayout1;
    RecyclerView.Adapter adapter;
    List<Lapangan> list = new ArrayList();
    LinearLayoutManager linearLayoutManager;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        relativeLayout1 = findViewById(R.id.r1);
        imageView = findViewById(R.id.accountad);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, LapanganActivity.class);
                startActivity(intent);
            }
        });
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        this.linearLayoutManager = linearLayoutManager2;
        linearLayoutManager2.setReverseLayout(true);
        this.linearLayoutManager.setStackFromEnd(true);
        this.recyclerView.setLayoutManager(this.linearLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        ProgressDialog progressDialog2 = new ProgressDialog(this);
        this.progressDialog = progressDialog2;
        progressDialog2.setMessage("Loading Data.");
        this.progressDialog.show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Lappangan");
        this.databaseReference = reference;
        reference.addValueEventListener(new ValueEventListener() {

            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                    AdminActivity.this.list.add((Lapangan) dataSnapshot2.getValue(Lapangan.class));
                }
                AdminActivity.this.adapter = new RecyclerViewAdapter(AdminActivity.this.getApplicationContext(), AdminActivity.this.list);
                AdminActivity.this.recyclerView.setAdapter(AdminActivity.this.adapter);
                AdminActivity.this.progressDialog.dismiss();
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(DatabaseError databaseError) {
                AdminActivity.this.progressDialog.dismiss();
            }
        });
    }
}