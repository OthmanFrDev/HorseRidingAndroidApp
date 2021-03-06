package com.example.horseriding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnConnect = findViewById(R.id.btnConnect);
        SessionManager sessionManager = new SessionManager(LoginActivity.this);
        if (sessionManager.chekingLogin()) {
            Intent splashIntent = new Intent(LoginActivity.this, DashboardActivity.class);
            LoginActivity.this.startActivity(splashIntent);
            LoginActivity.this.finish();
        }


    }


    public void clickhandler(View view) {
        EditText mailTxt, pswdTxt;
        mailTxt = findViewById(R.id.mailtxt);
        pswdTxt = findViewById(R.id.pswdtxt);
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, WS.URL + "users", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                JSONObject j = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        j = response.getJSONObject(i);


                        if (mailTxt.getText().toString().equals(j.getString("userEmail")) && pswdTxt.getText().toString().equals(j.getString("userPasswd")) && j.getString("userType").equals("ADMIN")) {

                            SessionManager sessionManager = new SessionManager(LoginActivity.this);
                            User user = new User(j.getInt("userId"), j.getString("userEmail"),
                                    j.getString("userPasswd"), j.getString("userFname"),
                                    j.getString("userLname"), j.getString("description"),
                                    j.getString("userType"), j.getString("userphoto"), j.getString("userPhone"), j.getString("lastLoginTime"), j.getString("displayColor"));
                            sessionManager.createLoginSession(user);

                            Intent splashIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                            LoginActivity.this.startActivity(splashIntent);
                            LoginActivity.this.finish();
                            break;
                        } else if (i == response.length() - 1) {
                            Toast.makeText(getApplicationContext(), "invalid email or password", Toast.LENGTH_LONG).show();
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
        }

        );


        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
    }

}