package com.example.horseriding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskController extends AppCompatActivity implements
        View.OnClickListener {

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Spinner monitor;
    Task task;

    Spinner clientid;


    private int rep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
        monitor = findViewById(R.id.monitor);
        clientid = findViewById(R.id.clientid);
        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        txtDate = (EditText) findViewById(R.id.in_date);
        txtTime = (EditText) findViewById(R.id.in_time);
        getmonitors();

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("task");
        txtDate.setText(task.getStartDate());
        txtTime.setText(intent.getStringExtra("time"));
        NumberPicker np = findViewById(R.id.numberPicker);

        np.setMinValue(0);
        np.setMaxValue(8);

        np.setOnValueChangedListener(onValueChangeListener);
        Toolbar toolbar = findViewById(R.id.toolBar);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        Intent myIntent = new Intent(getApplicationContext(), DayView_calendar.class);
        startActivity(myIntent);
        return true;
    }

    NumberPicker.OnValueChangeListener onValueChangeListener =
            new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                    rep = numberPicker.getValue();
                }
            };

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            if (monthOfYear + 1 >= 1 && monthOfYear + 1 <= 9) {
                                if (dayOfMonth >= 1 && dayOfMonth <= 9) {
                                    txtDate.setText(year + "-" + "0" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
                                } else
                                    txtDate.setText(year + "-" + "0" + (monthOfYear + 1) + "-" + dayOfMonth);
                            } else if (dayOfMonth >= 1 && dayOfMonth <= 9)
                                txtDate.setText(year + "-" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
                            else txtDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            if (hourOfDay >= 8 && hourOfDay <= 9) {
                                if (minute >= 0 && minute <= 9) {
                                    txtTime.setText("0" + hourOfDay + ":" + "0" + minute + ":00");
                                } else txtTime.setText("0" + hourOfDay + ":" + minute + ":00");
                            } else if (minute >= 0 && minute <= 9)
                                txtTime.setText(hourOfDay + ":" + "0" + minute + ":00");

                            else txtTime.setText(hourOfDay + ":" + minute + ":00");
                            if (hourOfDay >= 0 && hourOfDay <= 7 || hourOfDay > 18) {

                                Toast.makeText(TaskController.this, "work hours be between 00:08 to 18:00", Toast.LENGTH_LONG).show();
                                txtTime.setText(null);
                            }
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();

        }
    }

    public void PostSeance(View view) {


        monitor = findViewById(R.id.monitor);
        EditText title = findViewById(R.id.title);
        EditText detail = findViewById(R.id.detail);


        try {

            JSONObject jsonBody = new JSONObject();
            LocalDateTime localDateTime = LocalDateTime.parse(txtDate.getText().toString() + "T" + txtTime.getText().toString());
            for (int i = 0; i < rep; i++) {


                //    jsonBody.put("userId", Integer.valueOf(userId.getText().toString()));
                Task task= (Task) getIntent().getSerializableExtra("task");
              //  Client client = (Client) clientid.getSelectedItem();

                User user = (User) monitor.getSelectedItem();

                jsonBody.put("userFk", user.getUserId());
                jsonBody.put("startDate", localDateTime);
                jsonBody.put("durationMinut",task.getDurationMinut());
                jsonBody.put("title",title.getText().toString());
                jsonBody.put("detail",detail.getText().toString());
                jsonBody.put("isDone",task.getIsDone());
                localDateTime = localDateTime.plusDays(7);
                Log.d("jsonbody", jsonBody.toString());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        WS.URL + "tasks",
                        jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Do something with response
                                try {
                                    Toast.makeText(TaskController.this, "task ajouter", Toast.LENGTH_LONG).show();

                                    Intent splashIntent = new Intent(TaskController.this, DayView_calendar.class);
                                    TaskController.this.startActivity(splashIntent);
                                    TaskController.this.finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Do something when error occurred
                                try {
                                    if (error.networkResponse.statusCode == 400 || error.networkResponse.statusCode == 404) {
                                        Toast.makeText(TaskController.this, "Erreur: Informations incorrects", Toast.LENGTH_LONG).show();
                                    }
                                } catch (NullPointerException ex) {
                                    Toast.makeText(TaskController.this, "Server issue try later", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                );
                MySingleton.getInstance(TaskController.this).addToRequestQueue(jsonObjectRequest);

            }

        } catch (Exception e) {
            Toast.makeText(TaskController.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }

    }

    void getmonitors() {


        try {
            List<User> users = new ArrayList<>();

            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, WS.URL+"users", null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    Log.d("getallusers", "success");

                    JSONObject j = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            j = response.getJSONObject(i);
                            users.add(new User(j.getInt("userId"), j.getString("userEmail"),
                                    j.getString("userPasswd"), j.getString("userFname"),
                                    j.getString("userLname"), j.getString("description"),
                                    j.getString("userType"), j.getString("userphoto"), j.getString("userPhone"), j.getString("lastLoginTime"), j.getString("displayColor")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    ArrayAdapter<User> adapter = new ArrayAdapter<User>(TaskController.this,
                            R.layout.support_simple_spinner_dropdown_item, users);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    monitor.setAdapter(adapter);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                Log.d("ERROR", error.getMessage());
                            } catch (NullPointerException ex) {
                                Log.d("ERROR", ex.getMessage());
                            }
                        }
                    }
            );
            MySingleton.getInstance(TaskController.this).addToRequestQueue(req);
        } catch (Exception ex) {
            Log.d(getClass().getSimpleName(), ex.getMessage());
        }

    }

    void getclients() {


        try {
            List<Client> clients = new ArrayList<>();

            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, WS.URL+"clients", null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    Log.d("getallusers", "success");

                    JSONObject j = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            j = response.getJSONObject(i);
                            clients.add(new Client(j.getInt("clientId"), j.getString("fName"), j.getString("lName"), j.getString("photo"), j.getString("identityDoc"), j.getString("clientEmail"), j.getString("passwd"), j.getString("clientPhone"), j.getString("notes")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    ArrayAdapter<Client> adapter = new ArrayAdapter<Client>(TaskController.this,
                            R.layout.support_simple_spinner_dropdown_item, clients);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    clientid.setAdapter(adapter);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                Log.d("ERROR", error.getMessage());
                            } catch (NullPointerException ex) {
                                Log.d("ERROR", ex.getMessage());
                            }
                        }
                    }
            );
            MySingleton.getInstance(TaskController.this).addToRequestQueue(req);
        } catch (Exception ex) {
            Log.d(getClass().getSimpleName(), ex.getMessage());
        }

    }
}
