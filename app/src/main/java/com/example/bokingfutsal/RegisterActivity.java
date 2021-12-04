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
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = findViewById(R.id.sign_up_button);
        final EditText name = findViewById(R.id.name);
        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);
        final EditText phone = findViewById(R.id.phone);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(v -> {
            if (name.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Masukkaan Nama Anda", Toast.LENGTH_SHORT).show();
            }
            if (email.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Masukkan Email",Toast.LENGTH_SHORT).show();
            }
            if (password.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Masukkan Password",Toast.LENGTH_SHORT).show();
            }
            if(phone.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Masukkan Telephon",Toast.LENGTH_SHORT).show();
            }
            if (!(name.getText().toString().isEmpty() && email.getText().toString().isEmpty() && password.getText().toString().isEmpty())){
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        String uid = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()).getUid();
                        Users users = new Users(uid, name.getText().toString(),phone.getText().toString(),email.getText().toString(),password.getText().toString(),0);

                        firebaseDatabase.getReference().child("Users").child(uid).setValue(users);
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}