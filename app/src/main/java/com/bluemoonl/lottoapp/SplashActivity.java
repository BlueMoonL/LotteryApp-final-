package com.bluemoonl.lottoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        startSplash();

        lottieAnimationView = findViewById(R.id.splash_animation);

        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LottoMainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        handler.postDelayed(runnable, 3000);

        lottieAnimationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                Intent intent = new Intent(getApplicationContext(), LottoMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

//    public void startSplash() {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(getApplicationContext(), LottoMainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 1500);
//    }
}