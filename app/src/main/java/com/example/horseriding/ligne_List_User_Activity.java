package com.example.horseriding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

    public void getemplois(View view) {

       TextView id= view.findViewById(R.id.userrolelist);
        Log.d("emploisssssssssss",id.getText().toString());

    }
}