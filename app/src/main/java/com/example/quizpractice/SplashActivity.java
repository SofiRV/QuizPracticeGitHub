package com.example.quizpractice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 5000; // 5 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 1️⃣ Top title fade_in2
        View topTitle = findViewById(R.id.TextViewTopTitle);
        Animation fadeIn2 = AnimationUtils.loadAnimation(this, R.anim.fade_in2);
        topTitle.startAnimation(fadeIn2);

        // 2️⃣ TableLayout animación
        View tableLayout = findViewById(R.id.TableLayout01);
        Animation customAnim = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
        tableLayout.startAnimation(customAnim);

        // 3️⃣ Cambiar a MainActivity al terminar 5 segundos
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_DURATION);
    }
}
