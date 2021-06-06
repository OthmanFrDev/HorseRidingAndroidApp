package com.example.horseriding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class DayView_calendar extends AppCompatActivity {
    static LocalDateTime DateInit;

    String fullName;
    String URLEXTENSION;
    String color;
    List<Seance> lSeance;
    LinearLayout time_08, time_09, time_10, time_11, time_12, time_13, time_14, time_15, time_16, time_17, time_18;
    GridLayout contentDay;
    TextView day;

    DateTimeFormatter dateFormatter;
    DatabaseHandler databaseHandler;

    private String id;
    private String monitor;
    BottomNavigationView bnv;
    private Drawable drawable;
    private Dialog dialog;
    private Dialog dialogadd;
    private boolean istask;
    private View viewcalender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);

        setTitle("Emploi du jour");
        id = getIntent().getStringExtra("id");
        contentDay = findViewById(R.id.contentofday);
        day = findViewById(R.id.day);
        dialog = new Dialog(DayView_calendar.this);
        dialogadd = new Dialog(DayView_calendar.this);
        databaseHandler = new DatabaseHandler(DayView_calendar.this);
        if (savedInstanceState == null) {
            DateInit = LocalDateTime.now();
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


        if (DateInit == null) {
            DateInit = LocalDateTime.of(2021, 6, 10, 15, 48);
        }


        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        day.setText(dateFormatter.format(DateInit));


        urlExtension();

        //Creation du TextView qui va être insérer


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);


        params.setMargins(10, 10, 10, 10);
        dialog.setContentView(R.layout.progressbar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(DayView_calendar.this, R.drawable.textview_border);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        JsonArrayRequest jArray = new JsonArrayRequest(Request.Method.GET, WS.URL + URLEXTENSION, null, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {
                dialog.cancel();
                JSONObject j = null;

                for (int i = 0; i < response.length(); i++) {

                    TextView v = new TextView(DayView_calendar.this);
                    v.setLayoutParams(params);
                    v.setTextSize(15);
                    v.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    try {
                        j = response.getJSONObject(i);
                        Seance seance = new Seance(j.getInt("seanceId"), j.getInt("seanceGrpId"), j.getInt("clientId"), j.getInt("monitorId"), j.getInt("durationMinut"), j.getString("comments"), j.getString("startDate"));


                        databaseHandler.saveSeance(seance);

                        String dateDay = j.getString("startDate").substring(0, 10);
                        String dateTime = j.getString("startDate").substring(11, 16);
                        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

                        if (LocalDateTime.now().compareTo(LocalDateTime.parse(j.getString("startDate"))) <= 0) {
                            DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#048FD2"));
                            v.setBackgroundResource(R.drawable.textview_border);

                        } else {
                            DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#ff6347"));
                            v.setBackgroundResource(R.drawable.textview_border);

                        }

                        v.setText("seance : "+j.getInt("seanceId") );
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

                Toast.makeText(DayView_calendar.this, "server erreur check connection", Toast.LENGTH_SHORT).show();
                init();
                dialog.cancel();
                List<Seance> seances = databaseHandler.readSeance().stream().filter(f -> f.getStartDate().contains(day.getText())).collect(Collectors.toList());
                getSeance(seances);
                dialog.cancel();

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
                    getTache();
                    break;

                case "0":
                    URLEXTENSION = "seances/getwithdate/" + day.getText().toString();
                    getTache();
                    break;

                case "2":
                    URLEXTENSION = "seances/getwithdate/" + day.getText().toString() + "/" + id;
                    break;


            }
        } else {
            URLEXTENSION = "seances/getwithdate/" + day.getText().toString();
            getTache();
        }
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

            URL = "seances/getwithdate/" + day.getText().toString();
        }
        return URL;

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
        StringBuilder stringBuilder = new StringBuilder();



            if (linearLayout.getChildCount() != 0) {

                TextView textView = (TextView) linearLayout.getChildAt(0);


                String[] list = textView.getText().toString().split(" ");
                String text = list[0];
                if (text.equals("task")) {
                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, WS.URL + "tasks/"+ list[2], null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {


                            stringBuilder.append("task : \n\n");
                            JSONObject j = null;

                            try {
                                j = response;



                                stringBuilder.append("ID     : " + j.getInt("taskId") + " \ndate : " + j.getString("startDate") + " \ntitle  : " + j.getString("title") + "\n\n" +"detail : \n" + j.getString("detail") );
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }





                            AlertDialog.Builder builder = new AlertDialog.Builder(DayView_calendar.this);
                            builder.setTitle("details");
                            builder.setMessage(stringBuilder);

                            AlertDialog dialog = builder.create();
                            dialog.show();


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }

                    );


                    MySingleton.getInstance(DayView_calendar.this).addToRequestQueue(req);
                }
                else {
                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, WS.URL + "seances/" + list[2], null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {

                                String URLEXTENSION = urlExtensionforSeanceDetails(response);
                                JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, WS.URL + URLEXTENSION, null, new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray resp) {

                                        try {
                                            stringBuilder.append("Seance : \n\n");
                                            JSONObject j = null;
                                            for (int i = 0; i < resp.length(); i++) {
                                                j = resp.getJSONObject(i);
                                                stringBuilder.append("ID : " + j.getInt("seanceId") + " Monitor : " + j.getString("monitorId") + " Client : " + j.getString("clientId") + ".\n\n");


                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DayView_calendar.this);
                                        builder.setTitle("details");
                                        builder.setMessage(stringBuilder);

                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.e(DayView_calendar.class.getSimpleName(), error.getMessage());
                                            }
                                        }

                                );
                                MySingleton.getInstance(DayView_calendar.this).addToRequestQueue(req);


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
            }


        else {
            viewcalender=view;
            dialogadd.setContentView(R.layout.dialog_taskorseance);
            dialogadd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogadd.show();




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

    void getmonitor(int id) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "http://192.168.111.1:45455/users/by/" + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject resp) {

                try {
                    resp.getString("startDate");


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

        MySingleton.getInstance(DayView_calendar.this).addToRequestQueue(req);
    }

    void getSeance(List<Seance> seances) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(10, 10, 10, 10);
        for (int i = 0; i < seances.size(); i++) {
            TextView v = new TextView(DayView_calendar.this);
            v.setLayoutParams(params);
            v.setBackgroundResource(R.drawable.textview_border);
            v.setTextSize(19);
            v.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            String dateTime = seances.get(i).getStartDate().substring(11, 16);
            v.setText(seances.get(i).getSeanceId() + " " + seances.get(i).getComments());
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
        }
    }


    void getTache() {
        String urlTask = null;


        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        day.setText(dateFormatter.format(DateInit));


        if (getIntent().getStringExtra("emploitype") != null) {
            switch (getIntent().getStringExtra("emploitype")) {
                case "0":
                    urlTask = WS.URL + "tasks/daytasks/" + day.getText().toString();


                    break;
                case "1":
                    urlTask = WS.URL + "tasks/daytask/" + day.getText().toString() + "/" + id;


                    break;


            }
        } else {

            urlTask = WS.URL + "tasks/daytasks/" + day.getText().toString();
        }

        //Creation du TextView qui va être insérer

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(10, 10, 10, 10);
        dialog.setContentView(R.layout.progressbar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        JsonArrayRequest jArray = new JsonArrayRequest(Request.Method.GET, urlTask, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                dialog.cancel();
                JSONObject j = null;
                for (int i = 0; i < response.length(); i++) {
                    TextView v = new TextView(DayView_calendar.this);
                    v.setLayoutParams(params);
                    v.setBackgroundResource(R.drawable.textview_border);
                    v.setTextSize(19);
                    v.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    try {
                        j = response.getJSONObject(i);


                        String dateDay = j.getString("startDate").substring(0, 10);
                        String dateTime = j.getString("startDate").substring(11, 16);
                        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

                        if (LocalDateTime.now().compareTo(LocalDateTime.parse(j.getString("startDate"))) <= 0) {
                            color = "#00DAC5";
                        } else {

                            color = "#ff6347";
                        }

                        v.setText("task : "+j.getInt("taskId") );
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

            }
        });
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jArray);


    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("startDate", day.getText().toString());

    }

    void dialogOnClick(View view)
    {

    }
    void addingSeance( View view)
    {
        Intent addSeanceIntent = new Intent(DayView_calendar.this, SeanceController.class);
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
    void addingTask( View view)
    {
        Intent addSeanceIntent = new Intent(DayView_calendar.this, TaskController.class);
        dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dateFormatter.format(DateInit);
        Task task = new Task(1, date, 60, "", "", date,2);
        switch (view.getId()) {


            case R.id.time_08:



                addSeanceIntent.putExtra("task", (Serializable) task);
                addSeanceIntent.putExtra("time", "08:00");
                DayView_calendar.this.startActivity(addSeanceIntent);
                DayView_calendar.this.finish();
                break;
            case R.id.time_09:

                addSeanceIntent.putExtra("task", (Serializable)task );
                addSeanceIntent.putExtra("time", "09:00");
                DayView_calendar.this.startActivity(addSeanceIntent);
                DayView_calendar.this.finish();
                break;
            case R.id.time_10:

                addSeanceIntent.putExtra("task", (Serializable) task);
                addSeanceIntent.putExtra("time", "10:00");
                DayView_calendar.this.startActivity(addSeanceIntent);
                DayView_calendar.this.finish();
                break;
            case R.id.time_11:

                addSeanceIntent.putExtra("task", (Serializable)task );
                addSeanceIntent.putExtra("time", "11:00");
                DayView_calendar.this.startActivity(addSeanceIntent);
                DayView_calendar.this.finish();
                break;
            case R.id.time_12:

                addSeanceIntent.putExtra("task", (Serializable) task);
                addSeanceIntent.putExtra("time", "12:00");
                DayView_calendar.this.startActivity(addSeanceIntent);
                DayView_calendar.this.finish();
                break;
            case R.id.time_13:

                addSeanceIntent.putExtra("task", (Serializable) task);
                addSeanceIntent.putExtra("time", "13:00");
                DayView_calendar.this.startActivity(addSeanceIntent);
                DayView_calendar.this.finish();
                break;
            case R.id.time_14:
                addSeanceIntent.putExtra("task", (Serializable)task );
                addSeanceIntent.putExtra("time", "14:00");
                DayView_calendar.this.startActivity(addSeanceIntent);
                DayView_calendar.this.finish();
                break;
            case R.id.time_15:

                addSeanceIntent.putExtra("task", (Serializable)task );
                addSeanceIntent.putExtra("time", "15:00");
                DayView_calendar.this.startActivity(addSeanceIntent);
                DayView_calendar.this.finish();
                break;
            case R.id.time_16:

                addSeanceIntent.putExtra("task", (Serializable) task);
                addSeanceIntent.putExtra("time", "16:00");
                DayView_calendar.this.startActivity(addSeanceIntent);
                DayView_calendar.this.finish();
                break;
            case R.id.time_17:

                addSeanceIntent.putExtra("task", (Serializable)task);
                addSeanceIntent.putExtra("time", "17:00");
                DayView_calendar.this.startActivity(addSeanceIntent);
                DayView_calendar.this.finish();
                break;
            case R.id.time_18:

                addSeanceIntent.putExtra("task", (Serializable) task);
                addSeanceIntent.putExtra("time", "18:00");
                DayView_calendar.this.startActivity(addSeanceIntent);
                DayView_calendar.this.finish();
                break;
        }
    }
    public void getCalender(View view) {


        switch (view.getId())
        {
            case R.id.globalemploi:
             addingSeance(viewcalender);
              dialog.dismiss();
                break;

            case R.id.monitoremploi:
            addingTask(viewcalender);
                dialog.dismiss();
                break;

        }
    }
}