package com.example.horseriding;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.content.res.Configuration;

import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity {
    Dialog dialog;

    CardView cardView;

    BottomNavigationView bnv;

    LinearLayout userShape;
    TextView welcomeUserName, nameuser;
    private SessionManager sessionManager;
    private HashMap<String, String> userdetail;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bnv = findViewById(R.id.bottom_navigation);
        cardView = findViewById(R.id.cardviewuser);

        dialog = new Dialog(this);
        welcomeUserName = findViewById(R.id.welcomeuser);
        nameuser = findViewById(R.id.nameuser);
        sessionManager = new SessionManager(this);
        userdetail = sessionManager.getUserDetailFromSession();
        welcomeUserName.setText(userdetail.get(SessionManager.KEY_FULLNAME));
        userShape = findViewById(R.id.shapeuser);
        nameuser.setText(userdetail.get(SessionManager.KEY_FULLNAME).split(" ")[0].toUpperCase().substring(0, 1) + "" + userdetail.get(SessionManager.KEY_FULLNAME).split(" ")[1].toUpperCase().substring(0, 1));
        nameuser.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 70);
        nameuser.setTextColor(Color.parseColor("#ffffff"));

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i = null;
                switch (item.getItemId()) {
                    case R.id.hide_nav:
                        findViewById(R.id.floatingActionButtonweek).setVisibility(View.VISIBLE);
                        findViewById(R.id.bottom_navigation).setVisibility(View.INVISIBLE);
                        break;
                    case R.id.home_nav:
                        Toast.makeText(DashboardActivity.this, "Vous êtes dans l'acceuil", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.setting_nav:
                        i = new Intent(DashboardActivity.this, EditFormUser.class);
                        DashboardActivity.this.startActivity(i);
                        break;
                    case R.id.logout_nav:
                        SessionManager sessionManager = new SessionManager(DashboardActivity.this);
                        sessionManager.logout();
                        i = new Intent(DashboardActivity.this, LoginActivity.class);
                        DashboardActivity.this.startActivity(i);
                        DashboardActivity.this.finish();
                        break;
                }
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            userShape.setVisibility(View.INVISIBLE);
            welcomeUserName.setVisibility(View.INVISIBLE);
        }

    }

    public void OnclickCardview(View view) {


        Intent splashIntent = new Intent(DashboardActivity.this, ListActivity.class);
        switch (view.getId()) {
            case R.id.cardviewuser:
                splashIntent.putExtra("click", "0");
                DashboardActivity.this.startActivity(splashIntent);
                DashboardActivity.this.finish();
                break;
            case R.id.cardviewtask:
                DashboardActivity.this.startActivity(new Intent(DashboardActivity.this, NoteController.class));
                DashboardActivity.this.finish();
                break;
            case R.id.cardviewseance:

                Intent addIntent = new Intent(DashboardActivity.this, DayView_calendar.class);
                addIntent.putExtra("emploitype", "0");
                DashboardActivity.this.startActivity(addIntent);
                DashboardActivity.this.finish();


                break;
            case R.id.emplois:
                dialog.setContentView(R.layout.dialog_emplois);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;

        }

    }

    public void onclickbtn(View view) {
        findViewById(R.id.floatingActionButtonweek).setVisibility(View.INVISIBLE);
        findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
    }


    public void getCalender(View view) {
        Intent calenderIntent = new Intent(this, WeekView_Calendar.class);
        Intent ListActivityIntent = new Intent(DashboardActivity.this, ListActivity.class);
        switch (view.getId()) {
            case R.id.globalemploi:

                calenderIntent.putExtra("emploitype", "0");
                DashboardActivity.this.startActivity(calenderIntent);
                DashboardActivity.this.finish();
                dialog.dismiss();
                break;

            case R.id.monitoremploi:
                ListActivityIntent.putExtra("emploi", "1");
                ListActivityIntent.putExtra("click", "0");
                DashboardActivity.this.startActivity(ListActivityIntent);
                DashboardActivity.this.finish();
                dialog.dismiss();

                break;
            case R.id.clientemploi:

                ListActivityIntent.putExtra("click", "3");
                DashboardActivity.this.startActivity(ListActivityIntent);
                DashboardActivity.this.finish();
                dialog.dismiss();
                break;
        }
    }
}