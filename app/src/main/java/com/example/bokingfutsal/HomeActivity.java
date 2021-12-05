package com.example.bokingfutsal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private List<Image> imageList;
    private ImageAdapter imageAdapter;
    private Handler handler = new Handler();
    RecyclerView.Adapter adapter;
    List<Lapangan> list = new ArrayList();
    LinearLayoutManager linearLayoutManager;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ImageView imageView, imageViewAccount;
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


        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setClipChildren(false);
        viewPager2.setClipToPadding(false);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleX(0.90f + r * 0.25f);

        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 2000);
            }
        });

        imageViewAccount = findViewById(R.id.account);
        textView = findViewById(R.id.firstText);
        textView2 = findViewById(R.id.textView);
        textView3 = findViewById(R.id.textView2);
        textView4 = findViewById(R.id.textView3);
        textView5 = findViewById(R.id.textView4);
        searchView = findViewById(R.id.searchView);

        anim_from_button = AnimationUtils.loadAnimation(this, R.anim.anim_from_bottom);
        anim_from_top = AnimationUtils.loadAnimation(this, R.anim.anim_from_top);
        anim_from_left = AnimationUtils.loadAnimation(this, R.anim.anim_from_left);

        viewPager2.setAnimation(anim_from_button);
        textView.setAnimation(anim_from_top);
        textView2.setAnimation(anim_from_top);
        textView3.setAnimation(anim_from_top);
        textView4.setAnimation(anim_from_top);
        textView5.setAnimation(anim_from_top);
        searchView.setAnimation(anim_from_left);

        imageViewAccount.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, UserActivity.class);
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

        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerViewuser);
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
                    HomeActivity.this.list.add((Lapangan) dataSnapshot2.getValue(Lapangan.class));
                }
                HomeActivity.this.adapter = new RecyclerViewAdapter(HomeActivity.this.getApplicationContext(), HomeActivity.this.list);
                HomeActivity.this.recyclerView.setAdapter(HomeActivity.this.adapter);
                HomeActivity.this.progressDialog.dismiss();
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(DatabaseError databaseError) {
                HomeActivity.this.progressDialog.dismiss();
            }
        });

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
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
        handler.postDelayed(runnable, 2000);
    }
}