package com.example.horseriding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public class RecycleCalendar extends AppCompatActivity {
    List<Seance> lSeance;
    TextView time_08,time_09,time_10,time_11,time_12,time_13,time_14,time_15,time_16,time_17,time_18;
    TextView day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_calendar);

    }

    @Override
    protected void onResume() {
        super.onResume();
        time_08=findViewById(R.id.time_08);
        time_09=findViewById(R.id.time_09);
        time_10=findViewById(R.id.time_10);
        time_11=findViewById(R.id.time_11);
        time_12=findViewById(R.id.time_12);
        time_13=findViewById(R.id.time_13);
        time_14=findViewById(R.id.time_14);
        time_15=findViewById(R.id.time_15);
        time_16=findViewById(R.id.time_16);
        time_17=findViewById(R.id.time_17);
        time_18=findViewById(R.id.time_18);
        LocalDateTime DateInit=LocalDateTime.of(2020, 9, 14, 15, 56);
        Locale local=new Locale("fr","Fr");

        //DateTimeFormatter dt= ;
        day=findViewById(R.id.day);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //day.setText(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT).withLocale(local).format(DateInit));
        day.setText(dateFormatter.format(DateInit));
        JsonArrayRequest jArray=new JsonArrayRequest(Request.Method.GET, "http://192.168.1.7:45455/seances/getwithdate/"+day.getText().toString(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject j = null;
                for (int i = 0; i < response.length(); i++) {
                    Log.w("debuuuuuuuuuuuuuuuuuuuuuuuuuuuugggg","raniiii fl boucle "+response);
                    try {
                        j = response.getJSONObject(i);
                        String dateDay=j.getString("startDate").substring(0,10);
                        String dateTime=j.getString("startDate").substring(11,16);
                        switch(dateTime){
                            case "08:00":time_08.setText(j.getInt("seanceId")+" "+j.getInt("monitorId")+" Durée "+j.getInt("durationMinut")+"min");time_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                            case "09:00":time_09.setText(j.getInt("seanceId")+" "+j.getInt("monitorId")+" Durée "+j.getInt("durationMinut")+"min");time_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                            case "10:00":time_10.setText(j.getInt("seanceId")+" "+j.getInt("monitorId")+" Durée "+j.getInt("durationMinut")+"min");time_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                            case "11:00":time_11.setText(j.getInt("seanceId")+" "+j.getInt("monitorId")+" Durée "+j.getInt("durationMinut")+"min");time_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                            case "12:00":time_12.setText(j.getInt("seanceId")+" "+j.getInt("monitorId")+" Durée "+j.getInt("durationMinut")+"min");time_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                            case "13:00":time_13.setText(j.getInt("seanceId")+" "+j.getInt("monitorId")+" Durée "+j.getInt("durationMinut")+"min");time_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                            case "14:00":time_14.setText(j.getInt("seanceId")+" "+j.getInt("monitorId")+" Durée "+j.getInt("durationMinut")+"min");time_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                            case "15:00":time_15.setText(j.getInt("seanceId")+" "+j.getInt("monitorId")+" Durée "+j.getInt("durationMinut")+"min");time_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                            case "16:00":time_16.setText(j.getInt("seanceId")+" "+j.getInt("monitorId")+" Durée "+j.getInt("durationMinut")+"min");time_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                            case "17:00":time_17.setText(j.getInt("seanceId")+" "+j.getInt("monitorId")+" Durée "+j.getInt("durationMinut")+"min");time_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                            case "18:00":time_18.setText(j.getInt("seanceId")+" "+j.getInt("monitorId")+" Durée "+j.getInt("durationMinut")+"min");time_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("ONERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOOOR"," "+error);
            }
        });
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jArray);
    }
}