package com.example.horseriding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    RecyclerView listClient;

    int orientation;

    EditText sInput;
    List<User> users = new ArrayList<>();
    List<Task> tasks = new ArrayList<>();
    List<Seance> seances = new ArrayList<>();
    private UserAdapterRecycle.RecycleViewClickListner listener;
    Toolbar toolbar;
    UserAdapterRecycle ua;
    TaskAdapterRecycle ta;
    ClientAdapterRecycle ca;

    SeanceAdapterRecycle sa;

    Dialog dialog;
    private BottomNavigationView bnv;
    Intent intent;
    LinearLayout container;
    ConstraintLayout containerPor;
    TextView userShape;
    TextView userName;
    EditText mailinput;
    EditText telinput;
    ImageButton callbtn;
    ImageButton editbtn;
    ImageButton calendarbtn;
    Button dltBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_list);
        setTitle("Liste des utilisateurs");
        //Setting ToolBar Back Button

        toolbar = findViewById(R.id.toolBar);
        dialog = new Dialog(this);
        container = findViewById(R.id.container);
        containerPor = findViewById(R.id.containerPor);
        intent = getIntent();
        userShape = findViewById(R.id.shapeuser);
        userName = findViewById(R.id.nameuser);
        mailinput = findViewById(R.id.emailinput);
        telinput = findViewById(R.id.telephoneinput);
        callbtn = findViewById(R.id.callbtn);
        editbtn = findViewById(R.id.editbtn);
        calendarbtn = findViewById(R.id.calendarbtn);
        dltBtn = findViewById(R.id.dltBtn);
        orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        bnv = findViewById(R.id.bottom_navigation);
        if (getIntent().getStringExtra("emploi") != null) {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                containerPor.removeView(bnv);
            } else {
                ConstraintLayout cl = findViewById(R.id.details);
                container.removeView(cl);
            }
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
                            i = new Intent(ListActivity.this, DashboardActivity.class);
                            startActivity(i);
                            finish();
                            break;
                        case R.id.setting_nav:
                            i = new Intent(ListActivity.this, EditFormUser.class);
                            ListActivity.this.startActivity(i);
                            break;
                        case R.id.logout_nav:
                            SessionManager sessionManager = new SessionManager(ListActivity.this);
                            sessionManager.logout();
                            Intent splashIntent = new Intent(ListActivity.this, LoginActivity.class);
                            ListActivity.this.startActivity(splashIntent);
                            ListActivity.this.finish();
                            break;
                    }
                    return true;
                }
            });
        } else {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                containerPor.removeView(bnv);
            } else {
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
                                i = new Intent(ListActivity.this, DashboardActivity.class);
                                startActivity(i);
                                finish();
                                break;
                            case R.id.setting_nav:
                                i = new Intent(ListActivity.this, EditFormUser.class);
                                ListActivity.this.startActivity(i);
                                break;
                            case R.id.logout_nav:
                                SessionManager sessionManager = new SessionManager(ListActivity.this);
                                sessionManager.logout();
                                Intent splashIntent = new Intent(ListActivity.this, LoginActivity.class);
                                ListActivity.this.startActivity(splashIntent);
                                ListActivity.this.finish();
                                break;
                        }
                        return true;
                    }
                });
            }

        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        Intent myIntent = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(myIntent);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        listClient = findViewById(R.id.listUser);
        sInput = findViewById(R.id.searchinput);
        if (listClient != null) {
            if (intent.getStringExtra("click") != null) {
                if (intent.getStringExtra("click").equals("0")) {
                    getAllUsers();
                }
//            if(intent.getStringExtra("click").equals("1")){getAllTasks();}
//            if(intent.getStringExtra("click").equals("2")){getAllSeances();}
                if (intent.getStringExtra("click").equals("3")) {

                    int orientation = getResources().getConfiguration().orientation;
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        ConstraintLayout cl = findViewById(R.id.details);
                        container.removeView(cl);
                    }
                    getAllClients();
                }

            } else if (intent.getSerializableExtra("seance") != null) {
                Seance response = (Seance) intent.getSerializableExtra("seance");
                Log.d("response", response.getStartDate());

            }
        }
        sInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                switch (Integer.parseInt(getIntent().getStringExtra("click"))) {
                    case 0:
                        ua.getFilter().filter(s);
                        break;
                    case 3:
                        ca.getFilter().filter(s);
                        break;
                }
                // sa.getFilter().filter(s);
                //ta.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    public List<User> getAllUsers() {
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, WS.URL + "users", null, new Response.Listener<JSONArray>() {

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
                setOnclickListner();
                ua = new UserAdapterRecycle(ListActivity.this, users, listener);
                listClient.setLayoutManager(new LinearLayoutManager(ListActivity.this));
                listClient.setAdapter(ua);

                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    afficherUserDetails(0);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(MainActivity.class.getSimpleName(), error.getMessage());
            }
        }
        );

        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
        return users;
    }

    private void setOnclickListner() {
        listener = new UserAdapterRecycle.RecycleViewClickListner() {
            @Override
            public void onClickItem(View v, int position) {
                if (getIntent().getStringExtra("emploi") == null) {
                    afficherUserDetails(position);
                } else {
                    TextView idclient = v.findViewById(R.id.userrolelist);
                    TextView idMonitor = v.findViewById(R.id.nameuserlist);

                    Intent emlpoisIntent = new Intent(ListActivity.this, WeekView_Calendar.class);
                    emlpoisIntent.putExtra("emploitype", "1");
                    emlpoisIntent.putExtra("id", idMonitor.getText().toString().split(" ")[0]);
                    ListActivity.this.startActivity(emlpoisIntent);
                    ListActivity.this.finish();
                }
            }

        };
    }

    void getAllClients() {
        List<Client> clients = new ArrayList<>();
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, WS.URL + "clients", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject j = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        j = response.getJSONObject(i);
                        clients.add(new Client(j.getInt("clientId"), j.getString("fName"), j.getString("lName"), j.getString("photo"), j.getString("identityDoc"), j.getString("clientEmail"), j.getString("passwd"), j.getString("clientPhone"), j.getString("notes")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                ca = new ClientAdapterRecycle(ListActivity.this, clients);
                listClient.setLayoutManager(new LinearLayoutManager(ListActivity.this));
                listClient.setAdapter(ca);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }

        );
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
    }


    public List<Seance> getAllSeances() {
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, WS.URL + "seances", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject j = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        j = response.getJSONObject(i);
                        seances.add(new Seance(j.getInt("seanceId"), j.getInt("seanceGrpId"),
                                j.getInt("clientId"), j.getInt("monitorId"),
                                j.getInt("durationMinut"), j.getString("comments"), j.getString("startDate")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                sa = new SeanceAdapterRecycle(ListActivity.this, seances);

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
        Intent intent = getIntent();
        Intent addIntent = null;

        if (intent.getStringExtra("click").equals("0")) {
            addIntent = new Intent(ListActivity.this, UserController.class);
        }
        if (intent.getStringExtra("click").equals("1")) {
            addIntent = new Intent(ListActivity.this, UserController.class);/*task*/
        }
        if (intent.getStringExtra("click").equals("2")) {
            addIntent = new Intent(ListActivity.this, DayView_calendar.class);/*seance*/
        }
        if (intent.getStringExtra("click").equals("3")) {
            addIntent = new Intent(ListActivity.this, Add_Client.class);/*client*/
        }

        ListActivity.this.startActivity(addIntent);
        ListActivity.this.finish();
    }

    public void getemplois(View view) {
        Intent intent = getIntent();
        TextView idclient = view.findViewById(R.id.userrolelist);
        TextView idMonitor = view.findViewById(R.id.nameuserlist);

        Intent emlpoisIntent = new Intent(ListActivity.this, WeekView_Calendar.class);
        switch (intent.getStringExtra("click")) {
            case "0":
                emlpoisIntent.putExtra("emploitype", "1");
                emlpoisIntent.putExtra("id", idMonitor.getText().toString().split(" ")[0]);
                ListActivity.this.startActivity(emlpoisIntent);
                ListActivity.this.finish();
                break;
            case "3":

                emlpoisIntent.putExtra("emploitype", "2");
                emlpoisIntent.putExtra("id", idclient.getText().toString());
                ListActivity.this.startActivity(emlpoisIntent);
                ListActivity.this.finish();
                break;
        }


    }

    public void onclickbtn(View view) {
        findViewById(R.id.floatingActionButtonweek).setVisibility(View.INVISIBLE);
        findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
    }

    private void afficherUserDetails(int position) {
        User u = new User();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            dialog.setContentView(R.layout.activity_dialog_user_details);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            userShape = dialog.findViewById(R.id.shapeuser);
            userName = dialog.findViewById(R.id.nameuser);
            mailinput = dialog.findViewById(R.id.emailinput);
            telinput = dialog.findViewById(R.id.telephoneinput);
            callbtn = dialog.findViewById(R.id.callbtn);
            editbtn = dialog.findViewById(R.id.editbtn);
            calendarbtn = dialog.findViewById(R.id.calendarbtn);
            dltBtn = dialog.findViewById(R.id.dltBtn);
        }
        u = users.get(position);
        userShape.setText(u.getUserFname().toUpperCase().substring(0, 1) + "" + u.getUserLname().toUpperCase().substring(0, 1));
        userShape.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 50);
        userShape.setTextColor(Color.parseColor("#ffffff"));
        userShape.setPadding(5, 7, 5, 5);
        userName.setText(u.getUserFname() + " " + u.getUserLname());
        mailinput.setText(u.getUserEmail());
        mailinput.setTextSize(15);
        telinput.setText(u.getUserPhone());
        telinput.setTextSize(15);
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u = users.get(position);
                Intent iDial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0" + u.getUserPhone().substring(4, 13)));
                startActivity(iDial);
                dialog.dismiss();
            }
        });
        calendarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u = users.get(position);
                Intent i = new Intent(ListActivity.this, WeekView_Calendar.class);
                String id = String.valueOf(u.getUserId());
                i.putExtra("id", id);
                i.putExtra("emploitype", "1");
                startActivity(i);
                finish();
                dialog.dismiss();
            }
        });
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u = users.get(position);
                Intent i = new Intent(ListActivity.this, EditFormUser.class);
                i.putExtra("user", u);
                startActivity(i);
                finish();
                dialog.dismiss();
            }
        });
        dltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u = users.get(position);
                StringRequest dr = new StringRequest(Request.Method.DELETE, WS.URL + "users/" + u.getUserId(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String res) {
                                // response
                                startActivity(getIntent());
                                Toast.makeText(ListActivity.this, "Deleted", Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error.

                            }
                        }
                );
                MySingleton.getInstance(ListActivity.this).addToRequestQueue(dr);
            }
        });
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            dialog.show();
        }
    }
}