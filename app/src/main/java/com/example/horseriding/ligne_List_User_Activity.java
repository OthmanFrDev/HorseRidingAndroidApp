package com.example.horseriding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class ligne_List_User_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ligne__list__user_);
    }

    public void PostUser(View view) {
        UserController userC=new UserController();
        userC.PostUser(view);
    }
}