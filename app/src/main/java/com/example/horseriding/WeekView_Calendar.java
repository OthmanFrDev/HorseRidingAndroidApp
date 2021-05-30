package com.example.horseriding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeekView_Calendar extends AppCompatActivity {
    DatabaseHandler databaseHandler =new DatabaseHandler(WeekView_Calendar.this);
    String startDateString,endDateString;
    String id;
    String url;
    String color;
    List<Seance> seances;
  static   boolean server=false;
    TextView Lun_08,Lun_09,Lun_10,Lun_11,Lun_12,Lun_13,Lun_14,Lun_15,Lun_16,Lun_17,Lun_18,
            Mar_08,Mar_09,Mar_10,Mar_11,Mar_12,Mar_13,Mar_14,Mar_15,Mar_16,Mar_17,Mar_18,
            Mer_08,Mer_09,Mer_10,Mer_11,Mer_12,Mer_13,Mer_14,Mer_15,Mer_16,Mer_17,Mer_18,
            Jeu_08,Jeu_09,Jeu_10,Jeu_11,Jeu_12,Jeu_13,Jeu_14,Jeu_15,Jeu_16,Jeu_17,Jeu_18,
            Ven_08,Ven_09,Ven_10,Ven_11,Ven_12,Ven_13,Ven_14,Ven_15,Ven_16,Ven_17,Ven_18,
            Sam_08,Sam_09,Sam_10,Sam_11,Sam_12,Sam_13,Sam_14,Sam_15,Sam_16,Sam_17,Sam_18,
            Dim_08,Dim_09,Dim_10,Dim_11,Dim_12,Dim_13,Dim_14,Dim_15,Dim_16,Dim_17,Dim_18;

    LocalDateTime dateInit=LocalDateTime.of(2020, 9, 14, 15, 56);
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    LocalDateTime starDate=dateInit,endDate=dateInit;
    BottomNavigationView bnv;
    private Dialog dialog;
    private String urlTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view_calendar);
        Intent emploiIntent=getIntent();
        id=emploiIntent.getStringExtra("id");
        bnv=findViewById(R.id.bottom_navigation);
        dialog= new Dialog(WeekView_Calendar.this);
        switch(dateInit.getDayOfWeek().getValue()) {
            case 1:starDate=dateInit;endDate=starDate.plusDays(6);break;
            case 2:starDate=dateInit.minusDays(1);endDate=starDate.plusDays(6);break;
            case 3:starDate=dateInit.minusDays(2);endDate=starDate.plusDays(6);break;
            case 4:starDate=dateInit.minusDays(3);endDate=starDate.plusDays(6);break;
            case 5:starDate=dateInit.minusDays(4);endDate=starDate.plusDays(6);break;
            case 6:starDate=dateInit.minusDays(5);endDate=starDate.plusDays(6);break;
            case 7:starDate=dateInit.minusDays(6);endDate=starDate.plusDays(6);break;
        }
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i=null;
                switch(item.getItemId()){
                    case R.id.hide_nav:findViewById(R.id.floatingActionButtonweek).setVisibility(View.VISIBLE );findViewById(R.id.bottom_navigation).setVisibility(View.INVISIBLE);break;
                    case R.id.home_nav:i=new Intent(WeekView_Calendar.this,DashboardActivity.class);startActivity(i);finish();
                    case R.id.setting_nav:findViewById(R.id.floatingActionButtonweek).setVisibility(View.VISIBLE );findViewById(R.id.bottom_navigation).setVisibility(View.INVISIBLE);break;
                    case R.id.logout_nav:SessionManager sessionManager=new SessionManager(WeekView_Calendar.this);
                                        sessionManager.logout();
                        Intent splashIntent = new Intent(WeekView_Calendar.this, LoginActivity.class);
                        WeekView_Calendar.this.startActivity(splashIntent);
                        WeekView_Calendar.this.finish();break;
                }
                return true;
            }
        });
        startDateString=dateFormatter.format(starDate);
        endDateString=dateFormatter.format(endDate);
        Lun_08=findViewById(R.id.Lun_08);
        Lun_09=findViewById(R.id.Lun_09);
        Lun_10=findViewById(R.id.Lun_10);
        Lun_11=findViewById(R.id.Lun_11);
        Lun_12=findViewById(R.id.Lun_12);
        Lun_13=findViewById(R.id.Lun_13);
        Lun_14=findViewById(R.id.Lun_14);
        Lun_15=findViewById(R.id.Lun_15);
        Lun_16=findViewById(R.id.Lun_16);
        Lun_17=findViewById(R.id.Lun_17);
        Lun_18=findViewById(R.id.Lun_18);
        Mar_08=findViewById(R.id.Mar_08);
        Mar_09=findViewById(R.id.Mar_09);
        Mar_10=findViewById(R.id.Mar_10);
        Mar_11=findViewById(R.id.Mar_11);
        Mar_12=findViewById(R.id.Mar_12);
        Mar_13=findViewById(R.id.Mar_13);
        Mar_14=findViewById(R.id.Mar_14);
        Mar_15=findViewById(R.id.Mar_15);
        Mar_16=findViewById(R.id.Mar_16);
        Mar_17=findViewById(R.id.Mar_17);
        Mar_18=findViewById(R.id.Mar_18);
        Mer_08=findViewById(R.id.Mer_08);
        Mer_09=findViewById(R.id.Mer_09);
        Mer_10=findViewById(R.id.Mer_10);
        Mer_11=findViewById(R.id.Mer_11);
        Mer_12=findViewById(R.id.Mer_12);
        Mer_13=findViewById(R.id.Mer_13);
        Mer_14=findViewById(R.id.Mer_14);
        Mer_15=findViewById(R.id.Mer_15);
        Mer_16=findViewById(R.id.Mer_16);
        Mer_17=findViewById(R.id.Mer_17);
        Mer_18=findViewById(R.id.Mer_18);
        Jeu_08=findViewById(R.id.Jeu_08);
        Jeu_09=findViewById(R.id.Jeu_09);
        Jeu_10=findViewById(R.id.Jeu_10);
        Jeu_11=findViewById(R.id.Jeu_11);
        Jeu_12=findViewById(R.id.Jeu_12);
        Jeu_13=findViewById(R.id.Jeu_13);
        Jeu_14=findViewById(R.id.Jeu_14);
        Jeu_15=findViewById(R.id.Jeu_15);
        Jeu_16=findViewById(R.id.Jeu_16);
        Jeu_17=findViewById(R.id.Jeu_17);
        Jeu_18=findViewById(R.id.Jeu_18);
        Ven_08=findViewById(R.id.Ven_08);
        Ven_09=findViewById(R.id.Ven_09);
        Ven_10=findViewById(R.id.Ven_10);
        Ven_11=findViewById(R.id.Ven_11);
        Ven_12=findViewById(R.id.Ven_12);
        Ven_13=findViewById(R.id.Ven_13);
        Ven_14=findViewById(R.id.Ven_14);
        Ven_15=findViewById(R.id.Ven_15);
        Ven_16=findViewById(R.id.Ven_16);
        Ven_17=findViewById(R.id.Ven_17);
        Ven_18=findViewById(R.id.Ven_18);
        Sam_08=findViewById(R.id.Sam_08);
        Sam_09=findViewById(R.id.Sam_09);
        Sam_10=findViewById(R.id.Sam_10);
        Sam_11=findViewById(R.id.Sam_11);
        Sam_12=findViewById(R.id.Sam_12);
        Sam_13=findViewById(R.id.Sam_13);
        Sam_14=findViewById(R.id.Sam_14);
        Sam_15=findViewById(R.id.Sam_15);
        Sam_16=findViewById(R.id.Sam_16);
        Sam_17=findViewById(R.id.Sam_17);
        Sam_18=findViewById(R.id.Sam_18);
        Dim_08=findViewById(R.id.Dim_08);
        Dim_09=findViewById(R.id.Dim_09);
        Dim_10=findViewById(R.id.Dim_10);
        Dim_11=findViewById(R.id.Dim_11);
        Dim_12=findViewById(R.id.Dim_12);
        Dim_13=findViewById(R.id.Dim_13);
        Dim_14=findViewById(R.id.Dim_14);
        Dim_15=findViewById(R.id.Dim_15);
        Dim_16=findViewById(R.id.Dim_16);
        Dim_17=findViewById(R.id.Dim_17);
        Dim_18=findViewById(R.id.Dim_18);


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mI=getMenuInflater();

        mI.inflate(R.menu.menu_calendar,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){
            case R.id.month_view:calenderSwitcher(WeekView_Calendar.this,Month_view.class);
            case R.id.week_view:/*item.setVisible(false);*/Toast.makeText(WeekView_Calendar.this,"already in week section",Toast.LENGTH_SHORT).show();break;
            case R.id.day_view: calenderSwitcher(WeekView_Calendar.this,RecycleCalendar.class);


        }
        return true;
    }

    private void calenderSwitcher(Context context, Class<?> CLASS) {
        Intent i=null;
        i=new Intent(context, CLASS);
        if(getIntent().getStringExtra("emploitype")!=null){
            switch (getIntent().getStringExtra("emploitype"))
            {
                case "0": startActivity(i);finish();break;

                case "1":  i.putExtra("emploitype","1");i.putExtra("id",id); startActivity(i);finish();break;




                case "2":i.putExtra("emploitype","2");i.putExtra("id",id); startActivity(i);finish();break;


            }
        }else{ startActivity(i);finish(); }
    }

    @Override

    protected void onResume() {
        SessionManager sessionManager=new SessionManager(WeekView_Calendar.this);
        super.onResume();

         emploitype();

        dialog.setContentView(R.layout.progressbar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        JsonArrayRequest jArray=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {




            @Override
            public void onResponse(JSONArray response) {
                dialog.cancel();
                TextView day=findViewById(R.id.day);
                day.setText(startDateString+" -> "+endDateString);
                getSeance(response);
               // getTache();
                JSONObject j = null;




                for(int i=0;i<response.length();i++) {

                    try {
                        j = response.getJSONObject(i);
                        databaseHandler.saveSeance(new Seance(j.getInt("seanceId"), j.getString("comments"), j.getInt("clientId"), j.getInt("monitorId"), j.getInt("durationMinut"), j.getString("startDate")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WeekView_Calendar.this,"here error",Toast.LENGTH_SHORT).show();
            dialog.cancel();
             getSeance(seances);

            }
        });
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jArray);

    }

    private void emploitype() {
        if(getIntent().getStringExtra("emploitype")!=null){
            switch (getIntent().getStringExtra("emploitype"))
            {
                case "0":url=WS.URL+"seances/"+startDateString+"/"+endDateString ;
                seances=databaseHandler.readSeance();
                getTache();
                    break;
                case "1":url=WS.URL+"seances/monitor/"+startDateString+"/"+endDateString+"/"+id;
                    //getTache();
                  seances=databaseHandler.readUserSeance(Integer.valueOf(id));

                    break;
                case "2":url=WS.URL +"seances/"+startDateString+"/"+endDateString+"/"+id;
                seances=databaseHandler.readClientSeance(Integer.valueOf(id));
                    break;

            }
        }else{url=WS.URL+"seances/"+startDateString+"/"+endDateString; getTache();}
    }

    public void initId(String jour){

    }
    public void weekchanger(View view) {
        SessionManager sessionManager=new SessionManager(WeekView_Calendar.this);
        init();
         server=false;
         LocalDateTime firstDate=starDate;
        switch(view.getId())
        {


            case R.id.btnnext:  starDate=starDate.plusDays(7);
                                 startDateString= dateFormatter.format(starDate);
                                 endDate=endDate.plusDays(7);
                                 endDateString=dateFormatter.format(endDate);
                break;
            case R.id.btnprevious: starDate=starDate.minusDays(7);
                startDateString= dateFormatter.format(starDate);
                endDate=endDate.minusDays(7);
                endDateString=dateFormatter.format(endDate);
                break;
        }
        Locale local=new Locale("fr","Fr");
emploitype();
        //DateTimeFormatter dt= ;
        TextView day=findViewById(R.id.day);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //day.setText(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT).withLocale(local).format(DateInit));
        day.setText(startDateString+" -> "+endDateString);




            JsonArrayRequest jArray=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {
                sessionManager.server(true);

                getSeance(response  );

                }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.w("ONERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOOOR"," "+error);
            }
        });

        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jArray);

//       if(!sessionManager.getserver()&&firstDate.equals(starDate))
//      {
//          getSeance(databaseHandler.readSeance());}


    }
    void init()
    {
        Lun_08.setText("");Lun_08.setBackgroundColor(Color.parseColor("#ffffff"));

        Lun_09.setText("");Lun_09.setBackgroundColor(Color.parseColor("#ffffff"));

        Lun_10.setText("");Lun_10.setBackgroundColor(Color.parseColor("#ffffff"));

        Lun_11.setText("");Lun_11.setBackgroundColor(Color.parseColor("#ffffff"));

        Lun_12.setText("");Lun_12.setBackgroundColor(Color.parseColor("#ffffff"));

        Lun_13.setText("");Lun_13.setBackgroundColor(Color.parseColor("#ffffff"));

        Lun_14.setText("");Lun_14.setBackgroundColor(Color.parseColor("#ffffff"));

        Lun_15.setText("");Lun_15.setBackgroundColor(Color.parseColor("#ffffff"));

        Lun_16.setText("");Lun_16.setBackgroundColor(Color.parseColor("#ffffff"));

        Lun_17.setText("");Lun_17.setBackgroundColor(Color.parseColor("#ffffff"));

        Lun_18.setText("");Lun_18.setBackgroundColor(Color.parseColor("#ffffff"));
        Mar_08.setText("");Mar_08.setBackgroundColor(Color.parseColor("#ffffff"));

        Mar_09.setText("");Mar_09.setBackgroundColor(Color.parseColor("#ffffff"));

        Mar_10.setText("");Mar_10.setBackgroundColor(Color.parseColor("#ffffff"));

        Mar_11.setText("");Mar_11.setBackgroundColor(Color.parseColor("#ffffff"));

        Mar_12.setText("");Mar_12.setBackgroundColor(Color.parseColor("#ffffff"));

        Mar_13.setText("");Mar_13.setBackgroundColor(Color.parseColor("#ffffff"));

        Mar_14.setText("");Mar_14.setBackgroundColor(Color.parseColor("#ffffff"));

        Mar_15.setText("");Mar_15.setBackgroundColor(Color.parseColor("#ffffff"));

        Mar_16.setText("");Mar_16.setBackgroundColor(Color.parseColor("#ffffff"));

        Mar_17.setText("");Mar_17.setBackgroundColor(Color.parseColor("#ffffff"));

        Mar_18.setText("");Mar_18.setBackgroundColor(Color.parseColor("#ffffff"));

        Mer_08.setText("");Mer_08.setBackgroundColor(Color.parseColor("#ffffff"));

        Mer_09.setText("");Mer_09.setBackgroundColor(Color.parseColor("#ffffff"));

        Mer_10.setText("");Mer_10.setBackgroundColor(Color.parseColor("#ffffff"));

        Mer_11.setText("");Mer_11.setBackgroundColor(Color.parseColor("#ffffff"));

        Mer_12.setText("");Mer_12.setBackgroundColor(Color.parseColor("#ffffff"));

        Mer_13.setText("");Mer_13.setBackgroundColor(Color.parseColor("#ffffff"));

        Mer_14.setText("");Mer_14.setBackgroundColor(Color.parseColor("#ffffff"));

        Mer_15.setText("");Mer_15.setBackgroundColor(Color.parseColor("#ffffff"));

        Mer_16.setText("");Mer_16.setBackgroundColor(Color.parseColor("#ffffff"));

        Mer_17.setText("");Mer_17.setBackgroundColor(Color.parseColor("#ffffff"));

        Mer_18.setText("");Mer_18.setBackgroundColor(Color.parseColor("#ffffff"));
        Jeu_08.setText("");Jeu_08.setBackgroundColor(Color.parseColor("#ffffff"));

        Jeu_09.setText("");Jeu_09.setBackgroundColor(Color.parseColor("#ffffff"));

        Jeu_10.setText("");Jeu_10.setBackgroundColor(Color.parseColor("#ffffff"));

        Jeu_11.setText("");Jeu_11.setBackgroundColor(Color.parseColor("#ffffff"));

        Jeu_12.setText("");Jeu_12.setBackgroundColor(Color.parseColor("#ffffff"));

        Jeu_13.setText("");Jeu_13.setBackgroundColor(Color.parseColor("#ffffff"));

        Jeu_14.setText("");Jeu_14.setBackgroundColor(Color.parseColor("#ffffff"));

        Jeu_15.setText("");Jeu_15.setBackgroundColor(Color.parseColor("#ffffff"));

        Jeu_16.setText("");Jeu_16.setBackgroundColor(Color.parseColor("#ffffff"));

        Jeu_17.setText("");Jeu_17.setBackgroundColor(Color.parseColor("#ffffff"));

        Jeu_18.setText("");Jeu_18.setBackgroundColor(Color.parseColor("#ffffff"));
        Ven_08.setText("");Ven_08.setBackgroundColor(Color.parseColor("#ffffff"));

        Ven_09.setText("");Ven_09.setBackgroundColor(Color.parseColor("#ffffff"));

        Ven_10.setText("");Ven_10.setBackgroundColor(Color.parseColor("#ffffff"));

        Ven_11.setText("");Ven_11.setBackgroundColor(Color.parseColor("#ffffff"));

        Ven_12.setText("");Ven_12.setBackgroundColor(Color.parseColor("#ffffff"));

        Ven_13.setText("");Ven_13.setBackgroundColor(Color.parseColor("#ffffff"));

        Ven_14.setText("");Ven_14.setBackgroundColor(Color.parseColor("#ffffff"));

        Ven_15.setText("");Ven_15.setBackgroundColor(Color.parseColor("#ffffff"));

        Ven_16.setText("");Ven_16.setBackgroundColor(Color.parseColor("#ffffff"));

        Ven_17.setText("");Ven_17.setBackgroundColor(Color.parseColor("#ffffff"));

        Ven_18.setText("");Ven_18.setBackgroundColor(Color.parseColor("#ffffff"));
        Sam_08.setText("");Sam_08.setBackgroundColor(Color.parseColor("#ffffff"));

        Sam_09.setText("");Sam_09.setBackgroundColor(Color.parseColor("#ffffff"));

        Sam_10.setText("");Sam_10.setBackgroundColor(Color.parseColor("#ffffff"));

        Sam_11.setText("");Sam_11.setBackgroundColor(Color.parseColor("#ffffff"));

        Sam_12.setText("");Sam_12.setBackgroundColor(Color.parseColor("#ffffff"));

        Sam_13.setText("");Sam_13.setBackgroundColor(Color.parseColor("#ffffff"));

        Sam_14.setText("");Sam_14.setBackgroundColor(Color.parseColor("#ffffff"));

        Sam_15.setText("");Sam_15.setBackgroundColor(Color.parseColor("#ffffff"));

        Sam_16.setText("");Sam_16.setBackgroundColor(Color.parseColor("#ffffff"));

        Sam_17.setText("");Sam_17.setBackgroundColor(Color.parseColor("#ffffff"));

        Sam_18.setText("");Sam_18.setBackgroundColor(Color.parseColor("#ffffff"));
        Dim_08.setText("");Dim_08.setBackgroundColor(Color.parseColor("#ffffff"));

        Dim_09.setText("");Dim_09.setBackgroundColor(Color.parseColor("#ffffff"));

        Dim_10.setText("");Dim_10.setBackgroundColor(Color.parseColor("#ffffff"));

        Dim_11.setText("");Dim_11.setBackgroundColor(Color.parseColor("#ffffff"));

        Dim_12.setText("");Dim_12.setBackgroundColor(Color.parseColor("#ffffff"));

        Dim_13.setText("");Dim_13.setBackgroundColor(Color.parseColor("#ffffff"));

        Dim_14.setText("");Dim_14.setBackgroundColor(Color.parseColor("#ffffff"));

        Dim_15.setText("");Dim_15.setBackgroundColor(Color.parseColor("#ffffff"));

        Dim_16.setText("");Dim_16.setBackgroundColor(Color.parseColor("#ffffff"));

        Dim_17.setText("");Dim_17.setBackgroundColor(Color.parseColor("#ffffff"));

        Dim_18.setText("");Dim_18.setBackgroundColor(Color.parseColor("#ffffff"));


    }

    void getSeance(JSONArray response)
    {
        server=true;
        LocalDateTime dateFromResponse;
        JSONObject j = null;
        for(int i=0;i<response.length();i++){
            try {
                j = response.getJSONObject(i);
                dateFromResponse=LocalDateTime.parse(j.getString("startDate"));
                String dateTime=j.getString("startDate").substring(11,16);
//                if(LocalDateTime.now().compareTo(dateFromResponse)<=0)
//                {
//                    color="#EC7C32";
//                }else
//                {
//                    color="#ff6347";
//                }
                if(LocalDateTime.now().compareTo(dateFromResponse)<=0)
                {
                    color="#2de04b";
                }else
                {
                    color="#CC0000";
                }
                switch (dateFromResponse.getDayOfWeek().getValue()){
                    case 1: switch(dateTime){

                        case "08:00":Lun_08.setText(j.getInt("seanceId")+""+""+"");Lun_08.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "09:00":Lun_09.setText(j.getInt("seanceId")+""+""+"");Lun_09.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "10:00":Lun_10.setText(j.getInt("seanceId")+""+""+"");Lun_10.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "11:00":Lun_11.setText(j.getInt("seanceId")+""+""+"");Lun_11.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "12:00":Lun_12.setText(j.getInt("seanceId")+""+""+"");Lun_12.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "13:00":Lun_13.setText(j.getInt("seanceId")+""+""+"");Lun_13.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "14:00":Lun_14.setText(j.getInt("seanceId")+""+""+"");Lun_14.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "15:00":Lun_15.setText(j.getInt("seanceId")+""+""+"");Lun_15.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "16:00":Lun_16.setText(j.getInt("seanceId")+""+""+"");Lun_16.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "17:00":Lun_17.setText(j.getInt("seanceId")+""+""+"");Lun_17.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "18:00":Lun_18.setText(j.getInt("seanceId")+""+""+"");Lun_18.setBackgroundColor(Color.parseColor(color));

                            break;
                    }
                        break;
                    case 2: switch(dateTime){
                        case "08:00":Mar_08.setText(j.getInt("seanceId")+""+""+"");Mar_08.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "09:00":Mar_09.setText(j.getInt("seanceId")+""+""+"");Mar_09.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "10:00":Mar_10.setText(j.getInt("seanceId")+""+""+"");Mar_10.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "11:00":Mar_11.setText(j.getInt("seanceId")+""+""+"");Mar_11.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "12:00":Mar_12.setText(j.getInt("seanceId")+""+""+"");Mar_12.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "13:00":Mar_13.setText(j.getInt("seanceId")+""+""+"");Mar_13.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "14:00":Mar_14.setText(j.getInt("seanceId")+""+""+"");Mar_14.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "15:00":Mar_15.setText(j.getInt("seanceId")+""+""+"");Mar_15.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "16:00":Mar_16.setText(j.getInt("seanceId")+""+""+"");Mar_16.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "17:00":Mar_17.setText(j.getInt("seanceId")+""+""+"");Mar_17.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "18:00":Mar_18.setText(j.getInt("seanceId")+""+""+"");Mar_18.setBackgroundColor(Color.parseColor(color));
                            break;
                    }
                        break;
                    case 3:switch(dateTime){
                        case "08:00":Mer_08.setText(j.getInt("seanceId")+""+""+"");Mer_08.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "09:00":Mer_09.setText(j.getInt("seanceId")+""+""+"");Mer_09.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "10:00":Mer_10.setText(j.getInt("seanceId")+""+""+"");Mer_10.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "11:00":Mer_11.setText(j.getInt("seanceId")+""+""+"");Mer_11.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "12:00":Mer_12.setText(j.getInt("seanceId")+""+""+"");Mer_12.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "13:00":Mer_13.setText(j.getInt("seanceId")+""+""+"");Mer_13.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "14:00":Mer_14.setText(j.getInt("seanceId")+""+""+"");Mer_14.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "15:00":Mer_15.setText(j.getInt("seanceId")+""+""+"");Mer_15.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "16:00":Mer_16.setText(j.getInt("seanceId")+""+""+"");Mer_16.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "17:00":Mer_17.setText(j.getInt("seanceId")+""+""+"");Mer_17.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "18:00":Mer_18.setText(j.getInt("seanceId")+""+""+"");Mer_18.setBackgroundColor(Color.parseColor(color));
                            break;
                    }
                        break;
                    case 4:
                        switch(dateTime){
                            case "08:00":Jeu_08.setText(j.getInt("seanceId")+""+""+"");Jeu_08.setBackgroundColor(Color.parseColor(color));
                                break;
                            case "09:00":Jeu_09.setText(j.getInt("seanceId")+""+""+"");Jeu_09.setBackgroundColor(Color.parseColor(color));
                                break;
                            case "10:00":Jeu_10.setText(j.getInt("seanceId")+""+""+"");Jeu_10.setBackgroundColor(Color.parseColor(color));
                                break;
                            case "11:00":Jeu_11.setText(j.getInt("seanceId")+""+""+"");Jeu_11.setBackgroundColor(Color.parseColor(color));
                                break;
                            case "12:00":Jeu_12.setText(j.getInt("seanceId")+""+""+"");Jeu_12.setBackgroundColor(Color.parseColor(color));
                                break;
                            case "13:00":Jeu_13.setText(j.getInt("seanceId")+""+""+"");Jeu_13.setBackgroundColor(Color.parseColor(color));
                                break;
                            case "14:00":Jeu_14.setText(j.getInt("seanceId")+""+""+"");Jeu_14.setBackgroundColor(Color.parseColor(color));
                                break;
                            case "15:00":Jeu_15.setText(j.getInt("seanceId")+""+""+"");Jeu_15.setBackgroundColor(Color.parseColor(color));
                                break;
                            case "16:00":Jeu_16.setText(j.getInt("seanceId")+""+""+"");Jeu_16.setBackgroundColor(Color.parseColor(color));
                                break;
                            case "17:00":Jeu_17.setText(j.getInt("seanceId")+""+""+"");Jeu_17.setBackgroundColor(Color.parseColor(color));
                                break;
                            case "18:00":Jeu_18.setText(j.getInt("seanceId")+""+""+"");Jeu_18.setBackgroundColor(Color.parseColor(color));
                                break;
                        }
                        break;
                    case 5:switch(dateTime){
                        case "08:00":Ven_08.setText(j.getInt("seanceId")+""+""+"");Ven_08.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "09:00":Ven_09.setText(j.getInt("seanceId")+""+""+"");Ven_09.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "10:00":Ven_10.setText(j.getInt("seanceId")+""+""+"");Ven_10.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "11:00":Ven_11.setText(j.getInt("seanceId")+""+""+"");Ven_11.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "12:00":Ven_12.setText(j.getInt("seanceId")+""+""+"");Ven_12.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "13:00":Ven_13.setText(j.getInt("seanceId")+""+""+"");Ven_13.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "14:00":Ven_14.setText(j.getInt("seanceId")+""+""+"");Ven_14.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "15:00":Ven_15.setText(j.getInt("seanceId")+""+""+"");Ven_15.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "16:00":Ven_16.setText(j.getInt("seanceId")+""+""+"");Ven_16.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "17:00":Ven_17.setText(j.getInt("seanceId")+""+""+"");Ven_17.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "18:00":Ven_18.setText(j.getInt("seanceId")+""+""+"");Ven_18.setBackgroundColor(Color.parseColor(color));
                            break;
                    }
                        break;
                    case 6:	 switch(dateTime){
                        case "08:00":Sam_08.setText(j.getInt("seanceId")+""+""+"");Sam_08.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "09:00":Sam_09.setText(j.getInt("seanceId")+""+""+"");Sam_09.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "10:00":Sam_10.setText(j.getInt("seanceId")+""+""+"");Sam_10.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "11:00":Sam_11.setText(j.getInt("seanceId")+""+""+"");Sam_11.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "12:00":Sam_12.setText(j.getInt("seanceId")+""+""+"");Sam_12.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "13:00":Sam_13.setText(j.getInt("seanceId")+""+""+"");Sam_13.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "14:00":Sam_14.setText(j.getInt("seanceId")+""+""+"");Sam_14.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "15:00":Sam_15.setText(j.getInt("seanceId")+""+""+"");Sam_15.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "16:00":Sam_16.setText(j.getInt("seanceId")+""+""+"");Sam_16.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "17:00":Sam_17.setText(j.getInt("seanceId")+""+""+"");Sam_17.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "18:00":Sam_18.setText(j.getInt("seanceId")+""+""+"");Sam_18.setBackgroundColor(Color.parseColor(color));
                            break;
                    }
                        break;
                    case 7:	 switch(dateTime){
                        case "08:00":Dim_08.setText(j.getInt("seanceId")+""+""+"");Dim_08.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "09:00":Dim_09.setText(j.getInt("seanceId")+""+""+"");Dim_09.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "10:00":Dim_10.setText(j.getInt("seanceId")+""+""+"");Dim_10.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "11:00":Dim_11.setText(j.getInt("seanceId")+""+""+"");Dim_11.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "12:00":Dim_12.setText(j.getInt("seanceId")+""+""+"");Dim_12.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "13:00":Dim_13.setText(j.getInt("seanceId")+""+""+"");Dim_13.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "14:00":Dim_14.setText(j.getInt("seanceId")+""+""+"");Dim_14.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "15:00":Dim_15.setText(j.getInt("seanceId")+""+""+"");Dim_15.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "16:00":Dim_16.setText(j.getInt("seanceId")+""+""+"");Dim_16.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "17:00":Dim_17.setText(j.getInt("seanceId")+""+""+"");Dim_17.setBackgroundColor(Color.parseColor(color));
                            break;
                        case "18:00":Dim_18.setText(j.getInt("seanceId")+""+""+"");Dim_18.setBackgroundColor(Color.parseColor(color));
                            break;
                    }
                        break;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    void getTache()
    {
        if(getIntent().getStringExtra("emploitype")!=null){
            switch (getIntent().getStringExtra("emploitype"))
            {
                case "0":urlTask=WS.URL+"tasks/"+startDateString+"/"+endDateString ;


                    break;
                case "1":urlTask=WS.URL+"tasks/"+startDateString+"/"+endDateString +"/"+id;


                    break;


            }
        }else{url=WS.URL+"tasks/"+startDateString+"/"+endDateString; }

        JsonArrayRequest jArray=new JsonArrayRequest(Request.Method.GET, urlTask, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                TextView day=findViewById(R.id.day);

                day.setText(startDateString+" -> "+endDateString);

                LocalDateTime dateFromResponse;
                JSONObject j = null;
                for(int i=0;i<response.length();i++){
                    try {
                        j = response.getJSONObject(i);
                        dateFromResponse=LocalDateTime.parse(j.getString("startDate"));
                        String dateTime=j.getString("startDate").substring(11,16);
                        if(LocalDateTime.now().compareTo(dateFromResponse)<=0)
                        {
                            color="#00DAC5";
                        }else
                            {
                                 color="#ff6347";
                            }
                        switch (dateFromResponse.getDayOfWeek().getValue()){

                            case 1: switch(dateTime){
                                case "08:00":Lun_08.setText(j.getInt("taskId")+""+""+"");Lun_08.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "09:00":Lun_09.setText(j.getInt("taskId")+""+""+"");Lun_09.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "10:00":Lun_10.setText(j.getInt("taskId")+""+""+"");Lun_10.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "11:00":Lun_11.setText(j.getInt("taskId")+""+""+"");Lun_11.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "12:00":Lun_12.setText(j.getInt("taskId")+""+""+"");Lun_12.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "13:00":Lun_13.setText(j.getInt("taskId")+""+""+"");Lun_13.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "14:00":Lun_14.setText(j.getInt("taskId")+""+""+"");Lun_14.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "15:00":Lun_15.setText(j.getInt("taskId")+""+""+"");Lun_15.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "16:00":Lun_16.setText(j.getInt("taskId")+""+""+"");Lun_16.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "17:00":Lun_17.setText(j.getInt("taskId")+""+""+"");Lun_17.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "18:00":Lun_18.setText(j.getInt("taskId")+""+""+"");Lun_18.setBackgroundColor(Color.parseColor(color));
                                    break;
                            }
                                break;
                            case 2: switch(dateTime){
                                case "08:00":Mar_08.setText(j.getInt("taskId")+""+""+"");Mar_08.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "09:00":Mar_09.setText(j.getInt("taskId")+""+""+"");Mar_09.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "10:00":Mar_10.setText(j.getInt("taskId")+""+""+"");Mar_10.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "11:00":Mar_11.setText(j.getInt("taskId")+""+""+"");Mar_11.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "12:00":Mar_12.setText(j.getInt("taskId")+""+""+"");Mar_12.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "13:00":Mar_13.setText(j.getInt("taskId")+""+""+"");Mar_13.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "14:00":Mar_14.setText(j.getInt("taskId")+""+""+"");Mar_14.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "15:00":Mar_15.setText(j.getInt("taskId")+""+""+"");Mar_15.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "16:00":Mar_16.setText(j.getInt("taskId")+""+""+"");Mar_16.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "17:00":Mar_17.setText(j.getInt("taskId")+""+""+"");Mar_17.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "18:00":Mar_18.setText(j.getInt("taskId")+""+""+"");Mar_18.setBackgroundColor(Color.parseColor(color));
                                    break;
                            }
                                break;
                            case 3:switch(dateTime){
                                case "08:00":Mer_08.setText(j.getInt("taskId")+""+""+"");Mer_08.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "09:00":Mer_09.setText(j.getInt("taskId")+""+""+"");Mer_09.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "10:00":Mer_10.setText(j.getInt("taskId")+""+""+"");Mer_10.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "11:00":Mer_11.setText(j.getInt("taskId")+""+""+"");Mer_11.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "12:00":Mer_12.setText(j.getInt("taskId")+""+""+"");Mer_12.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "13:00":Mer_13.setText(j.getInt("taskId")+""+""+"");Mer_13.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "14:00":Mer_14.setText(j.getInt("taskId")+""+""+"");Mer_14.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "15:00":Mer_15.setText(j.getInt("taskId")+""+""+"");Mer_15.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "16:00":Mer_16.setText(j.getInt("taskId")+""+""+"");Mer_16.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "17:00":Mer_17.setText(j.getInt("taskId")+""+""+"");Mer_17.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "18:00":Mer_18.setText(j.getInt("taskId")+""+""+"");Mer_18.setBackgroundColor(Color.parseColor(color));
                                    break;
                            }
                                break;
                            case 4:
                                switch(dateTime){
                                    case "08:00":Jeu_08.setText(j.getInt("taskId")+""+""+"");Jeu_08.setBackgroundColor(Color.parseColor(color));
                                        break;
                                    case "09:00":Jeu_09.setText(j.getInt("taskId")+""+""+"");Jeu_09.setBackgroundColor(Color.parseColor(color));
                                        break;
                                    case "10:00":Jeu_10.setText(j.getInt("taskId")+""+""+"");Jeu_10.setBackgroundColor(Color.parseColor(color));
                                        break;
                                    case "11:00":Jeu_11.setText(j.getInt("taskId")+""+""+"");Jeu_11.setBackgroundColor(Color.parseColor(color));
                                        break;
                                    case "12:00":Jeu_12.setText(j.getInt("taskId")+""+""+"");Jeu_12.setBackgroundColor(Color.parseColor(color));
                                        break;
                                    case "13:00":Jeu_13.setText(j.getInt("taskId")+""+""+"");Jeu_13.setBackgroundColor(Color.parseColor(color));
                                        break;
                                    case "14:00":Jeu_14.setText(j.getInt("taskId")+""+""+"");Jeu_14.setBackgroundColor(Color.parseColor(color));
                                        break;
                                    case "15:00":Jeu_15.setText(j.getInt("taskId")+""+""+"");Jeu_15.setBackgroundColor(Color.parseColor(color));
                                        break;
                                    case "16:00":Jeu_16.setText(j.getInt("taskId")+""+""+"");Jeu_16.setBackgroundColor(Color.parseColor(color));
                                        break;
                                    case "17:00":Jeu_17.setText(j.getInt("taskId")+""+""+"");Jeu_17.setBackgroundColor(Color.parseColor(color));
                                        break;
                                    case "18:00":Jeu_18.setText(j.getInt("taskId")+""+""+"");Jeu_18.setBackgroundColor(Color.parseColor(color));
                                        break;
                                }
                                break;
                            case 5:switch(dateTime){
                                case "08:00":Ven_08.setText(j.getInt("taskId")+""+""+"");Ven_08.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "09:00":Ven_09.setText(j.getInt("taskId")+""+""+"");Ven_09.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "10:00":Ven_10.setText(j.getInt("taskId")+""+""+"");Ven_10.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "11:00":Ven_11.setText(j.getInt("taskId")+""+""+"");Ven_11.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "12:00":Ven_12.setText(j.getInt("taskId")+""+""+"");Ven_12.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "13:00":Ven_13.setText(j.getInt("taskId")+""+""+"");Ven_13.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "14:00":Ven_14.setText(j.getInt("taskId")+""+""+"");Ven_14.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "15:00":Ven_15.setText(j.getInt("taskId")+""+""+"");Ven_15.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "16:00":Ven_16.setText(j.getInt("taskId")+""+""+"");Ven_16.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "17:00":Ven_17.setText(j.getInt("taskId")+""+""+"");Ven_17.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "18:00":Ven_18.setText(j.getInt("taskId")+""+""+"");Ven_18.setBackgroundColor(Color.parseColor(color));
                                    break;
                            }
                                break;
                            case 6:	 switch(dateTime){
                                case "08:00":Sam_08.setText(j.getInt("taskId")+""+""+"");Sam_08.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "09:00":Sam_09.setText(j.getInt("taskId")+""+""+"");Sam_09.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "10:00":Sam_10.setText(j.getInt("taskId")+""+""+"");Sam_10.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "11:00":Sam_11.setText(j.getInt("taskId")+""+""+"");Sam_11.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "12:00":Sam_12.setText(j.getInt("taskId")+""+""+"");Sam_12.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "13:00":Sam_13.setText(j.getInt("taskId")+""+""+"");Sam_13.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "14:00":Sam_14.setText(j.getInt("taskId")+""+""+"");Sam_14.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "15:00":Sam_15.setText(j.getInt("taskId")+""+""+"");Sam_15.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "16:00":Sam_16.setText(j.getInt("taskId")+""+""+"");Sam_16.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "17:00":Sam_17.setText(j.getInt("taskId")+""+""+"");Sam_17.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "18:00":Sam_18.setText(j.getInt("taskId")+""+""+"");Sam_18.setBackgroundColor(Color.parseColor(color));
                                    break;
                            }
                                break;
                            case 7:	 switch(dateTime){
                                case "08:00":Dim_08.setText(j.getInt("taskId")+""+""+"");Dim_08.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "09:00":Dim_09.setText(j.getInt("taskId")+""+""+"");Dim_09.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "10:00":Dim_10.setText(j.getInt("taskId")+""+""+"");Dim_10.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "11:00":Dim_11.setText(j.getInt("taskId")+""+""+"");Dim_11.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "12:00":Dim_12.setText(j.getInt("taskId")+""+""+"");Dim_12.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "13:00":Dim_13.setText(j.getInt("taskId")+""+""+"");Dim_13.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "14:00":Dim_14.setText(j.getInt("taskId")+""+""+"");Dim_14.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "15:00":Dim_15.setText(j.getInt("taskId")+""+""+"");Dim_15.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "16:00":Dim_16.setText(j.getInt("taskId")+""+""+"");Dim_16.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "17:00":Dim_17.setText(j.getInt("taskId")+""+""+"");Dim_17.setBackgroundColor(Color.parseColor(color));
                                    break;
                                case "18:00":Dim_18.setText(j.getInt("taskId")+""+""+"");Dim_18.setBackgroundColor(Color.parseColor(color));
                                    break;
                            }

                                break;
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }





                for(int i=0;i<response.length();i++) {

                    try {
                        j = response.getJSONObject(i);

                        databaseHandler.saveTask(new Task(j.getInt("taskId"), j.getString("startDate"), j.getInt("durationMinut"), j.getString("title"), j.getString("detail"), j.getString("isDone"),j.getInt("userFk")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("gettache",error+" ");
            }
        });
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jArray);

    }


    void getSeance(List<Seance> seances)
    {

        LocalDateTime dateFromResponse;

        for(Seance seance:seances){
            try {

                dateFromResponse=LocalDateTime.parse(seance.getStartDate());
                String dateTime=seance.getStartDate().substring(11,16);
                switch (dateFromResponse.getDayOfWeek().getValue()){
                    case 1: switch(dateTime){
                        case "08:00":Lun_08.setText(seance.getSeanceId()+""+""+"");Lun_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "09:00":Lun_09.setText(seance.getSeanceId()+""+""+"");Lun_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "10:00":Lun_10.setText(seance.getSeanceId()+""+""+"");Lun_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "11:00":Lun_11.setText(seance.getSeanceId()+""+""+"");Lun_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "12:00":Lun_12.setText(seance.getSeanceId()+""+""+"");Lun_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "13:00":Lun_13.setText(seance.getSeanceId()+""+""+"");Lun_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "14:00":Lun_14.setText(seance.getSeanceId()+""+""+"");Lun_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "15:00":Lun_15.setText(seance.getSeanceId()+""+""+"");Lun_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "16:00":Lun_16.setText(seance.getSeanceId()+""+""+"");Lun_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "17:00":Lun_17.setText(seance.getSeanceId()+""+""+"");Lun_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "18:00":Lun_18.setText(seance.getSeanceId()+""+""+"");Lun_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                    }
                        break;
                    case 2: switch(dateTime){
                        case "08:00":Mar_08.setText(seance.getSeanceId()+""+""+"");Mar_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "09:00":Mar_09.setText(seance.getSeanceId()+""+""+"");Mar_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "10:00":Mar_10.setText(seance.getSeanceId()+""+""+"");Mar_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "11:00":Mar_11.setText(seance.getSeanceId()+""+""+"");Mar_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "12:00":Mar_12.setText(seance.getSeanceId()+""+""+"");Mar_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "13:00":Mar_13.setText(seance.getSeanceId()+""+""+"");Mar_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "14:00":Mar_14.setText(seance.getSeanceId()+""+""+"");Mar_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "15:00":Mar_15.setText(seance.getSeanceId()+""+""+"");Mar_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "16:00":Mar_16.setText(seance.getSeanceId()+""+""+"");Mar_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "17:00":Mar_17.setText(seance.getSeanceId()+""+""+"");Mar_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "18:00":Mar_18.setText(seance.getSeanceId()+""+""+"");Mar_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                    }
                        break;
                    case 3:switch(dateTime){
                        case "08:00":Mer_08.setText(seance.getSeanceId()+""+""+"");Mer_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "09:00":Mer_09.setText(seance.getSeanceId()+""+""+"");Mer_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "10:00":Mer_10.setText(seance.getSeanceId()+""+""+"");Mer_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "11:00":Mer_11.setText(seance.getSeanceId()+""+""+"");Mer_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "12:00":Mer_12.setText(seance.getSeanceId()+""+""+"");Mer_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "13:00":Mer_13.setText(seance.getSeanceId()+""+""+"");Mer_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "14:00":Mer_14.setText(seance.getSeanceId()+""+""+"");Mer_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "15:00":Mer_15.setText(seance.getSeanceId()+""+""+"");Mer_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "16:00":Mer_16.setText(seance.getSeanceId()+""+""+"");Mer_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "17:00":Mer_17.setText(seance.getSeanceId()+""+""+"");Mer_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "18:00":Mer_18.setText(seance.getSeanceId()+""+""+"");Mer_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                    }
                        break;
                    case 4:
                        switch(dateTime){
                            case "08:00":Jeu_08.setText(seance.getSeanceId()+""+""+"");Jeu_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "09:00":Jeu_09.setText(seance.getSeanceId()+""+""+"");Jeu_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "10:00":Jeu_10.setText(seance.getSeanceId()+""+""+"");Jeu_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "11:00":Jeu_11.setText(seance.getSeanceId()+""+""+"");Jeu_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "12:00":Jeu_12.setText(seance.getSeanceId()+""+""+"");Jeu_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "13:00":Jeu_13.setText(seance.getSeanceId()+""+""+"");Jeu_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "14:00":Jeu_14.setText(seance.getSeanceId()+""+""+"");Jeu_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "15:00":Jeu_15.setText(seance.getSeanceId()+""+""+"");Jeu_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "16:00":Jeu_16.setText(seance.getSeanceId()+""+""+"");Jeu_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "17:00":Jeu_17.setText(seance.getSeanceId()+""+""+"");Jeu_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "18:00":Jeu_18.setText(seance.getSeanceId()+""+""+"");Jeu_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                        }
                        switch(dateTime){
                            case "08:00":Jeu_08.setText(seance.getSeanceId()+""+""+"");Jeu_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "09:00":Jeu_09.setText(seance.getSeanceId()+""+""+"");Jeu_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "10:00":Jeu_10.setText(seance.getSeanceId()+""+""+"");Jeu_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "11:00":Jeu_11.setText(seance.getSeanceId()+""+""+"");Jeu_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "12:00":Jeu_12.setText(seance.getSeanceId()+""+""+"");Jeu_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "13:00":Jeu_13.setText(seance.getSeanceId()+""+""+"");Jeu_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "14:00":Jeu_14.setText(seance.getSeanceId()+""+""+"");Jeu_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "15:00":Jeu_15.setText(seance.getSeanceId()+""+""+"");Jeu_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "16:00":Jeu_16.setText(seance.getSeanceId()+""+""+"");Jeu_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "17:00":Jeu_17.setText(seance.getSeanceId()+""+""+"");Jeu_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "18:00":Jeu_18.setText(seance.getSeanceId()+""+""+"");Jeu_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                        }

                        break;
                    case 5:switch(dateTime){
                        case "08:00":Ven_08.setText(seance.getSeanceId()+""+""+"");Ven_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "09:00":Ven_09.setText(seance.getSeanceId()+""+""+"");Ven_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "10:00":Ven_10.setText(seance.getSeanceId()+""+""+"");Ven_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "11:00":Ven_11.setText(seance.getSeanceId()+""+""+"");Ven_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "12:00":Ven_12.setText(seance.getSeanceId()+""+""+"");Ven_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "13:00":Ven_13.setText(seance.getSeanceId()+""+""+"");Ven_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "14:00":Ven_14.setText(seance.getSeanceId()+""+""+"");Ven_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "15:00":Ven_15.setText(seance.getSeanceId()+""+""+"");Ven_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "16:00":Ven_16.setText(seance.getSeanceId()+""+""+"");Ven_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "17:00":Ven_17.setText(seance.getSeanceId()+""+""+"");Ven_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "18:00":Ven_18.setText(seance.getSeanceId()+""+""+"");Ven_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                    }
                        break;
                    case 6:	 switch(dateTime){
                        case "08:00":Sam_08.setText(seance.getSeanceId()+""+""+"");Sam_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "09:00":Sam_09.setText(seance.getSeanceId()+""+""+"");Sam_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "10:00":Sam_10.setText(seance.getSeanceId()+""+""+"");Sam_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "11:00":Sam_11.setText(seance.getSeanceId()+""+""+"");Sam_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "12:00":Sam_12.setText(seance.getSeanceId()+""+""+"");Sam_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "13:00":Sam_13.setText(seance.getSeanceId()+""+""+"");Sam_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "14:00":Sam_14.setText(seance.getSeanceId()+""+""+"");Sam_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "15:00":Sam_15.setText(seance.getSeanceId()+""+""+"");Sam_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "16:00":Sam_16.setText(seance.getSeanceId()+""+""+"");Sam_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "17:00":Sam_17.setText(seance.getSeanceId()+""+""+"");Sam_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "18:00":Sam_18.setText(seance.getSeanceId()+""+""+"");Sam_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                    }
                        break;
                    case 7:	 switch(dateTime){
                        case "08:00":Dim_08.setText(seance.getSeanceId()+""+""+"");Dim_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "09:00":Dim_09.setText(seance.getSeanceId()+""+""+"");Dim_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "10:00":Dim_10.setText(seance.getSeanceId()+""+""+"");Dim_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "11:00":Dim_11.setText(seance.getSeanceId()+""+""+"");Dim_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "12:00":Dim_12.setText(seance.getSeanceId()+""+""+"");Dim_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "13:00":Dim_13.setText(seance.getSeanceId()+""+""+"");Dim_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "14:00":Dim_14.setText(seance.getSeanceId()+""+""+"");Dim_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "15:00":Dim_15.setText(seance.getSeanceId()+""+""+"");Dim_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "16:00":Dim_16.setText(seance.getSeanceId()+""+""+"");Dim_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "17:00":Dim_17.setText(seance.getSeanceId()+""+""+"");Dim_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                        case "18:00":Dim_18.setText(seance.getSeanceId()+""+""+"");Dim_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                            break;
                    }
                        break;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void seancedetails(View view) {
        TextView textView=findViewById(view.getId());
        if(textView.getText().toString().compareTo("")!=0){

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,  WS.URL+"seances/"+ textView.getText().toString(), null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    try {

                        AlertDialog.Builder builder = new AlertDialog.Builder(WeekView_Calendar.this);
                        builder.setTitle("Seance Detail:");
                        builder.setMessage("ID = "+ response.getInt("seanceId")+"\n\n" +"Sart Date = "+ response.getString("startDate")+"\n\n"+
                                "Duration = "+response.getInt("durationMinut")+"\n\n"+
                               "Comment = "+ response.getString("comments")
                                );
                        builder.setNegativeButton("Close", null);
                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                try {
                                    LocalDateTime date=LocalDateTime.parse(response.getString("startDate"));
                                    String dateDay=response.getString("startDate").substring(0,10);
                                    String dateTime=response.getString("startDate").substring(11,16);

                                   LocalDateTime  DateInit=LocalDateTime.of(LocalDate.parse(dateDay), LocalTime.parse(dateTime));
                                    StringRequest dr = new StringRequest(Request.Method.DELETE, "http://192.168.111.1:45455/seances/"+response.getInt("seanceId"),
                                            new Response.Listener<String>()
                                            {
                                                @Override
                                                public void onResponse(String res) {
                                                    // response

                                                    startActivity(getIntent());
                                                    Toast.makeText(WeekView_Calendar.this, "Deleted", Toast.LENGTH_LONG).show();
                                                }
                                            },
                                            new Response.ErrorListener()
                                            {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    // error.

                                                }
                                            }
                                    ); MySingleton.getInstance(WeekView_Calendar.this).addToRequestQueue(dr);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }});


                        AlertDialog dialog = builder.create();
                        dialog.show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(MainActivity.class.getSimpleName(), error.getMessage());

                            getSeance(databaseHandler.readSeance());
                        }
                    }

            );
            MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
           }
           else{
//            LocalDateTime dateFromResponse;
//            dateFromResponse=LocalDateTime.parse(seance.getStartDate());
//            String dateTime=seance.getStartDate().substring(11,16);
//            switch (view.getId()){
//
//                case R.id.Lun_08:dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                starDate
//
//                    String date=  dateFormatter.format(DateInit);
//                    Seance seance=new Seance(1,1,1,2,60,"",date);
//                    Intent splashIntent = new Intent(RecycleCalendar.this, DateTimePicker.class);
//                    splashIntent.putExtra("seance", (Serializable) seance);
//                    splashIntent.putExtra("time","08:00");
//                    WeekView_Calendar.this.startActivity(splashIntent);
//                    WeekView_Calendar.this.finish();
//                    break;
//                case R.id.Lun_09:Lun_09.Lun_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_10:Lun_10.setText(seance.getSeanceId()+""+""+"");Lun_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_11:Lun_11.setText(seance.getSeanceId()+""+""+"");Lun_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_12:Lun_12.setText(seance.getSeanceId()+""+""+"");Lun_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_13:Lun_13.setText(seance.getSeanceId()+""+""+"");Lun_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_14:Lun_14.setText(seance.getSeanceId()+""+""+"");Lun_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_15:Lun_15.setText(seance.getSeanceId()+""+""+"");Lun_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_16:Lun_16.setText(seance.getSeanceId()+""+""+"");Lun_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_17:Lun_17.setText(seance.getSeanceId()+""+""+"");Lun_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_18:Lun_18.setText(seance.getSeanceId()+""+""+"");Lun_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Mar_08:Mar_08.setText(seance.getSeanceId()+""+""+"");Mar_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_09:Mar_09.setText(seance.getSeanceId()+""+""+"");Mar_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_10:Mar_10.setText(seance.getSeanceId()+""+""+"");Mar_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case "1Mar_11:Mar_11.setText(seance.getSeanceId()+""+""+"");Mar_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_12:Mar_12.setText(seance.getSeanceId()+""+""+"");Mar_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_13:Mar_13.setText(seance.getSeanceId()+""+""+"");Mar_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_14:Mar_14.setText(seance.getSeanceId()+""+""+"");Mar_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_15:Mar_15.setText(seance.getSeanceId()+""+""+"");Mar_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_16:Mar_16.setText(seance.getSeanceId()+""+""+"");Mar_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_17:Mar_17.setText(seance.getSeanceId()+""+""+"");Mar_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_18:Mar_18.setText(seance.getSeanceId()+""+""+"");Mar_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Mer_08:Mer_08.setText(seance.getSeanceId()+""+""+"");Mer_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_09:Mer_09.setText(seance.getSeanceId()+""+""+"");Mer_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_10:Mer_10.setText(seance.getSeanceId()+""+""+"");Mer_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_11:Mer_11.setText(seance.getSeanceId()+""+""+"");Mer_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_12:Mer_12.setText(seance.getSeanceId()+""+""+"");Mer_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_13:Mer_13.setText(seance.getSeanceId()+""+""+"");Mer_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_14:Mer_14.setText(seance.getSeanceId()+""+""+"");Mer_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_15:Mer_15.setText(seance.getSeanceId()+""+""+"");Mer_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_16:Mer_16.setText(seance.getSeanceId()+""+""+"");Mer_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_17:Mer_17.setText(seance.getSeanceId()+""+""+"");Mer_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_18:Mer_18.setText(seance.getSeanceId()+""+""+"");Mer_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Jeu_08:Jeu_08.setText(seance.getSeanceId()+""+""+"");Jeu_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_09:Jeu_09.setText(seance.getSeanceId()+""+""+"");Jeu_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_10:Jeu_10.setText(seance.getSeanceId()+""+""+"");Jeu_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_11:Jeu_11.setText(seance.getSeanceId()+""+""+"");Jeu_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_12:Jeu_12.setText(seance.getSeanceId()+""+""+"");Jeu_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_13:Jeu_13.setText(seance.getSeanceId()+""+""+"");Jeu_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_14:Jeu_14.setText(seance.getSeanceId()+""+""+"");Jeu_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_15:Jeu_15.setText(seance.getSeanceId()+""+""+"");Jeu_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_16:Jeu_16.setText(seance.getSeanceId()+""+""+"");Jeu_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_17:Jeu_17.setText(seance.getSeanceId()+""+""+"");Jeu_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_18:Jeu_18.setText(seance.getSeanceId()+""+""+"");Jeu_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Jeu_08:Jeu_08.setText(seance.getSeanceId()+""+""+"");Jeu_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_09:Jeu_09.setText(seance.getSeanceId()+""+""+"");Jeu_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_10:Jeu_10.setText(seance.getSeanceId()+""+""+"");Jeu_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_11:Jeu_11.setText(seance.getSeanceId()+""+""+"");Jeu_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_12:Jeu_12.setText(seance.getSeanceId()+""+""+"");Jeu_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_13:Jeu_13.setText(seance.getSeanceId()+""+""+"");Jeu_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_14:Jeu_14.setText(seance.getSeanceId()+""+""+"");Jeu_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_15:Jeu_15.setText(seance.getSeanceId()+""+""+"");Jeu_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_16:Jeu_16.setText(seance.getSeanceId()+""+""+"");Jeu_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_17:Jeu_17.setText(seance.getSeanceId()+""+""+"");Jeu_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_18:Jeu_18.setText(seance.getSeanceId()+""+""+"");Jeu_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Ven_08:Ven_08.setText(seance.getSeanceId()+""+""+"");Ven_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_09:Ven_09.setText(seance.getSeanceId()+""+""+"");Ven_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_10:Ven_10.setText(seance.getSeanceId()+""+""+"");Ven_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_11:Ven_11.setText(seance.getSeanceId()+""+""+"");Ven_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_12:Ven_12.setText(seance.getSeanceId()+""+""+"");Ven_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_13:Ven_13.setText(seance.getSeanceId()+""+""+"");Ven_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_14:Ven_14.setText(seance.getSeanceId()+""+""+"");Ven_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_15:Ven_15.setText(seance.getSeanceId()+""+""+"");Ven_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_16:Ven_16.setText(seance.getSeanceId()+""+""+"");Ven_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_17:Ven_17.setText(seance.getSeanceId()+""+""+"");Ven_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_18:Ven_18.setText(seance.getSeanceId()+""+""+"");Ven_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Sam_08:Sam_08.setText(seance.getSeanceId()+""+""+"");Sam_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_09:Sam_09.setText(seance.getSeanceId()+""+""+"");Sam_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_10:Sam_10.setText(seance.getSeanceId()+""+""+"");Sam_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_11:Sam_11.setText(seance.getSeanceId()+""+""+"");Sam_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_12:Sam_12.setText(seance.getSeanceId()+""+""+"");Sam_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_13:Sam_13.setText(seance.getSeanceId()+""+""+"");Sam_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_14:Sam_14.setText(seance.getSeanceId()+""+""+"");Sam_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_15:Sam_15.setText(seance.getSeanceId()+""+""+"");Sam_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_16:Sam_16.setText(seance.getSeanceId()+""+""+"");Sam_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_17:Sam_17.setText(seance.getSeanceId()+""+""+"");Sam_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_18:Sam_18.setText(seance.getSeanceId()+""+""+"");Sam_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Dim_08:Dim_08.setText(seance.getSeanceId()+""+""+"");Dim_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_09:Dim_09.setText(seance.getSeanceId()+""+""+"");Dim_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_10:Dim_10.setText(seance.getSeanceId()+""+""+"");Dim_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_11:Dim_11.setText(seance.getSeanceId()+""+""+"");Dim_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_12:Dim_12.setText(seance.getSeanceId()+""+""+"");Dim_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_13:Dim_13.setText(seance.getSeanceId()+""+""+"");Dim_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_14:Dim_14.setText(seance.getSeanceId()+""+""+"");Dim_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_15:Dim_15.setText(seance.getSeanceId()+""+""+"");Dim_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_16:Dim_16.setText(seance.getSeanceId()+""+""+"");Dim_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_17:Dim_17.setText(seance.getSeanceId()+""+""+"");Dim_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_18:Dim_18.setText(seance.getSeanceId()+""+""+"");Dim_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Lun_08:Lun_08.setText(seance.getSeanceId()+""+""+"");Lun_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_09:Lun_09.setText(seance.getSeanceId()+""+""+"");Lun_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_10:Lun_10.setText(seance.getSeanceId()+""+""+"");Lun_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_11:Lun_11.setText(seance.getSeanceId()+""+""+"");Lun_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_12:Lun_12.setText(seance.getSeanceId()+""+""+"");Lun_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_13:Lun_13.setText(seance.getSeanceId()+""+""+"");Lun_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_14:Lun_14.setText(seance.getSeanceId()+""+""+"");Lun_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_15:Lun_15.setText(seance.getSeanceId()+""+""+"");Lun_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_16:Lun_16.setText(seance.getSeanceId()+""+""+"");Lun_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_17:Lun_17.setText(seance.getSeanceId()+""+""+"");Lun_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_18:Lun_18.setText(seance.getSeanceId()+""+""+"");Lun_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Mar_08:Mar_08.setText(seance.getSeanceId()+""+""+"");Mar_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_09:Mar_09.setText(seance.getSeanceId()+""+""+"");Mar_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_10:Mar_10.setText(seance.getSeanceId()+""+""+"");Mar_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case "1Mar_11:Mar_11.setText(seance.getSeanceId()+""+""+"");Mar_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_12:Mar_12.setText(seance.getSeanceId()+""+""+"");Mar_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_13:Mar_13.setText(seance.getSeanceId()+""+""+"");Mar_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_14:Mar_14.setText(seance.getSeanceId()+""+""+"");Mar_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_15:Mar_15.setText(seance.getSeanceId()+""+""+"");Mar_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_16:Mar_16.setText(seance.getSeanceId()+""+""+"");Mar_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_17:Mar_17.setText(seance.getSeanceId()+""+""+"");Mar_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_18:Mar_18.setText(seance.getSeanceId()+""+""+"");Mar_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Mer_08:Mer_08.setText(seance.getSeanceId()+""+""+"");Mer_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_09:Mer_09.setText(seance.getSeanceId()+""+""+"");Mer_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_10:Mer_10.setText(seance.getSeanceId()+""+""+"");Mer_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_11:Mer_11.setText(seance.getSeanceId()+""+""+"");Mer_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_12:Mer_12.setText(seance.getSeanceId()+""+""+"");Mer_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_13:Mer_13.setText(seance.getSeanceId()+""+""+"");Mer_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_14:Mer_14.setText(seance.getSeanceId()+""+""+"");Mer_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_15:Mer_15.setText(seance.getSeanceId()+""+""+"");Mer_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_16:Mer_16.setText(seance.getSeanceId()+""+""+"");Mer_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_17:Mer_17.setText(seance.getSeanceId()+""+""+"");Mer_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_18:Mer_18.setText(seance.getSeanceId()+""+""+"");Mer_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Jeu_08:Jeu_08.setText(seance.getSeanceId()+""+""+"");Jeu_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_09:Jeu_09.setText(seance.getSeanceId()+""+""+"");Jeu_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_10:Jeu_10.setText(seance.getSeanceId()+""+""+"");Jeu_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_11:Jeu_11.setText(seance.getSeanceId()+""+""+"");Jeu_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_12:Jeu_12.setText(seance.getSeanceId()+""+""+"");Jeu_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_13:Jeu_13.setText(seance.getSeanceId()+""+""+"");Jeu_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_14:Jeu_14.setText(seance.getSeanceId()+""+""+"");Jeu_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_15:Jeu_15.setText(seance.getSeanceId()+""+""+"");Jeu_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_16:Jeu_16.setText(seance.getSeanceId()+""+""+"");Jeu_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_17:Jeu_17.setText(seance.getSeanceId()+""+""+"");Jeu_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_18:Jeu_18.setText(seance.getSeanceId()+""+""+"");Jeu_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Jeu_08:Jeu_08.setText(seance.getSeanceId()+""+""+"");Jeu_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_09:Jeu_09.setText(seance.getSeanceId()+""+""+"");Jeu_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_10:Jeu_10.setText(seance.getSeanceId()+""+""+"");Jeu_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_11:Jeu_11.setText(seance.getSeanceId()+""+""+"");Jeu_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_12:Jeu_12.setText(seance.getSeanceId()+""+""+"");Jeu_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_13:Jeu_13.setText(seance.getSeanceId()+""+""+"");Jeu_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_14:Jeu_14.setText(seance.getSeanceId()+""+""+"");Jeu_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_15:Jeu_15.setText(seance.getSeanceId()+""+""+"");Jeu_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_16:Jeu_16.setText(seance.getSeanceId()+""+""+"");Jeu_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_17:Jeu_17.setText(seance.getSeanceId()+""+""+"");Jeu_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_18:Jeu_18.setText(seance.getSeanceId()+""+""+"");Jeu_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Ven_08:Ven_08.setText(seance.getSeanceId()+""+""+"");Ven_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_09:Ven_09.setText(seance.getSeanceId()+""+""+"");Ven_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_10:Ven_10.setText(seance.getSeanceId()+""+""+"");Ven_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_11:Ven_11.setText(seance.getSeanceId()+""+""+"");Ven_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_12:Ven_12.setText(seance.getSeanceId()+""+""+"");Ven_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_13:Ven_13.setText(seance.getSeanceId()+""+""+"");Ven_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_14:Ven_14.setText(seance.getSeanceId()+""+""+"");Ven_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_15:Ven_15.setText(seance.getSeanceId()+""+""+"");Ven_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_16:Ven_16.setText(seance.getSeanceId()+""+""+"");Ven_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_17:Ven_17.setText(seance.getSeanceId()+""+""+"");Ven_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_18:Ven_18.setText(seance.getSeanceId()+""+""+"");Ven_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Sam_08:Sam_08.setText(seance.getSeanceId()+""+""+"");Sam_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_09:Sam_09.setText(seance.getSeanceId()+""+""+"");Sam_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_10:Sam_10.setText(seance.getSeanceId()+""+""+"");Sam_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_11:Sam_11.setText(seance.getSeanceId()+""+""+"");Sam_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_12:Sam_12.setText(seance.getSeanceId()+""+""+"");Sam_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_13:Sam_13.setText(seance.getSeanceId()+""+""+"");Sam_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_14:Sam_14.setText(seance.getSeanceId()+""+""+"");Sam_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_15:Sam_15.setText(seance.getSeanceId()+""+""+"");Sam_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_16:Sam_16.setText(seance.getSeanceId()+""+""+"");Sam_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_17:Sam_17.setText(seance.getSeanceId()+""+""+"");Sam_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_18:Sam_18.setText(seance.getSeanceId()+""+""+"");Sam_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Dim_08:Dim_08.setText(seance.getSeanceId()+""+""+"");Dim_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_09:Dim_09.setText(seance.getSeanceId()+""+""+"");Dim_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_10:Dim_10.setText(seance.getSeanceId()+""+""+"");Dim_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_11:Dim_11.setText(seance.getSeanceId()+""+""+"");Dim_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_12:Dim_12.setText(seance.getSeanceId()+""+""+"");Dim_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_13:Dim_13.setText(seance.getSeanceId()+""+""+"");Dim_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_14:Dim_14.setText(seance.getSeanceId()+""+""+"");Dim_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_15:Dim_15.setText(seance.getSeanceId()+""+""+"");Dim_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_16:Dim_16.setText(seance.getSeanceId()+""+""+"");Dim_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_17:Dim_17.setText(seance.getSeanceId()+""+""+"");Dim_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_18:Dim_18.setText(seance.getSeanceId()+""+""+"");Dim_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//
//                }
//
//            }

        }


    }



    public void onclickbtn(View view) {
         findViewById(R.id.floatingActionButtonweek).setVisibility(View.INVISIBLE);findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
    }


}