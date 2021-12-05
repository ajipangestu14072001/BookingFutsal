package com.example.bokingfutsal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowActivity extends AppCompatActivity {
    ImageView down_arrow;
    ScrollView third_scrollview;
    Animation from_bottom;
    TextView judul, des, fasilitas, harga;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        down_arrow = findViewById(R.id.down_arrow);
        third_scrollview = findViewById(R.id.third_scrillview);
        from_bottom = AnimationUtils.loadAnimation(this, R.anim.anim_from_bottom);
        down_arrow.setAnimation(from_bottom);
        third_scrollview.setAnimation(from_bottom);
        judul = findViewById(R.id.title);
        des = findViewById(R.id.destext);
        fasilitas = findViewById(R.id.fasilitastext);
        harga = findViewById(R.id.costtext);

        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String data1 = sharedPreferences.getString("data1","").trim();
        String data2 = sharedPreferences.getString("data2","").trim();
        String data3 = sharedPreferences.getString("data3","").trim();

        judul.setText(data2);
        des.setText(data1);
        fasilitas.setText(data1);
        harga.setText(data3);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        down_arrow.setOnClickListener(view -> {
            Intent intent = new Intent(ShowActivity.this, DetailActivity.class);
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View, String>(down_arrow, "background_image_transition");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ShowActivity.this, pairs);
            startActivity(intent, options.toBundle());
        });
    }
}