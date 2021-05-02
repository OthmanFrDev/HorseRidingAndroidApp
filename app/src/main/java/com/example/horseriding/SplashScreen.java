package com.example.horseriding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    public static int SPLASH_TIME_OUT=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(()->{
            Intent splashIntent=new Intent(SplashScreen.this,LoginActivity.class);
            SplashScreen.this.startActivity(splashIntent);
            SplashScreen.this.finish();
        },SPLASH_TIME_OUT);

    }
}