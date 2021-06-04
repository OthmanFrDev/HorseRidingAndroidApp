package com.example.horseriding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MonthView_Calendar extends AppCompatActivity {

    LocalDate dateInit = LocalDate.of(2020, 9, 14);
    LinearLayout Lun_1, Mar_1, Mer_1, Jeu_1, Ven_1, Sam_1, Dim_1,
            Lun_2, Mar_2, Mer_2, Jeu_2, Ven_2, Sam_2, Dim_2,
            Lun_3, Mar_3, Mer_3, Jeu_3, Ven_3, Sam_3, Dim_3,
            Lun_4, Mar_4, Mer_4, Jeu_4, Ven_4, Sam_4, Dim_4,
            Lun_5, Mar_5, Mer_5, Jeu_5, Ven_5, Sam_5, Dim_5;
    LocalDate starDate, endDate;
    TextView day;
    Drawable drawable;
    LocalDate firstDate = dateInit.withDayOfMonth(1);
    LinearLayout.LayoutParams params;

    TextView v;
    String startDateString;
    String endDateString;
    String url;
    String id;
    String emploitype;


    DatabaseHandler databaseHandler;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private List<Seance> seances;
    private GridLayout contentMonth;
    private BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Emploi du Mois");
        setContentView(R.layout.activity_month_view);

        Intent emploiIntent = getIntent();
        id = emploiIntent.getStringExtra("id");
        emploitype = getIntent().getStringExtra("emploitype");
        day = findViewById(R.id.day);
        contentMonth = findViewById(R.id.monthgrid);
        drawable = getResources().getDrawable(R.drawable.ic_baseline_brightness_1_24, getTheme());
        Lun_1 = findViewById(R.id.Lun_1);
        Mar_1 = findViewById(R.id.Mar_1);
        Mer_1 = findViewById(R.id.Mer_1);
        Jeu_1 = findViewById(R.id.Jeu_1);
        Ven_1 = findViewById(R.id.Ven_1);
        Sam_1 = findViewById(R.id.Sam_1);
        Dim_1 = findViewById(R.id.Dim_1);
        Lun_2 = findViewById(R.id.Lun_2);
        Mar_2 = findViewById(R.id.Mar_2);
        Mer_2 = findViewById(R.id.Mer_2);
        Jeu_2 = findViewById(R.id.Jeu_2);
        Ven_2 = findViewById(R.id.Ven_2);
        Sam_2 = findViewById(R.id.Sam_2);
        Dim_2 = findViewById(R.id.Dim_2);
        Lun_3 = findViewById(R.id.Lun_3);
        Mar_3 = findViewById(R.id.Mar_3);
        Mer_3 = findViewById(R.id.Mer_3);
        Jeu_3 = findViewById(R.id.Jeu_3);
        Ven_3 = findViewById(R.id.Ven_3);
        Sam_3 = findViewById(R.id.Sam_3);
        Dim_3 = findViewById(R.id.Dim_3);
        Lun_4 = findViewById(R.id.Lun_4);
        Mar_4 = findViewById(R.id.Mar_4);
        Mer_4 = findViewById(R.id.Mer_4);
        Jeu_4 = findViewById(R.id.Jeu_4);
        Ven_4 = findViewById(R.id.Ven_4);
        Sam_4 = findViewById(R.id.Sam_4);
        Dim_4 = findViewById(R.id.Dim_4);
        Lun_5 = findViewById(R.id.Lun_5);
        Mar_5 = findViewById(R.id.Mar_5);
        Mer_5 = findViewById(R.id.Mer_5);
        Jeu_5 = findViewById(R.id.Jeu_5);
        Ven_5 = findViewById(R.id.Ven_5);
        Sam_5 = findViewById(R.id.Sam_5);
        Dim_5 = findViewById(R.id.Dim_5);
        databaseHandler = new DatabaseHandler(MonthView_Calendar.this);
        starDate = firstDate;
        endDate = firstDate;
        switch (firstDate.getDayOfWeek().getValue()) {
            case 1:
                starDate = firstDate;
                endDate = starDate.plusDays(6);
                break;
            case 2:
                starDate = firstDate.minusDays(1);
                endDate = starDate.plusDays(6);
                break;
            case 3:
                starDate = firstDate.minusDays(2);
                endDate = starDate.plusDays(6);
                break;
            case 4:
                starDate = firstDate.minusDays(3);
                endDate = starDate.plusDays(6);
                break;
            case 5:
                starDate = firstDate.minusDays(4);
                endDate = starDate.plusDays(6);
                break;
            case 6:
                starDate = firstDate.minusDays(5);
                endDate = starDate.plusDays(6);
                break;
            case 7:
                starDate = firstDate.minusDays(6);
                endDate = starDate.plusDays(6);
                break;

        }
        startDateString = dateFormatter.format(starDate);
        endDateString = dateFormatter.format(endDate);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);
        bnv = findViewById(R.id.bottom_navigation);
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
                        i = new Intent(MonthView_Calendar.this, DashboardActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.setting_nav:
                        i = new Intent(MonthView_Calendar.this, EditFormUser.class);
                        MonthView_Calendar.this.startActivity(i);
                        break;
                    case R.id.logout_nav:
                        SessionManager sessionManager = new SessionManager(MonthView_Calendar.this);
                        sessionManager.logout();
                        Intent splashIntent = new Intent(MonthView_Calendar.this, LoginActivity.class);
                        MonthView_Calendar.this.startActivity(splashIntent);
                        MonthView_Calendar.this.finish();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mI = getMenuInflater();
        mI.inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = null;
        switch (item.getItemId()) {
            case R.id.month_view:
                findViewById(R.id.week_view).setVisibility(View.INVISIBLE);
                break;

            case R.id.week_view:
                calenderSwitcher(MonthView_Calendar.this, WeekView_Calendar.class);
                break;
            case R.id.day_view:
                calenderSwitcher(MonthView_Calendar.this, DayView_calendar.class);
                break;

        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();



        emploitype();
        day.setText(firstDate.getMonth().toString() + " " + firstDate.getYear());
        JsonArrayRequest jsonWeek1 = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                LocalDateTime dateFromResponse;
                JSONObject j = null;

                for (int i = 0; i < response.length(); i++) {
                    v = new TextView(MonthView_Calendar.this);
                    v.setLayoutParams(params);
                    v.setBackgroundResource(R.drawable.textview_border);
                    v.setTextSize(11);
                    v.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    try {

                        j = response.getJSONObject(i);
                        dateFromResponse = LocalDateTime.parse(j.getString("startDate"));
                        v.setText(" " + String.valueOf(j.getInt("seanceId")));


                        switch (dateFromResponse.getDayOfWeek().getValue()) {
                            case 1:
                                Lun_1.addView(v);
                                break;
                            case 2:
                                Mar_1.addView(v);
                                break;
                            case 3:
                                Mer_1.addView(v);
                                break;
                            case 4:
                                Jeu_1.addView(v);
                                break;
                            case 5:
                                Ven_1.addView(v);
                                break;
                            case 6:
                                Sam_1.addView(v);
                                break;
                            case 7:
                                Dim_1.addView(v);
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

            }
        });
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonWeek1);
        starDate = starDate.plusDays(7);
        endDate = endDate.plusDays(7);

        startDateString = dateFormatter.format(starDate);
        endDateString = dateFormatter.format(endDate);
        emploitype();
        JsonArrayRequest jsonWeek2 = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                LocalDateTime dateFromResponse;
                JSONObject j = null;
                for (int i = 0; i < response.length(); i++) {
                    v = new TextView(MonthView_Calendar.this);
                    v.setLayoutParams(params);
                    v.setBackgroundResource(R.drawable.textview_border);
                    v.setTextSize(11);
                    v.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

                    try {

                        j = response.getJSONObject(i);
                        v.setText(" " + String.valueOf(j.getInt("seanceId")));
                        dateFromResponse = LocalDateTime.parse(j.getString("startDate"));
                        switch (dateFromResponse.getDayOfWeek().getValue()) {
                            case 1:
                                Lun_2.addView(v);
                                break;
                            case 2:
                                Mar_2.addView(v);
                                break;
                            case 3:
                                Mer_2.addView(v);
                                break;
                            case 4:
                                Jeu_2.addView(v);
                                break;
                            case 5:
                                Ven_2.addView(v);
                                break;
                            case 6:
                                Sam_2.addView(v);
                                break;
                            case 7:
                                Dim_2.addView(v);
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

            }
        });
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonWeek2);

        starDate = starDate.plusDays(7);
        endDate = endDate.plusDays(7);
        startDateString = dateFormatter.format(starDate);
        endDateString = dateFormatter.format(endDate);
        emploitype();
        JsonArrayRequest jsonWeek3 = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                LocalDateTime dateFromResponse;
                JSONObject j = null;
                for (int i = 0; i < response.length(); i++) {
                    v = new TextView(MonthView_Calendar.this);
                    v.setLayoutParams(params);
                    v.setBackgroundResource(R.drawable.textview_border);
                    v.setTextSize(11);
                    v.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    try {

                        j = response.getJSONObject(i);
                        v.setText(" " + String.valueOf(j.getInt("seanceId")));
                        dateFromResponse = LocalDateTime.parse(j.getString("startDate"));
                        switch (dateFromResponse.getDayOfWeek().getValue()) {
                            case 1:
                                Lun_3.addView(v);
                                break;
                            case 2:
                                Mar_3.addView(v);
                                break;
                            case 3:
                                Mer_3.addView(v);
                                break;
                            case 4:
                                Jeu_3.addView(v);
                                break;
                            case 5:
                                Ven_3.addView(v);
                                break;
                            case 6:
                                Sam_3.addView(v);
                                break;
                            case 7:
                                Dim_3.addView(v);
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

            }
        });
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonWeek3);

        starDate = starDate.plusDays(7);
        endDate = endDate.plusDays(7);
        startDateString = dateFormatter.format(starDate);
        endDateString = dateFormatter.format(endDate);
        emploitype();
        JsonArrayRequest jsonWeek4 = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                LocalDateTime dateFromResponse;
                JSONObject j = null;
                for (int i = 0; i < response.length(); i++) {
                    v = new TextView(MonthView_Calendar.this);
                    v.setLayoutParams(params);
                    v.setBackgroundResource(R.drawable.textview_border);
                    v.setTextSize(11);
                    v.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    try {
                        j = response.getJSONObject(i);
                        v.setText(" " + String.valueOf(j.getInt("seanceId")));
                        dateFromResponse = LocalDateTime.parse(j.getString("startDate"));
                        switch (dateFromResponse.getDayOfWeek().getValue()) {
                            case 1:
                                Lun_4.addView(v);
                                break;
                            case 2:
                                Mar_4.addView(v);
                                break;
                            case 3:
                                Mer_4.addView(v);
                                break;
                            case 4:
                                Jeu_4.addView(v);
                                break;
                            case 5:
                                Ven_4.addView(v);
                                break;
                            case 6:
                                Sam_4.addView(v);
                                break;
                            case 7:
                                Dim_4.addView(v);
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

            }
        });
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonWeek4);

        starDate = starDate.plusDays(7);
        endDate = endDate.plusDays(7);
        startDateString = dateFormatter.format(starDate);
        endDateString = dateFormatter.format(endDate);
        emploitype();
        JsonArrayRequest jsonWeek5 = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                LocalDateTime dateFromResponse;
                JSONObject j = null;
                for (int i = 0; i < response.length(); i++) {
                    v = new TextView(MonthView_Calendar.this);
                    v.setLayoutParams(params);
                    v.setBackgroundResource(R.drawable.textview_border);
                    v.setTextSize(11);
                    v.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    try {
                        j = response.getJSONObject(i);
                        v.setText(" " + String.valueOf(j.getInt("seanceId")));
                        dateFromResponse = LocalDateTime.parse(j.getString("startDate"));
                        switch (dateFromResponse.getDayOfWeek().getValue()) {
                            case 1:
                                Lun_5.addView(v);
                                break;
                            case 2:
                                Mar_5.addView(v);
                                break;
                            case 3:
                                Mer_5.addView(v);
                                break;
                            case 4:
                                Jeu_5.addView(v);
                                break;
                            case 5:
                                Ven_5.addView(v);
                                break;
                            case 6:
                                Sam_5.addView(v);
                                break;
                            case 7:
                                Dim_5.addView(v);
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

            }
        });

        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonWeek5);

    }



    public void monthChanger(View view) {
        switch (view.getId()) {
            case R.id.btnnext:
                firstDate = firstDate.plusMonths(1);

                break;
            case R.id.btnprevious:
                firstDate = firstDate.minusMonths(1);
                break;
        }
        init();

        starDate = firstDate;
        endDate = firstDate;
        switch (firstDate.getDayOfWeek().getValue()) {
            case 1:
                starDate = firstDate;
                endDate = starDate.plusDays(6);
                break;
            case 2:
                starDate = firstDate.minusDays(1);
                endDate = starDate.plusDays(6);
                break;
            case 3:
                starDate = firstDate.minusDays(2);
                endDate = starDate.plusDays(6);
                break;
            case 4:
                starDate = firstDate.minusDays(3);
                endDate = starDate.plusDays(6);
                break;
            case 5:
                starDate = firstDate.minusDays(4);
                endDate = starDate.plusDays(6);
                break;
            case 6:
                starDate = firstDate.minusDays(5);
                endDate = starDate.plusDays(6);
                break;
            case 7:
                starDate = firstDate.minusDays(6);
                endDate = starDate.plusDays(6);
                break;

        }
        startDateString = dateFormatter.format(starDate);
        endDateString = dateFormatter.format(endDate);
        onResume();
    }

    private void emploitype() {
        if (getIntent().getStringExtra("emploitype") != null) {
            switch (getIntent().getStringExtra("emploitype")) {
                case "0":
                    url = WS.URL + "seances/" + startDateString + "/" + endDateString;
                    seances = databaseHandler.readSeance();
                    // getTache();
                    break;
                case "1":
                    url = WS.URL + "seances/monitor/" + startDateString + "/" + endDateString + "/" + id;
                    //getTache();
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


    void init() {

        LinearLayout l = null;
        for (int i = 0; i < contentMonth.getChildCount(); i++) {

            l = (LinearLayout) contentMonth.getChildAt(i);
            l.removeAllViews();



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

    public void onclickbtn(View view) {
        findViewById(R.id.floatingActionButtonweek).setVisibility(View.INVISIBLE);
        findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
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
                        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, WS.URL + URLEXTENSION, null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {


                                stringBuilder.append("seance : \n");
                                JSONObject j = null;
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        j = response.getJSONObject(i);
                                        stringBuilder.append("ID : " + j.getInt("seanceId") + " Monitor : " + j.getString("monitorId") + " Client : " + j.getString("clientId") + ".\n\n");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }


                                AlertDialog.Builder builder = new AlertDialog.Builder(MonthView_Calendar.this);
                                builder.setTitle("details");
                                builder.setMessage(stringBuilder);

                                AlertDialog dialog = builder.create();
                                dialog.show();

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(MainActivity.class.getSimpleName(), error.getMessage());
                            }
                        }

                        );


                        MySingleton.getInstance(MonthView_Calendar.this).addToRequestQueue(req);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            // getSeance(databaseHandler.readSeance());

                        }
                    }

            );

            MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);

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

    private String urlExtensionforSeanceDetails(JSONObject response) throws JSONException {
        String URL = null;
        if (getIntent().getStringExtra("emploitype") != null) {
            id = getIntent().getStringExtra("id");
            switch (getIntent().getStringExtra("emploitype")) {


                case "1":
                    URL = "seances/allnamesshortdateMonitor/" + response.getString("startDate").split("T")[0] + "/" + id;
                    break;

                case "0":
                    URL = "seances/allnamesshortdate/" + response.getString("startDate").split("T")[0];
                    break;

                case "2":
                    URL = "seances/allnamesshortdate/" + response.getString("startDate").split("T")[0] + "/" + id;
                    break;


            }
        } else {
            URL = "seances/allnamesshortdate/" + response.getString("startDate").split("T")[0];
        }
        return URL;
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("startDate",starDate+"");
    }

}