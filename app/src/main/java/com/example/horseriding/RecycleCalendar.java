package com.example.horseriding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class RecycleCalendar extends AppCompatActivity {
   static LocalDateTime DateInit;
   static LocalDateTime NextDate;
  static String DateInitString="";

    List<Seance> lSeance;
    TextView time_08,time_09,time_10,time_11,time_12,time_13,time_14,time_15,time_16,time_17,time_18;
    TextView day;
    DateTimeFormatter dateFormatter;
    Intent splashIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);
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
            case R.id.month_view:i=new Intent(RecycleCalendar.this, Month_view.class);startActivity(i);finish();break;
            case R.id.week_view:i=new Intent(RecycleCalendar.this, WeekView_Calendar.class);startActivity(i);finish();break;
            case R.id.day_view:findViewById(R.id.week_view).setVisibility(View.INVISIBLE);
        }
        return true;
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

        Locale local=new Locale("fr","Fr");

//DateInit=LocalDateTime.of();
        if(DateInit==null){DateInit=LocalDateTime.of(2020,9,14,15,48);}

        //DateTimeFormatter dt= ;
        day=findViewById(R.id.day);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //day.setText(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT).withLocale(local).format(DateInit));
        day.setText(dateFormatter.format(DateInit));
        JsonArrayRequest jArray=new JsonArrayRequest(Request.Method.GET, WS.URL+"+seances/getwithdate/"+day.getText().toString(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject j = null;
                for (int i = 0; i < response.length(); i++) {
                    Log.w("debuuuuuuuuuuuuuuuuuuuuuuuuuuuugggg","raniiii fl boucle "+response);
                    try {
                        j = response.getJSONObject(i);
                        Seance seance = new Seance(j.getInt("seanceId"),j.getInt("seanceGrpId"),j.getInt("clientId"),j.getInt("monitorId"),j.getInt("durationMinut"),j.getString("comments"),j.getString("startDate"));
                        DatabaseHandler databaseHandler =new DatabaseHandler(RecycleCalendar.this);
                      databaseHandler.saveSeance(seance);
                   //     Log.w("databaaaaaaaaaaaaaaaaaase",databaseHandler.readSeance(seance.getSeanceId()));
                        String dateDay=j.getString("startDate").substring(0,10);
                        String dateTime=j.getString("startDate").substring(11,16);
                        ConnectivityManager cm=(ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                        if(cm.getActiveNetworkInfo()==null||!cm.getActiveNetworkInfo().isConnected())
                        {


                        }
                        else
                        {
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
                        }}
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    catch(Exception ex){ex.printStackTrace();}
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

    public void nextday(View view) {
        init();
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
        switch(view.getId())
        {
            case R.id.btnnext:DateInit= DateInit.plusDays(1);
                break;
            case R.id.btnprevious:DateInit= DateInit.minusDays(1);
                break;
        }
        Locale local=new Locale("fr","Fr");


        //DateTimeFormatter dt= ;
        day=findViewById(R.id.day);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //day.setText(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT).withLocale(local).format(DateInit));
        day.setText(dateFormatter.format(DateInit));
        JsonArrayRequest jArray=new JsonArrayRequest(Request.Method.GET, WS.URL+"seances/getwithdate/"+day.getText().toString(), null, new Response.Listener<JSONArray>() {
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
    public void previousDay(View view) {
        init();
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

        Locale local=new Locale("fr","Fr");
        DateInit= DateInit.plusDays(1);

        //DateTimeFormatter dt= ;
        day=findViewById(R.id.day);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //day.setText(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT).withLocale(local).format(DateInit));
        day.setText(dateFormatter.format(DateInit));
        JsonArrayRequest jArray=new JsonArrayRequest(Request.Method.GET, WS.URL+"seances/getwithdate/"+day.getText().toString(), null, new Response.Listener<JSONArray>() {
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
    void init()
    {
        time_08.setText("VIDE");
        time_09.setText("VIDE");
        time_10.setText("VIDE");
        time_11.setText("VIDE");
        time_12.setText("VIDE");
        time_13.setText("VIDE");
        time_14.setText("VIDE");
        time_15.setText("VIDE");
        time_16.setText("VIDE");
        time_17.setText("VIDE");
        time_18.setBackgroundColor(Color.parseColor("#ffffff"));
        time_08.setBackgroundColor(Color.parseColor("#ffffff"));
        time_09.setBackgroundColor(Color.parseColor("#ffffff"));
        time_10.setBackgroundColor(Color.parseColor("#ffffff"));
        time_11.setBackgroundColor(Color.parseColor("#ffffff"));
        time_12.setBackgroundColor(Color.parseColor("#ffffff"));
        time_13.setBackgroundColor(Color.parseColor("#ffffff"));
        time_14.setBackgroundColor(Color.parseColor("#ffffff"));
        time_15.setBackgroundColor(Color.parseColor("#ffffff"));
        time_16.setBackgroundColor(Color.parseColor("#ffffff"));
        time_17.setBackgroundColor(Color.parseColor("#ffffff"));
        time_18.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    public void addSeance(View view) {
        TextView textView=findViewById(view.getId());
        if(textView.getText().toString().compareTo("VIDE")!=0){
           String[] list= textView.getText().toString().split(" ");
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,  WS.URL+"seances/"+ list[0], null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        AlertDialog.Builder builder = new AlertDialog.Builder(RecycleCalendar.this);
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

                                    DateInit=LocalDateTime.of(LocalDate.parse(dateDay),LocalTime.parse(dateTime));
                                    StringRequest dr = new StringRequest(Request.Method.DELETE, WS.URL+"seances/"+response.getInt("seanceId"),
                                            new Response.Listener<String>()
                                            {
                                                @Override
                                                public void onResponse(String res) {
                                                    // response

                                                    startActivity(getIntent());
                                                    Toast.makeText(RecycleCalendar.this, "Deleted", Toast.LENGTH_LONG).show();
                                                }
                                            },
                                            new Response.ErrorListener()
                                            {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    // error.

                                                }
                                            }
                                    ); MySingleton.getInstance(RecycleCalendar.this).addToRequestQueue(dr);
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
                            Log.e(RecycleCalendar.class.getSimpleName(), error.getMessage());
                        }
                    }

            );
            MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
        }
        else{
            switch(view.getId()){
                case R.id.time_08: dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String date=  dateFormatter.format(DateInit);
                    Seance seance=new Seance(1,1,1,2,60,"",date);
                    Intent splashIntent = new Intent(RecycleCalendar.this, DateTimePicker.class);
                    splashIntent.putExtra("seance", (Serializable) seance);
                    splashIntent.putExtra("time","08:00");
                    RecycleCalendar.this.startActivity(splashIntent);
                    RecycleCalendar.this.finish();
                    break;
                case R.id.time_09: dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    dateFormatter.format(DateInit);
                    splashIntent = new Intent(RecycleCalendar.this, DateTimePicker.class);
                    splashIntent.putExtra("seance", (Serializable)  new Seance(1,1,1,2,60,"",dateFormatter.format(DateInit)));
                    splashIntent.putExtra("time","09:00");
                    RecycleCalendar.this.startActivity(splashIntent);
                    RecycleCalendar.this.finish();
                    break;
                case R.id.time_10: dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    dateFormatter.format(DateInit);
                    splashIntent = new Intent(RecycleCalendar.this, DateTimePicker.class);
                    splashIntent.putExtra("seance", (Serializable)  new Seance(1,1,1,2,60,"",dateFormatter.format(DateInit)));
                    splashIntent.putExtra("time","10:00");
                    RecycleCalendar.this.startActivity(splashIntent);
                    RecycleCalendar.this.finish();
                    break;
                case R.id.time_11: dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    dateFormatter.format(DateInit);
                    splashIntent = new Intent(RecycleCalendar.this, DateTimePicker.class);
                    splashIntent.putExtra("seance", (Serializable)  new Seance(1,1,1,2,60,"",dateFormatter.format(DateInit)));
                    splashIntent.putExtra("time","11:00");
                    RecycleCalendar.this.startActivity(splashIntent);
                    RecycleCalendar.this.finish();
                    break;
                case R.id.time_12: dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    dateFormatter.format(DateInit);
                    splashIntent = new Intent(RecycleCalendar.this, DateTimePicker.class);
                    splashIntent.putExtra("seance", (Serializable)  new Seance(1,1,1,2,60,"",dateFormatter.format(DateInit)));
                    splashIntent.putExtra("time","12:00");
                    RecycleCalendar.this.startActivity(splashIntent);
                    RecycleCalendar.this.finish();
                    break;
                case R.id.time_13: dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    dateFormatter.format(DateInit);
                    splashIntent = new Intent(RecycleCalendar.this, DateTimePicker.class);
                    splashIntent.putExtra("seance", (Serializable)  new Seance(1,1,1,2,60,"",dateFormatter.format(DateInit)));
                    splashIntent.putExtra("time","13:00");
                    RecycleCalendar.this.startActivity(splashIntent);
                    RecycleCalendar.this.finish();
                    break;
                case R.id.time_14: dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    dateFormatter.format(DateInit);
                    splashIntent = new Intent(RecycleCalendar.this, DateTimePicker.class);
                    splashIntent.putExtra("seance", (Serializable)  new Seance(1,1,1,2,60,"",dateFormatter.format(DateInit)));
                    splashIntent.putExtra("time","14:00");
                    RecycleCalendar.this.startActivity(splashIntent);
                    RecycleCalendar.this.finish();
                    break;
                case R.id.time_15: dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    dateFormatter.format(DateInit);
                    splashIntent = new Intent(RecycleCalendar.this, DateTimePicker.class);
                    splashIntent.putExtra("seance", (Serializable)  new Seance(1,1,1,2,60,"",dateFormatter.format(DateInit)));
                    splashIntent.putExtra("time","15:00");
                    RecycleCalendar.this.startActivity(splashIntent);
                    RecycleCalendar.this.finish();
                    break;
                case R.id.time_16: dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    dateFormatter.format(DateInit);
                    splashIntent = new Intent(RecycleCalendar.this, DateTimePicker.class);
                    splashIntent.putExtra("seance", (Serializable)  new Seance(1,1,1,2,60,"",dateFormatter.format(DateInit)));
                    splashIntent.putExtra("time","16:00");
                    RecycleCalendar.this.startActivity(splashIntent);
                    RecycleCalendar.this.finish();
                    break;
                case R.id.time_17: dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    dateFormatter.format(DateInit);
                    splashIntent = new Intent(RecycleCalendar.this, DateTimePicker.class);
                    splashIntent.putExtra("seance", (Serializable)  new Seance(1,1,1,2,60,"",dateFormatter.format(DateInit)));
                    splashIntent.putExtra("time","17:00");
                    RecycleCalendar.this.startActivity(splashIntent);
                    RecycleCalendar.this.finish();
                    break;
                case R.id.time_18: dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    dateFormatter.format(DateInit);
                    splashIntent = new Intent(RecycleCalendar.this, DateTimePicker.class);
                    splashIntent.putExtra("seance", (Serializable)  new Seance(1,1,1,2,60,"",dateFormatter.format(DateInit)));
                    splashIntent.putExtra("time","18:00");
                    RecycleCalendar.this.startActivity(splashIntent);
                    RecycleCalendar.this.finish();
                    break;
            }
        }



    }


}