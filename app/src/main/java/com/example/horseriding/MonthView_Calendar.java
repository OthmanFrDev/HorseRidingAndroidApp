package com.example.horseriding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MonthView_Calendar extends AppCompatActivity {
    LocalDate dateInit= LocalDate.of(2020, 9, 14);
    TextView Lun_1,Mar_1,Mer_1,Jeu_1,Ven_1,Sam_1,Dim_1,
            Lun_2,Mar_2,Mer_2,Jeu_2,Ven_2,Sam_2,Dim_2,
            Lun_3,Mar_3,Mer_3,Jeu_3,Ven_3,Sam_3,Dim_3,
            Lun_4,Mar_4,Mer_4,Jeu_4,Ven_4,Sam_4,Dim_4,
            Lun_5,Mar_5,Mer_5,Jeu_5,Ven_5,Sam_5,Dim_5;
    LocalDate starDate,endDate;
    TextView day;
    LocalDate firstDate=dateInit.withDayOfMonth(1);


    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_view);
        day=findViewById(R.id.day);
        Lun_1=findViewById(R.id.Lun_1);
        Mar_1=findViewById(R.id.Mar_1);
        Mer_1=findViewById(R.id.Mer_1);
        Jeu_1=findViewById(R.id.Jeu_1);
        Ven_1=findViewById(R.id.Ven_1);
        Sam_1=findViewById(R.id.Sam_1);
        Dim_1=findViewById(R.id.Dim_1);
        Lun_2=findViewById(R.id.Lun_2);
        Mar_2=findViewById(R.id.Mar_2);
        Mer_2=findViewById(R.id.Mer_2);
        Jeu_2=findViewById(R.id.Jeu_2);
        Ven_2=findViewById(R.id.Ven_2);
        Sam_2=findViewById(R.id.Sam_2);
        Dim_2=findViewById(R.id.Dim_2);
        Lun_3=findViewById(R.id.Lun_3);
        Mar_3=findViewById(R.id.Mar_3);
        Mer_3=findViewById(R.id.Mer_3);
        Jeu_3=findViewById(R.id.Jeu_3);
        Ven_3=findViewById(R.id.Ven_3);
        Sam_3=findViewById(R.id.Sam_3);
        Dim_3=findViewById(R.id.Dim_3);
        Lun_4=findViewById(R.id.Lun_4);
        Mar_4=findViewById(R.id.Mar_4);
        Mer_4=findViewById(R.id.Mer_4);
        Jeu_4=findViewById(R.id.Jeu_4);
        Ven_4=findViewById(R.id.Ven_4);
        Sam_4=findViewById(R.id.Sam_4);
        Dim_4=findViewById(R.id.Dim_4);
        Lun_5=findViewById(R.id.Lun_5);
        Mar_5=findViewById(R.id.Mar_5);
        Mer_5=findViewById(R.id.Mer_5);
        Jeu_5=findViewById(R.id.Jeu_5);
        Ven_5=findViewById(R.id.Ven_5);
        Sam_5=findViewById(R.id.Sam_5);
        Dim_5=findViewById(R.id.Dim_5);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mI=getMenuInflater();
        mI.inflate(R.menu.menu_calendar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i=null;
        switch (item.getItemId()){
            case R.id.month_view:findViewById(R.id.week_view).setVisibility(View.INVISIBLE);break;
            case R.id.week_view:i=new Intent(MonthView_Calendar.this, WeekView_Calendar.class);startActivity(i);finish();break;
            case R.id.day_view:i=new Intent(MonthView_Calendar.this, RecyclerView.class);startActivity(i);finish();break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        starDate=firstDate;endDate=firstDate;
        day.setText(firstDate.getMonth().toString()+" "+firstDate.getYear());
        switch(firstDate.getDayOfWeek().getValue()) {
            case 1:starDate=firstDate;endDate=starDate.plusDays(6);break;
            case 2:starDate=firstDate.minusDays(1);endDate=starDate.plusDays(6);break;
            case 3:starDate=firstDate.minusDays(2);endDate=starDate.plusDays(6);break;
            case 4:starDate=firstDate.minusDays(3);endDate=starDate.plusDays(6);break;
            case 5:starDate=firstDate.minusDays(4);endDate=starDate.plusDays(6);break;
            case 6:starDate=firstDate.minusDays(5);endDate=starDate.plusDays(6);break;
            case 7:starDate=firstDate.minusDays(6);endDate=starDate.plusDays(6);break;
        }
        String startDateString = dateFormatter.format(starDate);
        String endDateString = dateFormatter.format(endDate);
        JsonArrayRequest jsonWeek1=new JsonArrayRequest(Request.Method.GET, WS.URL+"seances/"+startDateString+"/"+endDateString, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                LocalDateTime dateFromResponse;
                JSONObject j = null;
                for(int i=0;i<response.length();i++){
                    try {
                        j = response.getJSONObject(i);
                        dateFromResponse=LocalDateTime.parse(j.getString("startDate"));
                        switch (dateFromResponse.getDayOfWeek().getValue()){
                            case 1:Lun_1.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Lun_1.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 2:Mar_1.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Mar_1.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 3:Mer_1.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Mer_1.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 4:Jeu_1.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Jeu_1.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 5:Ven_1.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Ven_1.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 6:Sam_1.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Sam_1.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 7:Dim_1.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Dim_1.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonWeek1);
        starDate=starDate.plusDays(7);
        endDate=endDate.plusDays(7);
        startDateString=dateFormatter.format(starDate);
        endDateString=dateFormatter.format(endDate);
        JsonArrayRequest jsonWeek2=new JsonArrayRequest(Request.Method.GET, WS.URL+"seances/"+startDateString+"/"+endDateString, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                LocalDateTime dateFromResponse;
                JSONObject j = null;
                for(int i=0;i<response.length();i++){
                    try {
                        j = response.getJSONObject(i);
                        dateFromResponse=LocalDateTime.parse(j.getString("startDate"));
                        switch (dateFromResponse.getDayOfWeek().getValue()){
                            case 1:Lun_2.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Lun_2.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 2:Mar_2.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Mar_2.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 3:Mer_2.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Mer_2.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 4:Jeu_2.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Jeu_2.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 5:Ven_2.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Ven_2.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 6:Sam_2.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Sam_2.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 7:Dim_2.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Dim_2.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonWeek2);

        starDate=starDate.plusDays(7);
        endDate=endDate.plusDays(7);
        startDateString=dateFormatter.format(starDate);
        endDateString=dateFormatter.format(endDate);
        JsonArrayRequest jsonWeek3=new JsonArrayRequest(Request.Method.GET, WS.URL+"seances/"+startDateString+"/"+endDateString, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                LocalDateTime dateFromResponse;
                JSONObject j = null;
                for(int i=0;i<response.length();i++){
                    try {
                        j = response.getJSONObject(i);
                        dateFromResponse=LocalDateTime.parse(j.getString("startDate"));
                        switch (dateFromResponse.getDayOfWeek().getValue()){
                            case 1:Lun_3.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Lun_3.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 2:Mar_3.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Mar_3.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 3:Mer_3.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Mer_3.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 4:Jeu_3.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Jeu_3.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 5:Ven_3.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Ven_3.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 6:Sam_3.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Sam_3.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 7:Dim_3.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Dim_3.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonWeek3);

        starDate=starDate.plusDays(7);
        endDate=endDate.plusDays(7);
        startDateString=dateFormatter.format(starDate);
        endDateString=dateFormatter.format(endDate);
        JsonArrayRequest jsonWeek4=new JsonArrayRequest(Request.Method.GET, WS.URL+"seances/"+startDateString+"/"+endDateString, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                LocalDateTime dateFromResponse;
                JSONObject j = null;
                for(int i=0;i<response.length();i++){
                    try {
                        j = response.getJSONObject(i);
                        dateFromResponse=LocalDateTime.parse(j.getString("startDate"));
                        switch (dateFromResponse.getDayOfWeek().getValue()){
                            case 1:Lun_4.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Lun_4.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 2:Mar_4.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Mar_4.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 3:Mer_4.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Mer_4.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 4:Jeu_4.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Jeu_4.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 5:Ven_4.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Ven_4.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 6:Sam_4.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Sam_4.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 7:Dim_4.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Dim_4.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonWeek4);

        starDate=starDate.plusDays(7);
        endDate=endDate.plusDays(7);
        startDateString=dateFormatter.format(starDate);
        endDateString=dateFormatter.format(endDate);
        JsonArrayRequest jsonWeek5=new JsonArrayRequest(Request.Method.GET, WS.URL+"seances/"+startDateString+"/"+endDateString, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                LocalDateTime dateFromResponse;
                JSONObject j = null;
                for(int i=0;i<response.length();i++){
                    try {
                        j = response.getJSONObject(i);
                        dateFromResponse=LocalDateTime.parse(j.getString("startDate"));
                        switch (dateFromResponse.getDayOfWeek().getValue()){
                            case 1:Lun_5.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Lun_5.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 2:Mar_5.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Mar_5.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 3:Mer_5.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Mer_5.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 4:Jeu_5.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Jeu_5.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 5:Ven_5.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Ven_5.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 6:Sam_5.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Sam_5.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                            case 7:Dim_5.append(j.getInt("seanceId")+"\n"+""+""+dateFromResponse.getDayOfMonth());Dim_5.setBackgroundColor(Color.parseColor("#EC7C32"));break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonWeek5);

    }

    public void previousMonth(View view) {
        firstDate=firstDate.minusMonths(1);
        onResume();

    }

    public void nextMonth(View view) {
        firstDate=firstDate.plusMonths(1);
        onResume();
    }
}