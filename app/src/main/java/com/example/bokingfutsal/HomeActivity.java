package com.example.bokingfutsal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private List<Image> imageList;
    private ImageAdapter imageAdapter;
    private Handler handler = new Handler();
    CardView cardView, cardView2, cardView3;
    ImageView imageView;
    RelativeLayout relativeLayout;
    TextView textView, textView2, textView3, textView4, textView5;
    SearchView searchView;
    Animation anim_from_button, anim_from_top, anim_from_left;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager2 = findViewById(R.id.viewpager2);
        imageList = new ArrayList<>();

        imageList.add(new Image(R.drawable.banner1));
        imageList.add(new Image(R.drawable.banner2));
        imageList.add(new Image(R.drawable.banner3));
        imageList.add(new Image(R.drawable.banner4));

        imageAdapter = new ImageAdapter(imageList, viewPager2);
        viewPager2.setAdapter(imageAdapter);

        relativeLayout = findViewById(R.id.rc1);

        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setClipChildren(false);
        viewPager2.setClipToPadding(false);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleX(0.90f + r * 0.25f);

            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,2000);
            }
        });

        cardView = findViewById(R.id.cardView);
        cardView2 = findViewById(R.id.cardView2);
        cardView3 = findViewById(R.id.cardView3);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.firstText);
        textView2 = findViewById(R.id.textView);
        textView3 = findViewById(R.id.textView2);
        textView4 = findViewById(R.id.textView3);
        textView5 = findViewById(R.id.textView4);
        searchView = findViewById(R.id.searchView);

        anim_from_button = AnimationUtils.loadAnimation(this, R.anim.anim_from_bottom);
        anim_from_top = AnimationUtils.loadAnimation(this, R.anim.anim_from_top);
        anim_from_left = AnimationUtils.loadAnimation(this, R.anim.anim_from_left);

        relativeLayout.setAnimation(anim_from_button);
        cardView.setAnimation(anim_from_button);
        viewPager2.setAnimation(anim_from_button);
        cardView2.setAnimation(anim_from_button);
        cardView3.setAnimation(anim_from_button);
        imageView.setAnimation(anim_from_top);
        textView.setAnimation(anim_from_top);
        textView2.setAnimation(anim_from_top);
        textView3.setAnimation(anim_from_top);
        textView4.setAnimation(anim_from_top);
        textView5.setAnimation(anim_from_top);
        searchView.setAnimation(anim_from_left);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent secondActivity = new Intent(HomeActivity.this, DetailActivity.class);
                startActivity(secondActivity);
            }
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

    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable,2000);
    }
}