package com.example.horseriding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
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
    ListView listClient;
    String url = "http://192.168.111.1:45455/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //Setting ToolBar Back Button
        Toolbar toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public boolean onOptionsItemSelected(MenuItem item){

        Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(myIntent);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        listClient=findViewById(R.id.listUser);
        if(listClient!=null){

               getAllUsers();






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
                UserAdapter ua=new UserAdapter(ListActivity.this,users);
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
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url+"clients", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                TextView txtNom = findViewById(R.id.txtNom);
                TextView txtPrenom = findViewById(R.id.txtprenom);
                TextView txtMail = findViewById(R.id.userEmail);
                TextView txtPasswd = findViewById(R.id.userPasswd);
                TextView textView = (TextView) findViewById(R.id.text);
                try {
                    JSONObject j = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            j = response.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    Integer i = j.getInt("clientId");
                    txtNom.setText(j.getString("fName"));
                    txtPrenom.setText(j.getString("lName"));
                    txtMail.setText(j.getString("clientEmail"));
                    txtPasswd.setText(j.getString("passwd"));
                    textView.setText(i.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                TaskAdapter ua=new TaskAdapter(ListActivity.this,tasks);
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

                TextView txtNom = findViewById(R.id.txtNom);
                TextView txtPrenom = findViewById(R.id.txtprenom);
                TextView txtMail = findViewById(R.id.userEmail);
                TextView txtPasswd = findViewById(R.id.userPasswd);
                TextView textView = (TextView) findViewById(R.id.text);
                try {
                    JSONObject j = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            j = response.getJSONObject(i);
                            seances.add(new Seance(j.getInt("seanceId"),j.getInt("seanceGrpId"),
                                    j.getInt("clientId"),j.getInt("monitorId"),
                                    j.getInt("durationMinut"),j.getString("comments"))) ;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    SeanceAdapter ua=new SeanceAdapter(ListActivity.this,seances);
                    listClient.setAdapter(ua);
                    Integer i = j.getInt("clientId");
                    txtNom.setText(j.getString("fName"));
                    txtPrenom.setText(j.getString("lName"));
                    txtMail.setText(j.getString("clientEmail"));
                    txtPasswd.setText(j.getString("passwd"));
                    textView.setText(i.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
}