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
    String url = "http://192.168.1.7:45455/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnConnect= findViewById(R.id.btnConnect);


    }

    void login() {
        User user = new User(1, "dd", "00", "ayab", "dhada", "dsada", "admin", "ds", "299595");
        sharedpreferences = getSharedPreferences("UserInfos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("nomUtilisateur", user.getUserFname());
        editor.putString("emailUtilisateur", user.getUserEmail());
        editor.putString("prenomUtilisateur", user.getUserLname());
        editor.commit();


    }

    public void clickhandler(View view) {
        EditText mailTxt,pswdTxt;
        mailTxt=findViewById(R.id.mailtxt);
        pswdTxt=findViewById(R.id.pswdtxt);
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {



        Log.d("response","okeeey");
                JSONObject j = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        j = response.getJSONObject(i);


                                if (mailTxt.getText().toString().equals(j.getString("userEmail")) && pswdTxt.getText().toString().equals(j.getString("userPasswd"))&&j.getString("userType").equals("ADMIN")) {
                                    Log.d("login", "success");
                                    SessionManager sessionManager=new SessionManager(LoginActivity.this);
                                    User user = new User(j.getInt("userId"), j.getString("userEmail"),
                                            j.getString("userPasswd"), j.getString("userFname"),
                                            j.getString("userLname"), j.getString("description"),
                                            j.getString("userType"), j.getString("userphoto"), j.getString("userPhone"));
                                    sessionManager.createLoginSession(user);

                                    Intent splashIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                                    LoginActivity.this.startActivity(splashIntent);
                                    LoginActivity.this.finish();
                                    break;
                                } else if(i==response.length()-1){
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
                Log.e(MainActivity.class.getSimpleName(), error.getMessage());
            }
        }

        );



        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
    }

}