package com.example.horseriding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ListView listClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listClient=findViewById(R.id.listUser);
        if(listClient!=null){
            UserAdapter ua=new UserAdapter(ListActivity.this,new ArrayList<>());
            listClient.setAdapter(ua);
        }

    }
}