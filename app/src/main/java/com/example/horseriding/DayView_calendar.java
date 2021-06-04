package com.example.horseriding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class DayView_calendar extends AppCompatActivity {
    static LocalDateTime DateInit;
    String fullName;
    String URLEXTENSION;
    String color;
    List<Seance> lSeance;
    LinearLayout time_08, time_09, time_10, time_11, time_12, time_13, time_14, time_15, time_16, time_17, time_18;
    GridLayout contentDay;
    TextView day;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String id;
    private String monitor;
    BottomNavigationView bnv;
    private Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);
        setTitle("Emploi du jour");
        id = getIntent().getStringExtra("id");
        contentDay = findViewById(R.id.contentofday);
        day = findViewById(R.id.day);
        if (savedInstanceState == null) {
            DateInit = LocalDateTime.of(2020, 9, 14, 15, 48);
        } else {
            int annee = Integer.parseInt(savedInstanceState.getString("startDate").split("-")[0]);
            int mois = Integer.parseInt(savedInstanceState.getString("startDate").split("-")[1]);
            int jour = Integer.parseInt(savedInstanceState.getString("startDate").split("-")[2]);
            DateInit = LocalDateTime.of(annee, mois, jour, 00, 00);
        }
        time_08 = findViewById(R.id.time_08);
        time_09 = findViewById(R.id.time_09);
        time_10 = findViewById(R.id.time_10);
        time_11 = findViewById(R.id.time_11);
        time_12 = findViewById(R.id.time_12);
        time_13 = findViewById(R.id.time_13);
        time_14 = findViewById(R.id.time_14);
        time_15 = findViewById(R.id.time_15);
        time_16 = findViewById(R.id.time_16);
        time_17 = findViewById(R.id.time_17);
        time_18 = findViewById(R.id.time_18);
        drawable = getResources().getDrawable(R.drawable.ic_baseline_brightness_1_24, getTheme());
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
                        i = new Intent(DayView_calendar.this, DashboardActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.setting_nav:
                        i = new Intent(DayView_calendar.this, EditFormUser.class);
                        DayView_calendar.this.startActivity(i);
                        break;
                    case R.id.logout_nav:
                        SessionManager sessionManager = new SessionManager(DayView_calendar.this);
                        sessionManager.logout();
                        Intent splashIntent = new Intent(DayView_calendar.this, LoginActivity.class);
                        DayView_calendar.this.startActivity(splashIntent);
                        DayView_calendar.this.finish();
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
                calenderSwitcher(DayView_calendar.this, MonthView_Calendar.class);
                break;
            case R.id.week_view:
                calenderSwitcher(DayView_calendar.this, WeekView_Calendar.class);
                break;
            case R.id.day_view:/*findViewById(R.id.week_view).setVisibility(View.INVISIBLE)*/
                Toast.makeText(DayView_calendar.this, "already in day section", Toast.LENGTH_SHORT).show();

        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Locale local = new Locale("fr", "Fr");

        day.setText(dateFormatter.format(DateInit));
        urlExtension();
        //Creation des parametres du TextView qui va être insérer
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);
        JsonArrayRequest jArray = new JsonArrayRequest(Request.Method.GET, WS.URL + URLEXTENSION, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject j = null;
                for (int i = 0; i < response.length(); i++) {
                    TextView v = new TextView(DayView_calendar.this);
                    v.setLayoutParams(params);
                    v.setBackgroundResource(R.drawable.textview_border);
                    v.setTextSize(19);
                    v.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    try {
                        j = response.getJSONObject(i);
                        Seance seance = new Seance(j.getInt("seanceId"), j.getInt("seanceGrpId"), j.getInt("clientId"), j.getInt("monitorId"), j.getInt("durationMinut"), j.getString("comments"), j.getString("startDate"));
                        DatabaseHandler databaseHandler = new DatabaseHandler(DayView_calendar.this);
                        databaseHandler.saveSeance(seance);

                        String dateDay = j.getString("startDate").substring(0, 10);
                        String dateTime = j.getString("startDate").substring(11, 16);
                        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

                        if (LocalDateTime.now().compareTo(LocalDateTime.parse(j.getString("startDate"))) <= 0) {
                            color = "#00DAC5";
                        } else {

                            color = "#ff6347";
                        }
                        v.setText(j.getInt("seanceId") + " " + j.getString("comments"));
                        switch (dateTime) {
                            case "08:00":
                                time_08.addView(v);
                                break;
                            case "09:00":
                                time_09.addView(v);
                                break;
                            case "10:00":
                                time_10.addView(v);
                                break;
                            case "11:00":
                                time_11.addView(v);
                                break;
                            case "12:00":
                                time_12.addView(v);
                                break;
                            case "13:00":
                                time_13.addView(v);
                                break;
                            case "14:00":
                                time_14.addView(v);
                                break;
                            case "15:00":
                                time_15.addView(v);
                                break;
                            case "16:00":
                                time_16.addView(v);
                                break;
                            case "17:00":
                                time_17.addView(v);
                                break;
                            case "18:00":
                                time_18.addView(v);
                                break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("ONERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOOOR", " " + error);
            }
        });
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jArray);
    }

    private void urlExtension() {
        if (getIntent().getStringExtra("emploitype") != null) {
            id = getIntent().getStringExtra("id");
            switch (getIntent().getStringExtra("emploitype")) {
                case "1":
                    URLEXTENSION = "seances/monitor/" + day.getText().toString() + "/" + id;
                    break;

                case "0":
                    URLEXTENSION = "seances/getwithdate/" + day.getText().toString();
                    break;

                case "2":
                    URLEXTENSION = "seances/getwithdate/" + day.getText().toString() + "/" + id;
                    break;
            }
        } else {
            URLEXTENSION = "seances/getwithdate/" + day.getText().toString();
        }
    }

    public void daySwitcher(View view) {
        init();

        switch (view.getId()) {
            case R.id.btnnext:
                DateInit = DateInit.plusDays(1);
                break;
            case R.id.btnprevious:
                DateInit = DateInit.minusDays(1);
                break;
        }
        Locale local = new Locale("fr", "Fr");
        onResume();
    }

    void init() {
        LinearLayout l = null;
        for (int i = 0; i < contentDay.getChildCount(); i++) {
            l = (LinearLayout) contentDay.getChildAt(i);
            l.removeAllViews();
        }
    }

    public void clickSeance(View view) {
        LinearLayout linearLayout = findViewById(view.getId());
        if (linearLayout.getChildCount() != 0) {
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                TextView textView = (TextView) linearLayout.getChildAt(i);
                String[] list = textView.getText().toString().split(" ");
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, WS.URL + "seances/" + list[0], null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            monitor = getMonitorName(response.getInt("monitorId"));


                            AlertDialog.Builder builder = new AlertDialog.Builder(DayView_calendar.this);

                            builder.setTitle("Seance Detail:");
                            builder.setMessage("ID = " + response.getInt("seanceId") + "\n\n" + "Sart Date = " + response.getString("startDate") + "\n\n" + "Monitor = " + monitor + "\n\n" +
                                    "Duration = " + response.getInt("durationMinut") + "\n\n" +
                                    "Comment = " + response.getString("comments")
                            );

                            builder.setNegativeButton("Close", null);
                            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        LocalDateTime date = LocalDateTime.parse(response.getString("startDate"));
                                        String dateDay = response.getString("startDate").substring(0, 10);
                                        String dateTime = response.getString("startDate").substring(11, 16);

                                        DateInit = LocalDateTime.of(LocalDate.parse(dateDay), LocalTime.parse(dateTime));
                                        StringRequest dr = new StringRequest(Request.Method.DELETE, WS.URL + "seances/" + response.getInt("seanceId"),
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String res) {
                                                        // response

                                                        startActivity(getIntent());
                                                        Toast.makeText(DayView_calendar.this, "Deleted", Toast.LENGTH_LONG).show();
                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        // error.

                                                    }
                                                }
                                        );
                                        MySingleton.getInstance(DayView_calendar.this).addToRequestQueue(dr);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

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
                                Log.e(DayView_calendar.class.getSimpleName(), error.getMessage());
                            }
                        }

                );
                MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
            }
        } else {
            Intent addSeanceIntent = new Intent(DayView_calendar.this, DateTimePicker.class);
            dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = dateFormatter.format(DateInit);
            switch (view.getId()) {

                case R.id.time_08:
                    Seance seance = new Seance(1, 1, 1, 2, 60, "", date);


                    addSeanceIntent.putExtra("seance", (Serializable) seance);
                    addSeanceIntent.putExtra("time", "08:00");
                    DayView_calendar.this.startActivity(addSeanceIntent);
                    DayView_calendar.this.finish();
                    break;
                case R.id.time_09:

                    addSeanceIntent.putExtra("seance", (Serializable) new Seance(1, 1, 1, 2, 60, "", dateFormatter.format(DateInit)));
                    addSeanceIntent.putExtra("time", "09:00");
                    DayView_calendar.this.startActivity(addSeanceIntent);
                    DayView_calendar.this.finish();
                    break;
                case R.id.time_10:

                    addSeanceIntent.putExtra("seance", (Serializable) new Seance(1, 1, 1, 2, 60, "", dateFormatter.format(DateInit)));
                    addSeanceIntent.putExtra("time", "10:00");
                    DayView_calendar.this.startActivity(addSeanceIntent);
                    DayView_calendar.this.finish();
                    break;
                case R.id.time_11:

                    addSeanceIntent.putExtra("seance", (Serializable) new Seance(1, 1, 1, 2, 60, "", dateFormatter.format(DateInit)));
                    addSeanceIntent.putExtra("time", "11:00");
                    DayView_calendar.this.startActivity(addSeanceIntent);
                    DayView_calendar.this.finish();
                    break;
                case R.id.time_12:

                    addSeanceIntent.putExtra("seance", (Serializable) new Seance(1, 1, 1, 2, 60, "", dateFormatter.format(DateInit)));
                    addSeanceIntent.putExtra("time", "12:00");
                    DayView_calendar.this.startActivity(addSeanceIntent);
                    DayView_calendar.this.finish();
                    break;
                case R.id.time_13:

                    addSeanceIntent.putExtra("seance", (Serializable) new Seance(1, 1, 1, 2, 60, "", dateFormatter.format(DateInit)));
                    addSeanceIntent.putExtra("time", "13:00");
                    DayView_calendar.this.startActivity(addSeanceIntent);
                    DayView_calendar.this.finish();
                    break;
                case R.id.time_14:
                    addSeanceIntent.putExtra("seance", (Serializable) new Seance(1, 1, 1, 2, 60, "", dateFormatter.format(DateInit)));
                    addSeanceIntent.putExtra("time", "14:00");
                    DayView_calendar.this.startActivity(addSeanceIntent);
                    DayView_calendar.this.finish();
                    break;
                case R.id.time_15:

                    addSeanceIntent.putExtra("seance", (Serializable) new Seance(1, 1, 1, 2, 60, "", dateFormatter.format(DateInit)));
                    addSeanceIntent.putExtra("time", "15:00");
                    DayView_calendar.this.startActivity(addSeanceIntent);
                    DayView_calendar.this.finish();
                    break;
                case R.id.time_16:

                    addSeanceIntent.putExtra("seance", (Serializable) new Seance(1, 1, 1, 2, 60, "", dateFormatter.format(DateInit)));
                    addSeanceIntent.putExtra("time", "16:00");
                    DayView_calendar.this.startActivity(addSeanceIntent);
                    DayView_calendar.this.finish();
                    break;
                case R.id.time_17:

                    addSeanceIntent.putExtra("seance", (Serializable) new Seance(1, 1, 1, 2, 60, "", dateFormatter.format(DateInit)));
                    addSeanceIntent.putExtra("time", "17:00");
                    DayView_calendar.this.startActivity(addSeanceIntent);
                    DayView_calendar.this.finish();
                    break;
                case R.id.time_18:

                    addSeanceIntent.putExtra("seance", (Serializable) new Seance(1, 1, 1, 2, 60, "", dateFormatter.format(DateInit)));
                    addSeanceIntent.putExtra("time", "18:00");
                    DayView_calendar.this.startActivity(addSeanceIntent);
                    DayView_calendar.this.finish();
                    break;
            }
        }


    }

    private String getMonitorName(int id) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, WS.URL + "users/by/" + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    fullName = response.getString("userFname") + " " + response.getString("userLname");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(DayView_calendar.class.getSimpleName(), error.getMessage());
                    }
                }

        );
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
        return fullName;
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("startDate", day.getText().toString());
    }
}