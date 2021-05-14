package com.example.horseriding;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity {


    CardView cardView ;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Log.d("Debug",LocalDateTime.now().getMonth().toString()) ;

        cardView=findViewById(R.id.cardviewuser);
        TextView textView=findViewById(R.id.welcomeuser);
        SessionManager sessionManager=new SessionManager(this);
        HashMap<String,String> userdetail=sessionManager.getUserDetailFromSession();
        textView.setText(userdetail.get(SessionManager.KEY_FULLNAME));



    }

    public void afficherUtilisateur(View view) {

        MainActivity main = new MainActivity();
        Intent splashIntent = new Intent(DashboardActivity.this, ListActivity.class);
        switch (view.getId()) {
            case R.id.cardviewuser:

  splashIntent.putExtra("user","0");
                DashboardActivity.this.startActivity(splashIntent);
                DashboardActivity.this.finish();

            break;
            case R.id.cardviewtask:

splashIntent.putExtra("task","1");
                DashboardActivity.this.startActivity(splashIntent);
                DashboardActivity.this.finish();

            break;
            case R.id.cardviewseance:

                splashIntent.putExtra("seance","2");
                DashboardActivity.this.startActivity(splashIntent);
                DashboardActivity.this.finish();

            break;
        }

    }

    public void logout(View view) {
        SessionManager sessionManager=new SessionManager(this);
        sessionManager.logout();
        Intent splashIntent = new Intent(DashboardActivity.this, LoginActivity.class);
        DashboardActivity.this.startActivity(splashIntent);
        DashboardActivity.this.finish();
    }
}