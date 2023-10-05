package com.example.cloudcounselage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.airbnb.lottie.LottieAnimationView;
import android.util.Pair;

public class SplashActivity extends AppCompatActivity {
    LottieAnimationView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loading = findViewById(R.id.loading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent iHome =  new Intent(getApplicationContext(), LoginActivity.class);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair(loading,"logo_image");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this,pairs);
                startActivity(iHome,options.toBundle());

                finish();
            }
        },7018);
    }
}