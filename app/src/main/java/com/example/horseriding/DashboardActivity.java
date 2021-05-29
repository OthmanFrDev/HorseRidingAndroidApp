package com.example.horseriding;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity {
Dialog dialog;

    CardView cardView ;

    BottomNavigationView bnv;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Log.d("Debug",LocalDateTime.now().getMonth().toString()) ;
        bnv=findViewById(R.id.bottom_navigation);
        cardView=findViewById(R.id.cardviewuser);
        TextView textView=findViewById(R.id.welcomeuser);

        dialog=new Dialog(this);
        SessionManager sessionManager=new SessionManager(this);
        HashMap<String,String> userdetail=sessionManager.getUserDetailFromSession();
        textView.setText(userdetail.get(SessionManager.KEY_FULLNAME));

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i=null;
                switch(item.getItemId()){
                    case R.id.hide_nav:findViewById(R.id.floatingActionButtonweek).setVisibility(View.VISIBLE );findViewById(R.id.bottom_navigation).setVisibility(View.INVISIBLE);break;
                    case R.id.home_nav:Toast.makeText(DashboardActivity.this,"Vous êtes dans l'acceuil",Toast.LENGTH_SHORT).show();
                    case R.id.setting_nav:i = new Intent(DashboardActivity.this, EditFormUser.class);
                        DashboardActivity.this.startActivity(i);break;
                    case R.id.logout_nav:SessionManager sessionManager=new SessionManager(DashboardActivity.this);
                        sessionManager.logout();
                        i = new Intent(DashboardActivity.this, LoginActivity.class);
                        DashboardActivity.this.startActivity(i);
                        DashboardActivity.this.finish();break;
                }
                return true;
            }
        });

    }

    public void afficherUtilisateur(View view) {

        MainActivity main = new MainActivity();
        Intent splashIntent = new Intent(DashboardActivity.this, ListActivity.class);
        switch (view.getId()) {
            case R.id.cardviewuser:
                splashIntent.putExtra("click","0");
                DashboardActivity.this.startActivity(splashIntent);
                DashboardActivity.this.finish();
            break;
            case R.id.cardviewtask:

                //splashIntent.putExtra("click","1");

                DashboardActivity.this.startActivity(new Intent(DashboardActivity.this,NoteController.class));
                DashboardActivity.this.finish();
                    break;
            case R.id.cardviewseance:

               Intent addIntent = new Intent(DashboardActivity.this,RecycleCalendar.class);
               DashboardActivity.this.startActivity(addIntent);
               DashboardActivity.this.finish();
//                splashIntent.putExtra("click","2");
//                DashboardActivity.this.startActivity(splashIntent);
//                DashboardActivity.this.finish();

            break;   case R.id.emplois:
                dialog.setContentView(R.layout.dialog_emplois);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            break;

        }

    }
    public void onclickbtn(View view) {
        findViewById(R.id.floatingActionButtonweek).setVisibility(View.INVISIBLE);findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
    }

    public void logout(View view) {
        SessionManager sessionManager=new SessionManager(this);
        sessionManager.logout();
        Intent splashIntent = new Intent(DashboardActivity.this, LoginActivity.class);
        DashboardActivity.this.startActivity(splashIntent);
        DashboardActivity.this.finish();
    }

    public void getemploi(View view) {
        Intent calenderIntent=new Intent(this,WeekView_Calendar.class);
        Intent ListActivityIntent = new Intent(DashboardActivity.this, ListActivity.class);
        switch (view.getId())
    {
        case R.id.globalemploi:

            calenderIntent.putExtra("emploitype","0");
            DashboardActivity.this.startActivity(calenderIntent);
            DashboardActivity.this.finish();
        break;case R.id.monitoremploi:

//            splashIntent.putExtra("emploitype","1");
//            DashboardActivity.this.startActivity(splashIntent);
//            DashboardActivity.this.finish();
        ListActivityIntent.putExtra("click","0");

        DashboardActivity.this.startActivity(ListActivityIntent);
        DashboardActivity.this.finish();

        break;case R.id.clientemploi:

//            splashIntent.putExtra("emploitype","2");
//            DashboardActivity.this.startActivity(splashIntent);
//            DashboardActivity.this.finish();
        ListActivityIntent.putExtra("click","3");
        DashboardActivity.this.startActivity(ListActivityIntent);
        DashboardActivity.this.finish();
        break;
    }
    }
}