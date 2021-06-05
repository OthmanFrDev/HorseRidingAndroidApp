package com.example.horseriding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WeekView_Calendar extends AppCompatActivity {
    DatabaseHandler databaseHandler = new DatabaseHandler(WeekView_Calendar.this);
    String startDateString, endDateString;
    String id;

    String url;
    String color;
    List<Seance> seances;
    List<Seance> seanceList;
    LinearLayout.LayoutParams params;

    static boolean server = false;



    LinearLayout Lun_08, Lun_09, Lun_10, Lun_11, Lun_12, Lun_13, Lun_14, Lun_15, Lun_16, Lun_17, Lun_18,

    Mar_08, Mar_09, Mar_10, Mar_11, Mar_12, Mar_13, Mar_14, Mar_15, Mar_16, Mar_17, Mar_18,

            Mer_08, Mer_09, Mer_10, Mer_11, Mer_12, Mer_13, Mer_14, Mer_15, Mer_16, Mer_17, Mer_18,
            Jeu_08, Jeu_09, Jeu_10, Jeu_11, Jeu_12, Jeu_13, Jeu_14, Jeu_15, Jeu_16, Jeu_17, Jeu_18,
            Ven_08, Ven_09, Ven_10, Ven_11, Ven_12, Ven_13, Ven_14, Ven_15, Ven_16, Ven_17, Ven_18,
            Sam_08, Sam_09, Sam_10, Sam_11, Sam_12, Sam_13, Sam_14, Sam_15, Sam_16, Sam_17, Sam_18,
            Dim_08, Dim_09, Dim_10, Dim_11, Dim_12, Dim_13, Dim_14, Dim_15, Dim_16, Dim_17, Dim_18;
    LinearLayout linearIntersection;

    LocalDateTime dateInit;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    LocalDateTime starDate, endDate;

    BottomNavigationView bnv;
    private Dialog dialog;
    private Dialog dialoglist;
    private String urlTask;
    TextView v;
    private SeanceAdapterRecycle sa;
    private DialogInterface.OnClickListener listener;
    private GridLayout contentWeek;
    private Drawable drawable;

    private String fullName;

    TextView day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view_calendar);

        setTitle("Emploi de la semaine");
        day = findViewById(R.id.day);
        if (savedInstanceState != null) {
            dateInit = LocalDateTime.parse(savedInstanceState.getString("startDate"));
        } else {
            dateInit = LocalDateTime.of(2020, 9, 14, 15, 56);
        }
        starDate = dateInit;
        endDate = dateInit;

        Intent emploiIntent = getIntent();
        id = emploiIntent.getStringExtra("id");
        bnv = findViewById(R.id.bottom_navigation);
        dialog = new Dialog(WeekView_Calendar.this);
        dialoglist = new Dialog(WeekView_Calendar.this);
        seanceList = new ArrayList<Seance>();
        contentWeek = findViewById(R.id.gridweekdyalhadi);
        switch (dateInit.getDayOfWeek().getValue()) {
            case 1:
                starDate = dateInit;
                endDate = starDate.plusDays(6);
                break;
            case 2:
                starDate = dateInit.minusDays(1);
                endDate = starDate.plusDays(6);
                break;
            case 3:
                starDate = dateInit.minusDays(2);
                endDate = starDate.plusDays(6);
                break;
            case 4:
                starDate = dateInit.minusDays(3);
                endDate = starDate.plusDays(6);
                break;
            case 5:
                starDate = dateInit.minusDays(4);
                endDate = starDate.plusDays(6);
                break;
            case 6:
                starDate = dateInit.minusDays(5);
                endDate = starDate.plusDays(6);
                break;
            case 7:
                starDate = dateInit.minusDays(6);
                endDate = starDate.plusDays(6);
                break;
        }


        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);

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
                        i = new Intent(WeekView_Calendar.this, DashboardActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.setting_nav:
                        i = new Intent(WeekView_Calendar.this, EditFormUser.class);
                        WeekView_Calendar.this.startActivity(i);
                        break;
                    case R.id.logout_nav:
                        SessionManager sessionManager = new SessionManager(WeekView_Calendar.this);
                        sessionManager.logout();
                        Intent splashIntent = new Intent(WeekView_Calendar.this, LoginActivity.class);
                        WeekView_Calendar.this.startActivity(splashIntent);
                        WeekView_Calendar.this.finish();
                        break;
                }
                return true;
            }
        });
        startDateString = dateFormatter.format(starDate);
        endDateString = dateFormatter.format(endDate);

        day.setText(startDateString + " -> " + endDateString);

        Lun_08 = findViewById(R.id.Lun_08);
        Lun_09 = findViewById(R.id.Lun_09);
        Lun_10 = findViewById(R.id.Lun_10);
        Lun_11 = findViewById(R.id.Lun_11);
        Lun_12 = findViewById(R.id.Lun_12);
        Lun_13 = findViewById(R.id.Lun_13);
        Lun_14 = findViewById(R.id.Lun_14);
        Lun_15 = findViewById(R.id.Lun_15);
        Lun_16 = findViewById(R.id.Lun_16);
        Lun_17 = findViewById(R.id.Lun_17);
        Lun_18 = findViewById(R.id.Lun_18);
        Mar_08 = findViewById(R.id.Mar_08);
        Mar_09 = findViewById(R.id.Mar_09);
        Mar_10 = findViewById(R.id.Mar_10);
        Mar_11 = findViewById(R.id.Mar_11);
        Mar_12 = findViewById(R.id.Mar_12);
        Mar_13 = findViewById(R.id.Mar_13);
        Mar_14 = findViewById(R.id.Mar_14);
        Mar_15 = findViewById(R.id.Mar_15);
        Mar_16 = findViewById(R.id.Mar_16);
        Mar_17 = findViewById(R.id.Mar_17);
        Mar_18 = findViewById(R.id.Mar_18);
        Mer_08 = findViewById(R.id.Mer_08);
        Mer_09 = findViewById(R.id.Mer_09);
        Mer_10 = findViewById(R.id.Mer_10);
        Mer_11 = findViewById(R.id.Mer_11);
        Mer_12 = findViewById(R.id.Mer_12);
        Mer_13 = findViewById(R.id.Mer_13);
        Mer_14 = findViewById(R.id.Mer_14);
        Mer_15 = findViewById(R.id.Mer_15);
        Mer_16 = findViewById(R.id.Mer_16);
        Mer_17 = findViewById(R.id.Mer_17);
        Mer_18 = findViewById(R.id.Mer_18);
        Jeu_08 = findViewById(R.id.Jeu_08);
        Jeu_09 = findViewById(R.id.Jeu_09);
        Jeu_10 = findViewById(R.id.Jeu_10);
        Jeu_11 = findViewById(R.id.Jeu_11);
        Jeu_12 = findViewById(R.id.Jeu_12);
        Jeu_13 = findViewById(R.id.Jeu_13);
        Jeu_14 = findViewById(R.id.Jeu_14);
        Jeu_15 = findViewById(R.id.Jeu_15);
        Jeu_16 = findViewById(R.id.Jeu_16);
        Jeu_17 = findViewById(R.id.Jeu_17);
        Jeu_18 = findViewById(R.id.Jeu_18);
        Ven_08 = findViewById(R.id.Ven_08);
        Ven_09 = findViewById(R.id.Ven_09);
        Ven_10 = findViewById(R.id.Ven_10);
        Ven_11 = findViewById(R.id.Ven_11);
        Ven_12 = findViewById(R.id.Ven_12);
        Ven_13 = findViewById(R.id.Ven_13);
        Ven_14 = findViewById(R.id.Ven_14);
        Ven_15 = findViewById(R.id.Ven_15);
        Ven_16 = findViewById(R.id.Ven_16);
        Ven_17 = findViewById(R.id.Ven_17);
        Ven_18 = findViewById(R.id.Ven_18);
        Sam_08 = findViewById(R.id.Sam_08);
        Sam_09 = findViewById(R.id.Sam_09);
        Sam_10 = findViewById(R.id.Sam_10);
        Sam_11 = findViewById(R.id.Sam_11);
        Sam_12 = findViewById(R.id.Sam_12);
        Sam_13 = findViewById(R.id.Sam_13);
        Sam_14 = findViewById(R.id.Sam_14);
        Sam_15 = findViewById(R.id.Sam_15);
        Sam_16 = findViewById(R.id.Sam_16);
        Sam_17 = findViewById(R.id.Sam_17);
        Sam_18 = findViewById(R.id.Sam_18);
        Dim_08 = findViewById(R.id.Dim_08);
        Dim_09 = findViewById(R.id.Dim_09);
        Dim_10 = findViewById(R.id.Dim_10);
        Dim_11 = findViewById(R.id.Dim_11);
        Dim_12 = findViewById(R.id.Dim_12);
        Dim_13 = findViewById(R.id.Dim_13);
        Dim_14 = findViewById(R.id.Dim_14);
        Dim_15 = findViewById(R.id.Dim_15);
        Dim_16 = findViewById(R.id.Dim_16);
        Dim_17 = findViewById(R.id.Dim_17);
        Dim_18 = findViewById(R.id.Dim_18);
        drawable = getResources().getDrawable(R.drawable.ic_baseline_brightness_1_24, getTheme());


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mI = getMenuInflater();

        mI.inflate(R.menu.menu_calendar, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.month_view:
                calenderSwitcher(WeekView_Calendar.this, MonthView_Calendar.class);
                break;
            case R.id.week_view:/*item.setVisible(false);*/
                Toast.makeText(WeekView_Calendar.this, "already in week section", Toast.LENGTH_SHORT).show();
                break;
            case R.id.day_view:
                calenderSwitcher(WeekView_Calendar.this, DayView_calendar.class);

        }
        return true;
    }


    @Override
    protected void onResume() {

        super.onResume();
        SessionManager sessionManager = new SessionManager(WeekView_Calendar.this);
        emploitype();

        dialog.setContentView(R.layout.progressbar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        JsonArrayRequest jArray = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                dialog.cancel();


                getSeance(response);
                JSONObject j = null;

                for (int i = 0; i < response.length(); i++) {

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
                error.printStackTrace();
                Toast.makeText(WeekView_Calendar.this, "here error", Toast.LENGTH_SHORT).show();
                dialog.cancel();
                getSeance(seances);

            }
        });
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jArray);
    }

    private void emploitype() {
        if (getIntent().getStringExtra("emploitype") != null) {
            switch (getIntent().getStringExtra("emploitype")) {
                case "0":
                    url = WS.URL + "seances/" + startDateString + "/" + endDateString;
                    seances = databaseHandler.readSeance();
                    getTache();
                    break;
                case "1":
                    url = WS.URL + "seances/monitor/" + startDateString + "/" + endDateString + "/" + id;
                    getTache();
                    seances = databaseHandler.readUserSeance(Integer.valueOf(id));

                    break;
                case "2":
                    url = WS.URL + "seances/" + startDateString + "/" + endDateString + "/" + id;
                    seances = databaseHandler.readClientSeance(Integer.valueOf(id));
                    break;
            }
        } else {
            url = WS.URL + "seances/" + startDateString + "/" + endDateString; //getTache();
        }
    }


    public void weekchanger(View view) {

        SessionManager sessionManager = new SessionManager(WeekView_Calendar.this);
        init();
        server = false;
        LocalDateTime firstDate = starDate;

        switch (view.getId()) {

            case R.id.btnnext:
                starDate = starDate.plusDays(7);
                startDateString = dateFormatter.format(starDate);
                endDate = endDate.plusDays(7);
                endDateString = dateFormatter.format(endDate);
                break;
            case R.id.btnprevious:
                starDate = starDate.minusDays(7);
                startDateString = dateFormatter.format(starDate);
                endDate = endDate.minusDays(7);
                endDateString = dateFormatter.format(endDate);
                break;
        }
        Locale local = new Locale("fr", "Fr");


        //DateTimeFormatter dt= ;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //day.setText(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT).withLocale(local).format(DateInit));
        day.setText(startDateString + " -> " + endDateString);

        onResume();

//            JsonArrayRequest jArray=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//
//
//            @Override
//            public void onResponse(JSONArray response) {
//
//                sessionManager.server(true);
//
//                getSeance(response  );
//
//
//                }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Log.w("ONERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOOOR"," "+error);
//            }
//        });

        //MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jArray);

//       if(!sessionManager.getserver()&&firstDate.equals(starDate))
//      {
//          getSeance(databaseHandler.readSeance());}


    }


    void init() {

        LinearLayout l = null;
        for (int i = 1; i < contentWeek.getChildCount(); i++) {
            GridLayout G = (GridLayout) contentWeek.getChildAt(i);
            for (int j = 1; j < G.getChildCount(); j++) {
                if (G.getChildAt(j) instanceof LinearLayout) {
                    l = (LinearLayout) G.getChildAt(j);
                    l.removeAllViews();
                }
            }

        }
    }


    private void calenderSwitcher(Context context, Class<?> CLASS) {
        Intent i = null;
        i = new Intent(context, CLASS);
        if (getIntent().getStringExtra("emploitype") != null) {
            switch (getIntent().getStringExtra("emploitype")) {
                case "0":
                    startActivity(i);
                    finish();
                    break;

                case "1":
                    i.putExtra("emploitype", "1");
                    i.putExtra("id", id);
                    startActivity(i);
                    finish();
                    break;

                case "2":
                    i.putExtra("emploitype", "2");
                    i.putExtra("id", id);
                    startActivity(i);
                    finish();
                    break;

            }
        } else {
            startActivity(i);
            finish();
        }
    }

    void getSeance(JSONArray response) {
        LocalDateTime dateFromResponse;
        JSONObject j = null;
        for (int i = 0; i < response.length(); i++) {
            try {
                v = new TextView(WeekView_Calendar.this);
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(WeekView_Calendar.this, R.drawable.textview_border);
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                v.setLayoutParams(params);
                v.setTextSize(11);
                v.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                j = response.getJSONObject(i);
                dateFromResponse = LocalDateTime.parse(j.getString("startDate"));
                String dateTime = j.getString("startDate").substring(11, 16);

                if (LocalDateTime.now().compareTo(dateFromResponse) <= 0) {
                    color = "#2de04b";
                } else {
                    color = "#CC0000";
                }
                v.setText(String.valueOf(j.getInt("seanceId")));
                v.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                if (LocalDateTime.now().compareTo(LocalDateTime.parse(j.getString("startDate"))) <= 0) {
                    DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#048FD2"));
                    v.setBackgroundResource(R.drawable.textview_border);

                } else {
                    DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#ff6347"));
                    v.setBackgroundResource(R.drawable.textview_border);

                }
                switch (dateFromResponse.getDayOfWeek().getValue()) {

                    case 1:
                        switch (dateTime) {

                            case "08:00":
                                Lun_08.addView(v);
                                break;
                            case "09:00":
                                Lun_09.addView(v);
                                break;
                            case "10:00":
                                Lun_10.addView(v);
                                break;
                            case "11:00":
                                Lun_11.addView(v);
                                break;
                            case "12:00":
                                Lun_12.addView(v);
                                break;
                            case "13:00":
                                Lun_13.addView(v);
                                break;
                            case "14:00":
                                Lun_14.addView(v);
                                break;
                            case "15:00":
                                Lun_15.addView(v);
                                break;
                            case "16:00":
                                Lun_16.addView(v);
                                break;
                            case "17:00":
                                Lun_17.addView(v);
                                break;
                            case "18:00":
                                Lun_18.addView(v);

                                break;
                        }
                        break;

                    case 2:
                        switch (dateTime) {
                            case "08:00":
                                Mar_08.addView(v);
                                break;
                            case "09:00":
                                Mar_09.addView(v);
                                break;
                            case "10:00":
                                Mar_10.addView(v);
                                break;
                            case "11:00":
                                Mar_11.addView(v);
                                break;
                            case "12:00":
                                Mar_12.addView(v);
                                break;
                            case "13:00":
                                Mar_13.addView(v);
                                break;
                            case "14:00":
                                Mar_14.addView(v);
                                break;
                            case "15:00":
                                Mar_15.addView(v);
                                break;
                            case "16:00":
                                Mar_16.addView(v);
                                break;
                            case "17:00":
                                Mar_17.addView(v);
                                break;
                            case "18:00":
                                Mar_18.addView(v);
                                break;
                        }
                        break;

                    case 3:
                        switch (dateTime) {
                            case "08:00":
                                Mer_08.addView(v);
                                break;
                            case "09:00":
                                Mer_09.addView(v);
                                break;
                            case "10:00":
                                Mer_10.addView(v);
                                break;
                            case "11:00":
                                Mer_11.addView(v);
                                break;
                            case "12:00":
                                Mer_12.addView(v);
                                break;
                            case "13:00":
                                Mer_13.addView(v);
                                break;
                            case "14:00":
                                Mer_14.addView(v);
                                break;
                            case "15:00":
                                Mer_15.addView(v);
                                break;
                            case "16:00":
                                Mer_16.addView(v);
                                break;
                            case "17:00":
                                Mer_17.addView(v);
                                break;
                            case "18:00":
                                Mer_18.addView(v);
                                break;
                        }
                        break;

                    case 4:
                        switch (dateTime) {
                            case "08:00":
                                Jeu_08.addView(v);
                                break;
                            case "09:00":
                                Jeu_09.addView(v);
                                break;
                            case "10:00":
                                Jeu_10.addView(v);
                                break;
                            case "11:00":
                                Jeu_11.addView(v);
                                break;
                            case "12:00":
                                Jeu_12.addView(v);
                                break;
                            case "13:00":
                                Jeu_13.addView(v);
                                break;
                            case "14:00":
                                Jeu_14.addView(v);
                                break;
                            case "15:00":
                                Jeu_15.addView(v);
                                break;
                            case "16:00":
                                Jeu_16.addView(v);
                                break;
                            case "17:00":
                                Jeu_17.addView(v);
                                break;
                            case "18:00":
                                Jeu_18.addView(v);
                                break;
                        }
                        break;
                    case 5:
                        switch (dateTime) {
                            case "08:00":
                                Ven_08.addView(v);
                                break;
                            case "09:00":
                                Ven_09.addView(v);
                                break;
                            case "10:00":
                                Ven_10.addView(v);
                                break;
                            case "11:00":
                                Ven_11.addView(v);
                                break;
                            case "12:00":
                                Ven_12.addView(v);
                                break;
                            case "13:00":
                                Ven_13.addView(v);
                                break;
                            case "14:00":
                                Ven_14.addView(v);
                                break;
                            case "15:00":
                                Ven_15.addView(v);
                                break;
                            case "16:00":
                                Ven_16.addView(v);
                                break;
                            case "17:00":
                                Ven_17.addView(v);
                                break;
                            case "18:00":
                                Ven_18.addView(v);
                                break;
                        }
                        break;
                    case 6:
                        switch (dateTime) {
                            case "08:00":
                                Sam_08.addView(v);
                                break;
                            case "09:00":
                                Sam_09.addView(v);
                                break;
                            case "10:00":
                                Sam_10.addView(v);
                                break;
                            case "11:00":
                                Sam_11.addView(v);
                                break;
                            case "12:00":
                                Sam_12.addView(v);
                                break;
                            case "13:00":
                                Sam_13.addView(v);
                                break;
                            case "14:00":
                                Sam_14.addView(v);
                                break;
                            case "15:00":
                                Sam_15.addView(v);
                                break;
                            case "16:00":
                                Sam_16.addView(v);
                                break;
                            case "17:00":
                                Sam_17.addView(v);
                                break;
                            case "18:00":
                                Sam_18.addView(v);
                                break;
                        }
                        break;
                    case 7:
                        switch (dateTime) {
                            case "08:00":
                                Dim_08.addView(v);
                                break;
                            case "09:00":
                                Dim_09.addView(v);
                                break;
                            case "10:00":
                                Dim_10.addView(v);
                                break;
                            case "11:00":
                                Dim_11.addView(v);
                                break;
                            case "12:00":
                                Dim_12.addView(v);
                                break;
                            case "13:00":
                                Dim_13.addView(v);
                                break;
                            case "14:00":
                                Dim_14.addView(v);
                                break;
                            case "15:00":
                                Dim_15.addView(v);
                                break;
                            case "16:00":
                                Dim_16.addView(v);
                                break;
                            case "17:00":
                                Dim_17.addView(v);
                                break;
                            case "18:00":
                                Dim_18.addView(v);
                                break;
                        }
                        break;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    void getTache() {
        if (getIntent().getStringExtra("emploitype") != null) {
            switch (getIntent().getStringExtra("emploitype")) {
                case "0":
                    urlTask = WS.URL + "tasks/" + startDateString + "/" + endDateString;

                    break;
                case "1":
                    urlTask = WS.URL + "tasks/" + startDateString + "/" + endDateString + "/" + id;

                    break;
            }
        } else {
            url = WS.URL + "tasks/" + startDateString + "/" + endDateString;
        }
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(WeekView_Calendar.this, R.drawable.textview_border);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        JsonArrayRequest jArray = new JsonArrayRequest(Request.Method.GET, urlTask, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                TextView day = findViewById(R.id.day);

                day.setText(startDateString + " -> " + endDateString);

                LocalDateTime dateFromResponse;
                JSONObject j = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        j = response.getJSONObject(i);
                        dateFromResponse = LocalDateTime.parse(j.getString("startDate"));
                        String dateTime = j.getString("startDate").substring(11, 16);
                        if (LocalDateTime.now().compareTo(dateFromResponse) <= 0) {
                            color = "#00DAC5";
                        } else {
                            color = "#ff6347";
                        }

                        v = new TextView(WeekView_Calendar.this);
                        v.setLayoutParams(params);
                        v.setTextSize(11);
                        v.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                        if (LocalDateTime.now().compareTo(LocalDateTime.parse(j.getString("startDate"))) <= 0) {
                            DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#048FD2"));
                            v.setBackgroundResource(R.drawable.textview_border);

                        } else {
                            DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#ff6347"));
                            v.setBackgroundResource(R.drawable.textview_border);

                        }
                        v.setText(j.getInt("taskId") + "task");
                        switch (dateFromResponse.getDayOfWeek().getValue()) {


                            case 1:
                                switch (dateTime) {

                                    case "08:00":
                                        Lun_08.addView(v);
                                        break;
                                    case "09:00":
                                        Lun_09.addView(v);
                                        break;
                                    case "10:00":
                                        Lun_10.addView(v);
                                        break;
                                    case "11:00":
                                        Lun_11.addView(v);
                                        break;
                                    case "12:00":
                                        Lun_12.addView(v);
                                        break;
                                    case "13:00":
                                        Lun_13.addView(v);
                                        break;
                                    case "14:00":
                                        Lun_14.addView(v);
                                        break;
                                    case "15:00":
                                        Lun_15.addView(v);
                                        break;
                                    case "16:00":
                                        Lun_16.addView(v);
                                        break;
                                    case "17:00":
                                        Lun_17.addView(v);
                                        break;
                                    case "18:00":
                                        Lun_18.addView(v);

                                        break;
                                }
                                break;

                            case 2:
                                switch (dateTime) {
                                    case "08:00":
                                        Mar_08.addView(v);
                                        break;
                                    case "09:00":
                                        Mar_09.addView(v);
                                        break;
                                    case "10:00":
                                        Mar_10.addView(v);
                                        break;
                                    case "11:00":
                                        Mar_11.addView(v);
                                        break;
                                    case "12:00":
                                        Mar_12.addView(v);
                                        break;
                                    case "13:00":
                                        Mar_13.addView(v);
                                        break;
                                    case "14:00":
                                        Mar_14.addView(v);
                                        break;
                                    case "15:00":
                                        Mar_15.addView(v);
                                        break;
                                    case "16:00":
                                        Mar_16.addView(v);
                                        break;
                                    case "17:00":
                                        Mar_17.addView(v);
                                        break;
                                    case "18:00":
                                        Mar_18.addView(v);
                                        break;
                                }
                                break;

                            case 3:
                                switch (dateTime) {
                                    case "08:00":
                                        Mer_08.addView(v);
                                        break;
                                    case "09:00":
                                        Mer_09.addView(v);
                                        break;
                                    case "10:00":
                                        Mer_10.addView(v);
                                        break;
                                    case "11:00":
                                        Mer_11.addView(v);
                                        break;
                                    case "12:00":
                                        Mer_12.addView(v);
                                        break;
                                    case "13:00":
                                        Mer_13.addView(v);
                                        break;
                                    case "14:00":
                                        Mer_14.addView(v);
                                        break;
                                    case "15:00":
                                        Mer_15.addView(v);
                                        break;
                                    case "16:00":
                                        Mer_16.addView(v);
                                        break;
                                    case "17:00":
                                        Mer_17.addView(v);
                                        break;
                                    case "18:00":
                                        Mer_18.addView(v);
                                        break;
                                }
                                break;

                            case 4:
                                switch (dateTime) {
                                    case "08:00":
                                        Jeu_08.addView(v);
                                        break;
                                    case "09:00":
                                        Jeu_09.addView(v);
                                        break;
                                    case "10:00":
                                        Jeu_10.addView(v);
                                        break;
                                    case "11:00":
                                        Jeu_11.addView(v);
                                        break;
                                    case "12:00":
                                        Jeu_12.addView(v);
                                        break;
                                    case "13:00":
                                        Jeu_13.addView(v);
                                        break;
                                    case "14:00":
                                        Jeu_14.addView(v);
                                        break;
                                    case "15:00":
                                        Jeu_15.addView(v);
                                        break;
                                    case "16:00":
                                        Jeu_16.addView(v);
                                        break;
                                    case "17:00":
                                        Jeu_17.addView(v);
                                        break;
                                    case "18:00":
                                        Jeu_18.addView(v);
                                        break;
                                }
                                break;

                            case 5:
                                switch (dateTime) {
                                    case "08:00":
                                        Ven_08.addView(v);
                                        break;
                                    case "09:00":
                                        Ven_09.addView(v);
                                        break;
                                    case "10:00":
                                        Ven_10.addView(v);
                                        break;
                                    case "11:00":
                                        Ven_11.addView(v);
                                        break;
                                    case "12:00":
                                        Ven_12.addView(v);
                                        break;
                                    case "13:00":
                                        Ven_13.addView(v);
                                        break;
                                    case "14:00":
                                        Ven_14.addView(v);
                                        break;
                                    case "15:00":
                                        Ven_15.addView(v);
                                        break;
                                    case "16:00":
                                        Ven_16.addView(v);
                                        break;
                                    case "17:00":
                                        Ven_17.addView(v);
                                        break;
                                    case "18:00":
                                        Ven_18.addView(v);
                                        break;
                                }
                                break;

                            case 6:
                                switch (dateTime) {
                                    case "08:00":
                                        Sam_08.addView(v);
                                        break;
                                    case "09:00":
                                        Sam_09.addView(v);
                                        break;
                                    case "10:00":
                                        Sam_10.addView(v);
                                        break;
                                    case "11:00":
                                        Sam_11.addView(v);
                                        break;
                                    case "12:00":
                                        Sam_12.addView(v);
                                        break;
                                    case "13:00":
                                        Sam_13.addView(v);
                                        break;
                                    case "14:00":
                                        Sam_14.addView(v);
                                        break;
                                    case "15:00":
                                        Sam_15.addView(v);
                                        break;
                                    case "16:00":
                                        Sam_16.addView(v);
                                        break;
                                    case "17:00":
                                        Sam_17.addView(v);
                                        break;
                                    case "18:00":
                                        Sam_18.addView(v);
                                        break;
                                }
                                break;

                            case 7:
                                switch (dateTime) {
                                    case "08:00":
                                        Dim_08.addView(v);
                                        break;
                                    case "09:00":
                                        Dim_09.addView(v);
                                        break;
                                    case "10:00":
                                        Dim_10.addView(v);
                                        break;
                                    case "11:00":
                                        Dim_11.addView(v);
                                        break;
                                    case "12:00":
                                        Dim_12.addView(v);
                                        break;
                                    case "13:00":
                                        Dim_13.addView(v);
                                        break;
                                    case "14:00":
                                        Dim_14.addView(v);
                                        break;
                                    case "15:00":
                                        Dim_15.addView(v);
                                        break;
                                    case "16:00":
                                        Dim_16.addView(v);
                                        break;
                                    case "17:00":
                                        Dim_17.addView(v);
                                        break;
                                    case "18:00":
                                        Dim_18.addView(v);
                                        break;
                                }
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        j = response.getJSONObject(i);
                        databaseHandler.saveTask(new Task(j.getInt("taskId"), j.getString("startDate"), j.getInt("durationMinut"), j.getString("title"), j.getString("detail"), j.getString("isDone"), j.getInt("userFk")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("gettache", error + " ");
            }
        });
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jArray);
    }


    void getSeance(List<Seance> seances) {

        LocalDateTime dateFromResponse;
        for (Seance seance : seances) {
            try {

                dateFromResponse = LocalDateTime.parse(seance.getStartDate());
                String dateTime = seance.getStartDate().substring(11, 16);
                v.setText(seance.getSeanceId() + "" + "" + "");
                switch (dateFromResponse.getDayOfWeek().getValue()) {
                    case 1:
                        switch (dateTime) {
                            case "08:00":
                                Lun_08.addView(v);
                                Lun_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "09:00":
                                Lun_09.addView(v);
                                Lun_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "10:00":
                                Lun_10.addView(v);
                                Lun_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "11:00":
                                Lun_11.addView(v);
                                Lun_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "12:00":
                                Lun_12.addView(v);
                                Lun_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "13:00":
                                Lun_13.addView(v);
                                Lun_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "14:00":
                                Lun_14.addView(v);
                                Lun_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "15:00":
                                Lun_15.addView(v);
                                Lun_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "16:00":
                                Lun_16.addView(v);
                                Lun_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "17:00":
                                Lun_17.addView(v);
                                Lun_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "18:00":
                                Lun_18.addView(v);
                                Lun_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                        }
                        break;

                    case 2:
                        switch (dateTime) {
                            case "08:00":
                                Mar_08.addView(v);
                                Mar_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "09:00":
                                Mar_09.addView(v);
                                Mar_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "10:00":
                                Mar_10.addView(v);
                                Mar_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "11:00":
                                Mar_11.addView(v);
                                Mar_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "12:00":
                                Mar_12.addView(v);
                                Mar_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "13:00":
                                Mar_13.addView(v);
                                Mar_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "14:00":
                                Mar_14.addView(v);
                                Mar_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "15:00":
                                Mar_15.addView(v);
                                Mar_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "16:00":
                                Mar_16.addView(v);
                                Mar_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "17:00":
                                Mar_17.addView(v);
                                Mar_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "18:00":
                                Mar_18.addView(v);
                                Mar_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                        }
                        break;

                    case 3:
                        switch (dateTime) {
                            case "08:00":
                                Mer_08.addView(v);
                                Mer_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "09:00":
                                Mer_09.addView(v);
                                Mer_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "10:00":
                                Mer_10.addView(v);
                                Mer_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "11:00":
                                Mer_11.addView(v);
                                Mer_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "12:00":
                                Mer_12.addView(v);
                                Mer_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "13:00":
                                Mer_13.addView(v);
                                Mer_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "14:00":
                                Mer_14.addView(v);
                                Mer_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "15:00":
                                Mer_15.addView(v);
                                Mer_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "16:00":
                                Mer_16.addView(v);
                                Mer_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "17:00":
                                Mer_17.addView(v);
                                Mer_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "18:00":
                                Mer_18.addView(v);
                                Mer_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                        }
                        break;

                    case 4:
                        switch (dateTime) {
                            case "08:00":
                                Jeu_08.addView(v);
                                Jeu_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "09:00":
                                Jeu_09.addView(v);
                                Jeu_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "10:00":
                                Jeu_10.addView(v);
                                Jeu_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "11:00":
                                Jeu_11.addView(v);
                                Jeu_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "12:00":
                                Jeu_12.addView(v);
                                Jeu_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "13:00":
                                Jeu_13.addView(v);
                                Jeu_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "14:00":
                                Jeu_14.addView(v);
                                Jeu_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "15:00":
                                Jeu_15.addView(v);
                                Jeu_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "16:00":
                                Jeu_16.addView(v);
                                Jeu_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "17:00":
                                Jeu_17.addView(v);
                                Jeu_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "18:00":
                                Jeu_18.addView(v);
                                Jeu_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                        }

                        break;

                    case 5:
                        switch (dateTime) {
                            case "08:00":
                                Ven_08.addView(v);
                                Ven_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "09:00":
                                Ven_09.addView(v);
                                Ven_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "10:00":
                                Ven_10.addView(v);
                                Ven_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "11:00":
                                Ven_11.addView(v);
                                Ven_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "12:00":
                                Ven_12.addView(v);
                                Ven_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "13:00":
                                Ven_13.addView(v);
                                Ven_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "14:00":
                                Ven_14.addView(v);
                                Ven_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "15:00":
                                Ven_15.addView(v);
                                Ven_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "16:00":
                                Ven_16.addView(v);
                                Ven_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "17:00":
                                Ven_17.addView(v);
                                Ven_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "18:00":
                                Ven_18.addView(v);
                                Ven_18.setBackgroundColor(Color.parseColor("#EC7C32"));

                                break;
                        }
                        break;

                    case 6:
                        switch (dateTime) {
                            case "08:00":
                                Sam_08.addView(v);
                                Sam_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "09:00":
                                Sam_09.addView(v);
                                Sam_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "10:00":
                                Sam_10.addView(v);
                                Sam_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "11:00":
                                Sam_11.addView(v);
                                Sam_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "12:00":
                                Sam_12.addView(v);
                                Sam_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "13:00":
                                Sam_13.addView(v);
                                Sam_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "14:00":
                                Sam_14.addView(v);
                                Sam_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "15:00":
                                Sam_15.addView(v);
                                Sam_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "16:00":
                                Sam_16.addView(v);
                                Sam_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "17:00":
                                Sam_17.addView(v);
                                Sam_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "18:00":
                                Sam_18.addView(v);
                                Sam_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                        }
                        break;

                    case 7:
                        switch (dateTime) {
                            case "08:00":
                                Dim_08.addView(v);
                                Dim_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "09:00":
                                Dim_09.addView(v);
                                Dim_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "10:00":
                                Dim_10.addView(v);
                                Dim_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "11:00":
                                Dim_11.addView(v);
                                Dim_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "12:00":
                                Dim_12.addView(v);
                                Dim_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "13:00":
                                Dim_13.addView(v);
                                Dim_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "14:00":
                                Dim_14.addView(v);
                                Dim_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "15:00":
                                Dim_15.addView(v);
                                Dim_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "16:00":
                                Dim_16.addView(v);
                                Dim_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "17:00":
                                Dim_17.addView(v);
                                Dim_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                                break;
                            case "18:00":
                                Dim_18.addView(v);
                                Dim_18.setBackgroundColor(Color.parseColor("#EC7C32"));
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
        StringBuilder stringBuilder = new StringBuilder();
        TextView textView;
        LinearLayout linearLayout = findViewById(view.getId());

        if (linearLayout.getChildCount() > 0) {

            textView = (TextView) linearLayout.getChildAt(0);

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, WS.URL + "seances/" + textView.getText().toString().trim(), null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    try {

                        String URLEXTENSION = urlExtensionforSeanceDetails(response);

                           /* Seance seance= new Seance(response.getInt("seanceId"),response.getInt("seanceGrpId"),
                                    response.getInt("clientId"),response.getInt("monitorId"),
                                    response.getInt("durationMinut"),response.getString("comments"),response.getString("startDate"));
                         //  Intent i= new Intent(WeekView_Calendar.this,ListActivity.class); i.putExtra("seance", (Serializable) seance); startActivity(i);finish();*/


                        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, WS.URL + URLEXTENSION, null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {


                                stringBuilder.append("seance : \n");
                                JSONObject j = null;
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        j = response.getJSONObject(i);


//                            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,  "http://192.168.111.1:45455/users/by/"+ j.getString("monitorId"), null, new Response.Listener<JSONObject>() {
//                                @Override
//                                public void onResponse(JSONObject resp) {
//
//                                    try {
//                                        JSONObject jo=response.getJSONObject();
//
//                                        fullName =  resp.getString("userFname")+ " "+ resp.getString("userLname");
//
//                                        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,  "http://192.168.111.1:45455/users/by/"+ j.getString("monitorId"), null, new Response.Listener<JSONObject>() {
//                                            @Override
//                                            public void onResponse(JSONObject resp) {
//
//                                                try {
//
//                                                    fullName =  resp.getString("userFname")+ " "+ resp.getString("userLname");
//
//
//
//                                                } catch (JSONException e) {
//                                                    e.printStackTrace();
//                                                }
//                                            }
//                                        },
//                                                new Response.ErrorListener() {
//                                                    @Override
//                                                    public void onErrorResponse(VolleyError error) {
//                                                        Log.e(DayView_calendar.class.getSimpleName(), error.getMessage());
//                                                    }
//                                                }
//
//                                        );
//                                        MySingleton.getInstance(WeekView_Calendar.this).addToRequestQueue(req);
//
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            },
//                                    new Response.ErrorListener() {
//                                        @Override
//                                        public void onErrorResponse(VolleyError error) {
//                                            Log.e(DayView_calendar.class.getSimpleName(), error.getMessage());
//                                        }
//                                    }
//
//                            );
//                            MySingleton.getInstance(WeekView_Calendar.this).addToRequestQueue(req);
                                        stringBuilder.append("ID : " + j.getInt("seanceId") + " Monitor : " + j.getString("monitorId") + " Client : " + j.getString("clientId") + ".\n\n");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }



                                }


                                AlertDialog.Builder builder = new AlertDialog.Builder(WeekView_Calendar.this);
                                builder.setTitle("details");
                                builder.setMessage(stringBuilder);

                                AlertDialog dialog = builder.create();
                                dialog.show();


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }

                        );


                        MySingleton.getInstance(WeekView_Calendar.this).addToRequestQueue(req);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            getSeance(databaseHandler.readSeance());

                        }
                    }

            );

            MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);


//
            //           AlertDialog.Builder builder = new AlertDialog.Builder(WeekView_Calendar.this);
            //           builder.setTitle("Seance Detail:");
//            builder.setMessage("ID = "+ response.getInt("seanceId")+"\n\n" +"Sart Date = "+ response.getString("startDate")+"\n\n"+
//                    "Duration = "+response.getInt("durationMinut")+"\n\n"+
//                    "Comment = "+ response.getString("comments")
//            );
            //   builder.setAdapter((ListAdapter) sa,listener);
            //   builder.setNegativeButton("Close", null);
//            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which)
//                {
//                    try {
//
//                         LocalDateTime date=LocalDateTime.parse(response.getString("startDate"));
//                         String dateDay=response.getString("startDate").substring(0,10);
//                         String dateTime=response.getString("startDate").substring(11,16);
//
//                        LocalDateTime  DateInit=LocalDateTime.of(LocalDate.parse(dateDay), LocalTime.parse(dateTime));
//                        StringRequest dr = new StringRequest(Request.Method.DELETE, WS.URL +"seances/"+response.getInt("seanceId"),
//                                new Response.Listener<String>()
//                                {
//                                    @Override
//                                    public void onResponse(String res) {
//                                        // response
//
//                                        startActivity(getIntent());
//                                        Toast.makeText(WeekView_Calendar.this, "Deleted", Toast.LENGTH_LONG).show();
//                                    }
//                                },
//                                new Response.ErrorListener()
//                                {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        // error.
//
//                                    }
//                                }
//                        ); MySingleton.getInstance(WeekView_Calendar.this).addToRequestQueue(dr);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }});


            //   AlertDialog dialog = builder.create();
            //  dialog.show();

        } else {
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
//                case R.id.Lun_10:Lun_10.addView(v);Lun_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_11:Lun_11.addView(v);Lun_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_12:Lun_12.addView(v);Lun_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_13:Lun_13.addView(v);Lun_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_14:Lun_14.addView(v);Lun_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_15:Lun_15.addView(v);Lun_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_16:Lun_16.addView(v);Lun_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_17:Lun_17.addView(v);Lun_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_18:Lun_18.addView(v);Lun_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Mar_08:Mar_08.addView(v);Mar_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_09:Mar_09.addView(v);Mar_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_10:Mar_10.addView(v);Mar_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case "1Mar_11:Mar_11.addView(v);Mar_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_12:Mar_12.addView(v);Mar_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_13:Mar_13.addView(v);Mar_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_14:Mar_14.addView(v);Mar_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_15:Mar_15.addView(v);Mar_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_16:Mar_16.addView(v);Mar_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_17:Mar_17.addView(v);Mar_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_18:Mar_18.addView(v);Mar_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Mer_08:Mer_08.addView(v);Mer_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_09:Mer_09.addView(v);Mer_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_10:Mer_10.addView(v);Mer_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_11:Mer_11.addView(v);Mer_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_12:Mer_12.addView(v);Mer_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_13:Mer_13.addView(v);Mer_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_14:Mer_14.addView(v);Mer_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_15:Mer_15.addView(v);Mer_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_16:Mer_16.addView(v);Mer_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_17:Mer_17.addView(v);Mer_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_18:Mer_18.addView(v);Mer_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Jeu_08:Jeu_08.addView(v);Jeu_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_09:Jeu_09.addView(v);Jeu_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_10:Jeu_10.addView(v);Jeu_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_11:Jeu_11.addView(v);Jeu_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_12:Jeu_12.addView(v);Jeu_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_13:Jeu_13.addView(v);Jeu_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_14:Jeu_14.addView(v);Jeu_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_15:Jeu_15.addView(v);Jeu_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_16:Jeu_16.addView(v);Jeu_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_17:Jeu_17.addView(v);Jeu_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_18:Jeu_18.addView(v);Jeu_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Jeu_08:Jeu_08.addView(v);Jeu_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_09:Jeu_09.addView(v);Jeu_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_10:Jeu_10.addView(v);Jeu_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_11:Jeu_11.addView(v);Jeu_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_12:Jeu_12.addView(v);Jeu_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_13:Jeu_13.addView(v);Jeu_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_14:Jeu_14.addView(v);Jeu_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_15:Jeu_15.addView(v);Jeu_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_16:Jeu_16.addView(v);Jeu_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_17:Jeu_17.addView(v);Jeu_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_18:Jeu_18.addView(v);Jeu_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Ven_08:Ven_08.addView(v);Ven_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_09:Ven_09.addView(v);Ven_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_10:Ven_10.addView(v);Ven_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_11:Ven_11.addView(v);Ven_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_12:Ven_12.addView(v);Ven_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_13:Ven_13.addView(v);Ven_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_14:Ven_14.addView(v);Ven_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_15:Ven_15.addView(v);Ven_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_16:Ven_16.addView(v);Ven_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_17:Ven_17.addView(v);Ven_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_18:Ven_18.addView(v);Ven_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Sam_08:Sam_08.addView(v);Sam_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_09:Sam_09.addView(v);Sam_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_10:Sam_10.addView(v);Sam_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_11:Sam_11.addView(v);Sam_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_12:Sam_12.addView(v);Sam_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_13:Sam_13.addView(v);Sam_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_14:Sam_14.addView(v);Sam_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_15:Sam_15.addView(v);Sam_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_16:Sam_16.addView(v);Sam_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_17:Sam_17.addView(v);Sam_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_18:Sam_18.addView(v);Sam_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Dim_08:Dim_08.addView(v);Dim_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_09:Dim_09.addView(v);Dim_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_10:Dim_10.addView(v);Dim_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_11:Dim_11.addView(v);Dim_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_12:Dim_12.addView(v);Dim_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_13:Dim_13.addView(v);Dim_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_14:Dim_14.addView(v);Dim_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_15:Dim_15.addView(v);Dim_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_16:Dim_16.addView(v);Dim_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_17:Dim_17.addView(v);Dim_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_18:Dim_18.addView(v);Dim_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Lun_08:Lun_08.addView(v);Lun_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_09:Lun_09.addView(v);Lun_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_10:Lun_10.addView(v);Lun_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_11:Lun_11.addView(v);Lun_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_12:Lun_12.addView(v);Lun_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_13:Lun_13.addView(v);Lun_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_14:Lun_14.addView(v);Lun_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_15:Lun_15.addView(v);Lun_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_16:Lun_16.addView(v);Lun_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_17:Lun_17.addView(v);Lun_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Lun_18:Lun_18.addView(v);Lun_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Mar_08:Mar_08.addView(v);Mar_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_09:Mar_09.addView(v);Mar_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_10:Mar_10.addView(v);Mar_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case "1Mar_11:Mar_11.addView(v);Mar_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_12:Mar_12.addView(v);Mar_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_13:Mar_13.addView(v);Mar_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_14:Mar_14.addView(v);Mar_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_15:Mar_15.addView(v);Mar_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_16:Mar_16.addView(v);Mar_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_17:Mar_17.addView(v);Mar_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mar_18:Mar_18.addView(v);Mar_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Mer_08:Mer_08.addView(v);Mer_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_09:Mer_09.addView(v);Mer_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_10:Mer_10.addView(v);Mer_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_11:Mer_11.addView(v);Mer_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_12:Mer_12.addView(v);Mer_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_13:Mer_13.addView(v);Mer_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_14:Mer_14.addView(v);Mer_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_15:Mer_15.addView(v);Mer_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_16:Mer_16.addView(v);Mer_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_17:Mer_17.addView(v);Mer_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Mer_18:Mer_18.addView(v);Mer_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Jeu_08:Jeu_08.addView(v);Jeu_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_09:Jeu_09.addView(v);Jeu_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_10:Jeu_10.addView(v);Jeu_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_11:Jeu_11.addView(v);Jeu_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_12:Jeu_12.addView(v);Jeu_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_13:Jeu_13.addView(v);Jeu_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_14:Jeu_14.addView(v);Jeu_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_15:Jeu_15.addView(v);Jeu_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_16:Jeu_16.addView(v);Jeu_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_17:Jeu_17.addView(v);Jeu_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_18:Jeu_18.addView(v);Jeu_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Jeu_08:Jeu_08.addView(v);Jeu_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_09:Jeu_09.addView(v);Jeu_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_10:Jeu_10.addView(v);Jeu_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_11:Jeu_11.addView(v);Jeu_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_12:Jeu_12.addView(v);Jeu_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_13:Jeu_13.addView(v);Jeu_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_14:Jeu_14.addView(v);Jeu_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_15:Jeu_15.addView(v);Jeu_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_16:Jeu_16.addView(v);Jeu_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_17:Jeu_17.addView(v);Jeu_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Jeu_18:Jeu_18.addView(v);Jeu_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Ven_08:Ven_08.addView(v);Ven_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_09:Ven_09.addView(v);Ven_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_10:Ven_10.addView(v);Ven_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_11:Ven_11.addView(v);Ven_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_12:Ven_12.addView(v);Ven_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_13:Ven_13.addView(v);Ven_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_14:Ven_14.addView(v);Ven_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_15:Ven_15.addView(v);Ven_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_16:Ven_16.addView(v);Ven_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_17:Ven_17.addView(v);Ven_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Ven_18:Ven_18.addView(v);Ven_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Sam_08:Sam_08.addView(v);Sam_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_09:Sam_09.addView(v);Sam_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_10:Sam_10.addView(v);Sam_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_11:Sam_11.addView(v);Sam_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_12:Sam_12.addView(v);Sam_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_13:Sam_13.addView(v);Sam_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_14:Sam_14.addView(v);Sam_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_15:Sam_15.addView(v);Sam_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_16:Sam_16.addView(v);Sam_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_17:Sam_17.addView(v);Sam_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Sam_18:Sam_18.addView(v);Sam_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//                case R.id.Dim_08:Dim_08.addView(v);Dim_08.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_09:Dim_09.addView(v);Dim_09.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_10:Dim_10.addView(v);Dim_10.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_11:Dim_11.addView(v);Dim_11.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_12:Dim_12.addView(v);Dim_12.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_13:Dim_13.addView(v);Dim_13.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_14:Dim_14.addView(v);Dim_14.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_15:Dim_15.addView(v);Dim_15.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_16:Dim_16.addView(v);Dim_16.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_17:Dim_17.addView(v);Dim_17.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//                case R.id.Dim_18:Dim_18.addView(v);Dim_18.setBackgroundColor(Color.parseColor("#EC7C32"));
//                    break;
//
//
//                }
//
//            }
        }
    }

    public void onclickbtn(View view) {
        findViewById(R.id.floatingActionButtonweek).setVisibility(View.INVISIBLE);
        findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
    }


    private String urlExtensionforSeanceDetails(JSONObject response) throws JSONException {
        String URL = null;
        if (getIntent().getStringExtra("emploitype") != null) {
            id = getIntent().getStringExtra("id");
            switch (getIntent().getStringExtra("emploitype")) {


                case "1":
                    URL = "seances/allnamesMonitor/" + response.getString("startDate") + "/" + id;
                    break;

                case "0":
                    URL = "seances/allnames/" + response.getString("startDate");
                    break;

                case "2":
                    URL = "seances/allnamesClient/" + response.getString("startDate") + "/" + id;
                    break;


            }
        } else {
            URL = "seances/getwithdate/" + response.getString("startDate");
        }
        return URL;
    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("startDate", starDate.toString());
        Log.d("testttttststststst", starDate.toString());
    }

}