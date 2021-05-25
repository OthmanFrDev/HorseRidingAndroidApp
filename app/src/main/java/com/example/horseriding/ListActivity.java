package com.example.horseriding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    EditText sInput;
    List<User>users=new ArrayList<>() ;
    List<Task>tasks=new ArrayList<>() ;
    List<Seance>seances=new ArrayList<>() ;
    private UserAdapterRecycle.RecycleViewClickListner listener;

    UserAdapterRecycle ua;
    TaskAdapterRecycle ta;
    SeanceAdapterRecycle sa=new SeanceAdapterRecycle(ListActivity.this,seances);
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_list);
        //Setting ToolBar Back Button
        Toolbar toolbar=findViewById(R.id.toolBar);
        dialog=new Dialog(this);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);}
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
        sInput=findViewById(R.id.searchinput);
        if(listClient!=null){
            Intent intent=getIntent();
            if(intent.getStringExtra("click")!=null){
            if(intent.getStringExtra("click").equals("0")){getAllUsers();}
            if(intent.getStringExtra("click").equals("1")){getAllTasks();}
            if(intent.getStringExtra("click").equals("2")){getAllSeances();}
            if(intent.getStringExtra("click").equals("3")){getAllClients();}}

        }
        sInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ua.getFilter().filter(s);
                sa.getFilter().filter(s);
                ta.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public List<User> getAllUsers() {
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
                                j.getString("userType"), j.getString("userphoto"), j.getString("userPhone"),j.getString("lastLoginTime"),j.getString("displayColor")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                setOnclickListner();
                ua=new UserAdapterRecycle(ListActivity.this,users,listener);
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

    private void setOnclickListner() {
        listener=new UserAdapterRecycle.RecycleViewClickListner() {
            @Override
            public void onClickItem(View v, int position) {
                Toast.makeText(ListActivity.this,""+users.get(position).getUserEmail(),Toast.LENGTH_LONG).show();
                TextView userShape=findViewById(R.id.shapeuser);
                TextView userName=findViewById(R.id.nameuser);
                EditText mailinput=findViewById(R.id.emailinput);
                EditText telinput=findViewById(R.id.telephoneinput);
                ImageButton callbtn=findViewById(R.id.callbtn);
                ImageButton editbtn=findViewById(R.id.editbtn);
                ImageButton calendarbtn=findViewById(R.id.calendarbtn);
                User u=new User();
                u=users.get(position);
                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    dialog.setContentView(R.layout.activity_dialog_user_details);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    userShape=dialog.findViewById(R.id.shapeuser);
                    userName=dialog.findViewById(R.id.nameuser);
                    mailinput=dialog.findViewById(R.id.emailinput);
                    telinput=dialog.findViewById(R.id.telephoneinput);
                    callbtn=dialog.findViewById(R.id.callbtn);
                    editbtn=dialog.findViewById(R.id.editbtn);
                    calendarbtn=dialog.findViewById(R.id.calendarbtn);
                }
                    userShape.setText(u.getUserFname().toUpperCase().substring(0,1)+""+u.getUserLname().toUpperCase().substring(0,1));
                    userShape.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 50);
                    userShape.setTextColor(Color.parseColor("#ffffff"));
                    userShape.setPadding(5,7,5,5);
                    userName.setText(u.getUserFname()+" "+u.getUserLname());
                    mailinput.setText(u.getUserEmail());
                    mailinput.setTextSize(15);
                    telinput.setText(u.getUserPhone());
                    telinput.setTextSize(15);
                    callbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            User u=users.get(position);
                            Intent iDial=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0"+u.getUserPhone().substring(4,13)));
                            startActivity(iDial);
                        }
                    });
                    calendarbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            User u=users.get(position);
                            Intent i=new Intent(ListActivity.this,WeekView_Calendar.class);
                            i.putExtra("id",u.getUserId()+"");
                            i.putExtra("emploitype","1");
                            startActivity(i);
                            dialog.dismiss();
                            finish();
                        }
                    });
                    editbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            User u=users.get(position);
                            Intent i=new Intent(ListActivity.this,EditFormUser.class);
                            i.putExtra("user",u);
                            startActivity(i);
                            dialog.dismiss();
                            finish();
                        }
                    });
                    dialog.show();
            }
        };
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

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url+"tasks", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


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
                ta=new TaskAdapterRecycle(ListActivity.this,tasks);
                listClient.setLayoutManager(new LinearLayoutManager(ListActivity.this));
                listClient.setAdapter(ta);


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
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url+"seances", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

 /*               TextView txtNom = findViewById(R.id.txtNom);
                TextView txtPrenom = findViewById(R.id.txtprenom);
                TextView txtMail = findViewById(R.id.userEmail);
                TextView txtPasswd = findViewById(R.id.userPasswd);
                TextView textView = (TextView) findViewById(R.id.text);
                try {*/
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
                listClient.setLayoutManager(new LinearLayoutManager(ListActivity.this));
                    listClient.setAdapter(sa);
                    /*Integer i = j.getInt("clientId");
                    txtNom.setText(j.getString("fName"));
                    txtPrenom.setText(j.getString("lName"));
                    txtMail.setText(j.getString("clientEmail"));
                    txtPasswd.setText(j.getString("passwd"));
                    textView.setText(i.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
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