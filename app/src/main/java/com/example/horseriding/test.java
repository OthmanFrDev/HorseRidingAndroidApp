package com.example.horseriding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alamkanak.weekview.WeekView;

public class test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Get a reference for the week view in the layout.
        WeekView mWeekView = (WeekView) findViewById(R.id.weekView);

    }
}