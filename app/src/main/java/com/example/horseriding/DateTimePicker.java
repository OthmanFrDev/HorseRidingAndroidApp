package com.example.horseriding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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

public class DateTimePicker extends AppCompatActivity implements
        View.OnClickListener {

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Spinner monitor;
    Seance seance;

    Spinner clientid;
    private String url="http://192.168.111.1:45455/seances";

    private int rep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.seance);
         monitor=findViewById(R.id.monitor);
         clientid = findViewById(R.id.clientid);
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);
        getmonitors();
        getclients();
        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        Intent intent = getIntent();
        seance= (Seance) intent.getSerializableExtra("seance");
        txtDate.setText(seance.getStartDate());
        txtTime.setText(intent.getStringExtra("time"));
        NumberPicker np = findViewById(R.id.numberPicker);

        np.setMinValue(1);
        np.setMaxValue(8);

        np.setOnValueChangedListener(onValueChangeListener);

    }
    NumberPicker.OnValueChangeListener onValueChangeListener =
            new 	NumberPicker.OnValueChangeListener(){
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                    rep=numberPicker.getValue();
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
if(monthOfYear+1>=1&&monthOfYear+1<=9){
    if(dayOfMonth>=1&&dayOfMonth<=9){txtDate.setText(year + "-" + "0"+(monthOfYear + 1) + "-" +"0"+ dayOfMonth);}
    else  txtDate.setText(year + "-" + "0"+(monthOfYear + 1) + "-" + dayOfMonth);
}
else if(dayOfMonth>=1&&dayOfMonth<=9)txtDate.setText(year + "-" + (monthOfYear + 1) + "-" +"0"+ dayOfMonth);
     else   txtDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

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
if(hourOfDay>=8&&hourOfDay<=9)
{
    if(minute>=0&&minute<=9){txtTime.setText("0"+hourOfDay + ":" + "0"+minute+":00");}
    else txtTime.setText("0"+hourOfDay + ":" + minute+":00");
}
else if(minute>=0&&minute<=9) txtTime.setText(hourOfDay + ":" +"0"+ minute+":00");

else  txtTime.setText(hourOfDay + ":" + minute+":00");
                            if(hourOfDay>=0&&hourOfDay<=7||hourOfDay>18) {

                                Toast.makeText(DateTimePicker.this, "work hours be between 00:08 to 18:00", Toast.LENGTH_LONG).show();
                                txtTime.setText(null);
                            }
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();

        }
    }

    public void PostSeance(View view) {



         monitor = findViewById(R.id.monitor);
        EditText duration = findViewById(R.id.duration);
        EditText comment = findViewById(R.id.comment);




        try {

            JSONObject jsonBody = new JSONObject();
            LocalDateTime localDateTime = LocalDateTime.parse(txtDate.getText().toString() + "T" + txtTime.getText().toString());
            for (int i = 0; i < rep; i++){



            //    jsonBody.put("userId", Integer.valueOf(userId.getText().toString()));
            jsonBody.put("seanceGrpId", 1);
            Client client= (Client) clientid.getSelectedItem();
            jsonBody.put("clientId", Integer.valueOf(client.getClientId()));
            User user = (User) monitor.getSelectedItem();
            jsonBody.put("monitorId", Integer.valueOf(user.getUserId()));
            jsonBody.put("startDate", localDateTime);
            jsonBody.put("durationMinut", Integer.valueOf(duration.getText().toString()));
            jsonBody.put("isDone", 1);
            jsonBody.put("paymentId", 1);
            jsonBody.put("comments", comment.getText().toString());
                localDateTime = localDateTime.plusDays(7);
            Log.d("jsonbody", jsonBody.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    WS.URL+"seances",
                    jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Do something with response
                            try {
                                Toast.makeText(DateTimePicker.this, "Bien modifié", Toast.LENGTH_LONG).show();

                                Intent splashIntent = new Intent(DateTimePicker.this, RecycleCalendar.class);
                                DateTimePicker.this.startActivity(splashIntent);
                                DateTimePicker.this.finish();
                            } catch (Exception e) {
                                Log.d("wsrong", e.getMessage());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Do something when error occurred
                            try {
                                if (error.networkResponse.statusCode == 400 || error.networkResponse.statusCode == 404) {
                                    Toast.makeText(DateTimePicker.this, "Erreur: Informations incorrects", Toast.LENGTH_LONG).show();
                                }
                                Log.e(MainActivity.class.getSimpleName(), error.toString());
                            } catch (NullPointerException ex) {
                                Toast.makeText(DateTimePicker.this, "Server issue try later", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            );
            MySingleton.getInstance(DateTimePicker.this).addToRequestQueue(jsonObjectRequest);

        }

        } catch (Exception e) {
            Toast.makeText(DateTimePicker.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("wsrong", e.getMessage());
        }

    }

    void getmonitors() {


        try {
            List<User>users=new ArrayList<>() ;

            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, "http://192.168.111.1:45455/users", null, new Response.Listener<JSONArray>() {

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
                                    j.getString("userType"), j.getString("userphoto"), j.getString("userPhone"),j.getString("lastLoginTime"),j.getString("displayColor")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    ArrayAdapter<User> adapter=new ArrayAdapter<User>(DateTimePicker.this,
                            R.layout.support_simple_spinner_dropdown_item,users);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    monitor.setAdapter(adapter);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                Log.d("wsrong", error.getMessage());
                            } catch (NullPointerException ex) {
                                Log.d("wsrong", ex.getMessage());
                            }
                        }
                    }
            );
            MySingleton.getInstance(DateTimePicker.this).addToRequestQueue(req);
        } catch (Exception ex) {
            Log.d(getClass().getSimpleName(), ex.getMessage());
        }

    }
    void getclients() {


        try {
            List<Client>clients=new ArrayList<>() ;

            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, "http://192.168.111.1:45455/clients", null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    Log.d("getallusers", "success");

                    JSONObject j = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            j = response.getJSONObject(i);
                            clients.add(new Client(j.getInt("clientId"), j.getString("fName"), j.getString("lName"), j.getString("photo"), j.getString("identityDoc"), j.getString("clientEmail"),  j.getString("passwd"), j.getString("clientPhone"), j.getString("notes")) );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    ArrayAdapter<Client> adapter=new ArrayAdapter<Client>(DateTimePicker.this,
                            R.layout.support_simple_spinner_dropdown_item,clients);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    clientid.setAdapter(adapter);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                Log.d("wsrong", error.getMessage());
                            } catch (NullPointerException ex) {
                                Log.d("wsrong", ex.getMessage());
                            }
                        }
                    }
            );
            MySingleton.getInstance(DateTimePicker.this).addToRequestQueue(req);
        } catch (Exception ex) {
            Log.d(getClass().getSimpleName(), ex.getMessage());
        }

    }
}
