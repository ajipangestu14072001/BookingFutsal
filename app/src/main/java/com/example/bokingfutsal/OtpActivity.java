package com.example.bokingfutsal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OtpActivity extends AppCompatActivity {
private EditText editText;
private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        editText = findViewById(R.id.edittextotp);
        button = findViewById(R.id.btnvalidasi);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
                String OTP = sharedPreferences.getString("code","").trim();
                String getCode = editText.getText().toString().trim();
                if (getCode.equals(OTP)){
                    Intent intent = new Intent(OtpActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "OTP Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}