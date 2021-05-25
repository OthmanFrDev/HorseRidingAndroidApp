package com.example.horseriding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    RecyclerView listClient;
    String url = "http://192.168.111.1:45455/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_list);
        //Setting ToolBar Back Button
        Toolbar toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public boolean onOptionsItemSelected(MenuItem item){

        Intent myIntent = new Intent(getApplicationContext(),DashboardActivity.class);
        startActivity(myIntent);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        listClient=findViewById(R.id.listUser);
        if(listClient!=null){
            Intent intent=getIntent();
            if(intent.getStringExtra("click")!=null){
            if(intent.getStringExtra("click").equals("0")){getAllUsers();}
            if(intent.getStringExtra("click").equals("1")){getAllTasks();}
            if(intent.getStringExtra("click").equals("2")){getAllSeances();}
            if(intent.getStringExtra("click").equals("3")){getAllClients();}}

        }

    }
    public List<User> getAllUsers() {
        List<User>users=new ArrayList<>() ;

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url+"users", null, new Response.Listener<JSONArray>() {

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
                                j.getString("userType"), j.getString("userphoto"), j.getString("userPhone")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                UserAdapterRecycle ua=new UserAdapterRecycle(ListActivity.this,users);
                listClient.setLayoutManager(new LinearLayoutManager(ListActivity.this));
                listClient.setAdapter(ua);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(MainActivity.class.getSimpleName(), error.getMessage());
            }
        }

        );


        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
        return users;
    }
    void getAllClients() {
        List<Client>clients=new ArrayList<>() ;
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url+"clients", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//
//                TextView txtNom = findViewById(R.id.txtNom);
//                TextView txtPrenom = findViewById(R.id.txtprenom);
//                TextView txtMail = findViewById(R.id.userEmail);
//                TextView txtPasswd = findViewById(R.id.userPasswd);
//                TextView textView = (TextView) findViewById(R.id.text);
               // try {
                    JSONObject j = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            j = response.getJSONObject(i);
                            clients.add(new Client(j.getInt("clientId"), j.getString("fName"), j.getString("lName"), j.getString("photo"), j.getString("identityDoc"), j.getString("clientEmail"),  j.getString("passwd"), j.getString("clientPhone"), j.getString("notes")) );

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
//                    Integer i = j.getInt("clientId");
//                    txtNom.setText(j.getString("fName"));
//                    txtPrenom.setText(j.getString("lName"));
//                    txtMail.setText(j.getString("clientEmail"));
//                    txtPasswd.setText(j.getString("passwd"));
//                    textView.setText(i.toString());

//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                ClientAdapterRecycle ua=new ClientAdapterRecycle(ListActivity.this,clients);
                listClient.setLayoutManager(new LinearLayoutManager(ListActivity.this));
                listClient.setAdapter(ua);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(MainActivity.class.getSimpleName(), error.getMessage());
            }
        }

        );


        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
    }
    void getAllTasks() {
        List<Task>tasks=new ArrayList<>() ;
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url+"tasks", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                TextView txtNom = findViewById(R.id.txtNom);
                TextView txtPrenom = findViewById(R.id.txtprenom);
                TextView txtMail = findViewById(R.id.userEmail);
                TextView txtPasswd = findViewById(R.id.userPasswd);
                TextView textView = (TextView) findViewById(R.id.text);
                JSONObject j = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        j = response.getJSONObject(i);
                        tasks.add(new Task(j.getInt("taskId"),j.getInt("durationMinut"),
                                j.getInt("userFk"),j.getString("title"),j.getString("detail")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                TaskAdapterRecycle ua=new TaskAdapterRecycle(ListActivity.this,tasks);
                listClient.setLayoutManager(new LinearLayoutManager(ListActivity.this));
                listClient.setAdapter(ua);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(MainActivity.class.getSimpleName(), error.getMessage());
            }
        }

        );


        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
    }
    public List<Seance> getAllSeances() {
        List<Seance>seances=new ArrayList<>() ;
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url+"seances", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

//                TextView txtNom = findViewById(R.id.txtNom);
//                TextView txtPrenom = findViewById(R.id.txtprenom);
//                TextView txtMail = findViewById(R.id.userEmail);
//                TextView txtPasswd = findViewById(R.id.userPasswd);
//                TextView textView = (TextView) findViewById(R.id.text);
//                try {
                    JSONObject j = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            j = response.getJSONObject(i);
                            seances.add(new Seance(j.getInt("seanceId"),j.getInt("seanceGrpId"),
                                    j.getInt("clientId"),j.getInt("monitorId"),
                                    j.getInt("durationMinut"),j.getString("comments"),j.getString("startDate"))) ;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                SeanceAdapterRecycle ua=new SeanceAdapterRecycle(ListActivity.this,seances);
                listClient.setLayoutManager(new LinearLayoutManager(ListActivity.this));
                listClient.setAdapter(ua);
//                    Integer i = j.getInt("clientId");
//                    txtNom.setText(j.getString("fName"));
//                    txtPrenom.setText(j.getString("lName"));
//                    txtMail.setText(j.getString("clientEmail"));
//                    txtPasswd.setText(j.getString("passwd"));
//                    textView.setText(i.toString());
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(MainActivity.class.getSimpleName(), error.getMessage());
            }
        }

        );


        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
        return seances;
    }

    public void PostUser(View view) {
        Intent intent=getIntent();
        Intent addIntent = null;

        if(intent.getStringExtra("click").equals("0")){addIntent = new Intent(ListActivity.this,UserController.class); }
        if(intent.getStringExtra("click").equals("1")){addIntent = new Intent(ListActivity.this,UserController.class);/*task*/}
        if(intent.getStringExtra("click").equals("2")){addIntent = new Intent(ListActivity.this,RecycleCalendar.class);/*seance*/}
        if(intent.getStringExtra("click").equals("3")){addIntent = new Intent(ListActivity.this,UserController.class);/*client*/}

        ListActivity.this.startActivity(addIntent);
        ListActivity.this.finish();
    }
    public void getemplois(View view) {
        Intent intent=getIntent();
        TextView idclient= view.findViewById(R.id.userrolelist);
        TextView idMonitor= view.findViewById(R.id.nameuserlist);

        Intent emlpoisIntent = new Intent(ListActivity.this,WeekView_Calendar.class);
        switch (intent.getStringExtra("click"))
        {
            case "0":
                emlpoisIntent.putExtra("emploitype","1");
                emlpoisIntent.putExtra("id",idMonitor.getText().toString().split(" ")[0]);
                ListActivity.this.startActivity(emlpoisIntent);
                ListActivity.this.finish();
                break;
            case "3":

                emlpoisIntent.putExtra("emploitype","2");
                emlpoisIntent.putExtra("id",idclient.getText().toString());
                ListActivity.this.startActivity(emlpoisIntent);
                ListActivity.this.finish();
                break;
        }


    }
}