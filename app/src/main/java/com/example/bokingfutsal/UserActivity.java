package com.example.bokingfutsal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {
    private Button logout;
    private FirebaseAuth auth1;
    private FirebaseAuth.AuthStateListener authListener1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        final TextView name = findViewById(R.id.nama);
        final  TextView emaiL = findViewById(R.id.email);
        final TextView telepoN = findViewById(R.id.telepon);
        final TextView uiD = findViewById(R.id.uid);
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String data1 = sharedPreferences.getString("nama","").trim();
        String data2 = sharedPreferences.getString("email","").trim();
        String data3 = sharedPreferences.getString("telepon","").trim();
        String data4 = sharedPreferences.getString("uid","").trim();
        name.setText(data1);
        emaiL.setText(data2);
        telepoN.setText(data3);
        uiD.setText(data4);
        logout = findViewById(R.id.logout);

        logout.setOnClickListener(v -> {
            signOut();
        });

        auth1 = FirebaseAuth.getInstance();

        authListener1 = firebaseAuth -> {
            FirebaseUser user1 = firebaseAuth.getCurrentUser();
            if (user1 == null) {
                startActivity(new Intent(UserActivity.this, LoginActivity.class));
                finish();
            }
        };
    }
    public void signOut() {
        auth1.signOut();
    }
    @Override
    public void onStart() {
        super.onStart();
        auth1.addAuthStateListener(authListener1);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener1 != null) {
            auth1.removeAuthStateListener(authListener1);
        }
    }
}