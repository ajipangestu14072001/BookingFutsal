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
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    ImageView second_back_arrow, second_arrow_up, imgLoad;
    TextView second_title, second_subtitle, more_details;
    Animation from_left, from_right, from_bottom;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        second_back_arrow = findViewById(R.id.second_back_arrow);
        second_arrow_up = findViewById(R.id.seconf_arrow_up);
        second_title = findViewById(R.id.second_title1);
        second_subtitle = findViewById(R.id.second_subtitle1);
        more_details = findViewById(R.id.more_details);
        imgLoad = findViewById(R.id.imgload);
        loaddata();
        second_back_arrow.setOnClickListener(view -> {
            Intent intent = new Intent(DetailActivity.this, AdminActivity.class);
            startActivity(intent);
        });
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
        from_left = AnimationUtils.loadAnimation(this, R.anim.anim_from_left);
        from_right = AnimationUtils.loadAnimation(this, R.anim.anim_from_right);
        from_bottom = AnimationUtils.loadAnimation(this, R.anim.anim_from_bottom);
        second_back_arrow.setAnimation(from_left);
        second_title.setAnimation(from_right);
        second_subtitle.setAnimation(from_right);
        second_arrow_up.setAnimation(from_bottom);
        more_details.setAnimation(from_bottom);
        second_arrow_up.setOnClickListener(view -> {
            Intent intent = new Intent(DetailActivity.this, ShowActivity.class);
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View, String>(second_arrow_up, "background_image_transition");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DetailActivity.this, pairs);
            startActivity(intent, options.toBundle());
        });
    }

    public void loaddata(){
        String string = getIntent().getExtras().getString("image");
        second_title.setText(getIntent().getStringExtra("judul"));
        second_subtitle.setText(getIntent().getStringExtra("deskripsi"));
        String data2 = getIntent().getStringExtra("judul");
        String data3 = getIntent().getStringExtra("deskripsi");
        Glide.with(getApplicationContext()).load(string).into(this.imgLoad);
        SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("data1", string);
        editor.putString("data2", data2);
        editor.putString("data3", data3);
        editor.apply();
    }
}