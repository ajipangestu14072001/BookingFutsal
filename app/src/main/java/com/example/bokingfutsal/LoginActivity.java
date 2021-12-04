package com.example.bokingfutsal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
private Button button1, button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button1 = findViewById(R.id.btn_login);
        button2 = findViewById(R.id.btn_signup);
        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);

        button1.setOnClickListener(v -> {
            if (email.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Email Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            }
            if (password.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            }
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            if (!(email.getText().toString().isEmpty() && password.getText().toString().isEmpty())){
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(task -> {
                    Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()).getUid();
                    if (task.isSuccessful()){
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        firebaseDatabase.getReference().child("Users").child("userType").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int userType =  snapshot.getValue(Integer.class);
                                if (userType == 0){
                                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(getApplicationContext(),"ADMIN", Toast.LENGTH_SHORT).show();
                                }

                                if(userType == 1){
                                    Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(getApplicationContext(), "USER", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });
            }

        });

        button2.setOnClickListener(v -> {
           Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
           startActivity(intent);
        });
    }
}