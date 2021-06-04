package com.example.horseriding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserController extends AppCompatActivity {
    Spinner userType;
    TextInputLayout nom, prenom, mail, tel, lastmdp, desc;
    TextView label;
    BottomNavigationView bnv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form_user);
        userType = findViewById(R.id.userspinner);
        //Setting ToolBar Back Button
        getType();
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        nom = findViewById(R.id.inputnom);
        prenom = findViewById(R.id.inputprenom);
        mail = findViewById(R.id.inputemail);
        tel = findViewById(R.id.inputtel);
        lastmdp = findViewById(R.id.inputlastpass);
        //desc=findViewById(R.id.inputdescription);
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
                        i = new Intent(UserController.this, DashboardActivity.class);
                        UserController.this.startActivity(i);
                        break;
                    case R.id.setting_nav:
                        i = new Intent(UserController.this, EditFormUser.class);
                        UserController.this.startActivity(i);
                        break;
                    case R.id.logout_nav:
                        SessionManager sessionManager = new SessionManager(UserController.this);
                        sessionManager.logout();
                        i = new Intent(UserController.this, LoginActivity.class);
                        UserController.this.startActivity(i);
                        UserController.this.finish();
                        break;
                }
                return true;
            }
        });
    }

    public void onclickbtn(View view) {
        findViewById(R.id.floatingActionButtonweek).setVisibility(View.INVISIBLE);
        findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), ListActivity.class);
        myIntent.putExtra("click", "0");
        startActivity(myIntent);
        return true;
    }

    public void PostUser(View view) {
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");
        try {
            String type = (String) userType.getSelectedItem();
            int adminLevel = 0;
            switch (type) {
                case "ADMIN":
                    adminLevel = 100;
                    break;
                case "MONITOR":
                    adminLevel = 0;
                    break;
            }
            JSONObject jsonBody = new JSONObject();
            //    jsonBody.put("userId", Integer.valueOf(userId.getText().toString()));
            String pwd=generateRandomPassword();
            StringBuilder msg=new StringBuilder();
            jsonBody.put("userEmail", mail.getEditText().getText().toString().trim());
            jsonBody.put("userPasswd", pwd);
            jsonBody.put("adminLevel", adminLevel);
            jsonBody.put("lastLoginTime", LocalDateTime.now().toString());
            jsonBody.put("isActive", 1);
            jsonBody.put("userFname", prenom.getEditText().getText());
            jsonBody.put("userLname", nom.getEditText().getText());
            jsonBody.put("description", /*desc.getEditText().getText()*/"no description");

            jsonBody.put("userType", type);
            jsonBody.put("userphoto", "default.png");
            jsonBody.put("contractDate", LocalDateTime.now().toString());
            jsonBody.put("userPhone", tel.getEditText().getText());
            jsonBody.put("displayColor", "#0000FF");
            Log.d("jsonbody", jsonBody.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, WS.URL+"users", jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Do something with response
                            try {

                                msg.append("Bonjour Madame, Monsieur "+jsonBody.getString("userFname")+" "+jsonBody.getString("userLname")+" , \n");
                                msg.append("Vous pouvez maintenant télécharger l'application et vous connectez avec les informations ci-dessus.\n");
                                msg.append("Votre email :"+jsonBody.getString("userEmail")+"\n");
                                msg.append("Votre mot de passe :"+jsonBody.getString("userPasswd")+"\n");
                                Toast.makeText(UserController.this, "Ajouter", Toast.LENGTH_LONG).show();
                                JavaMailAPI javaMailAPI=new JavaMailAPI(UserController.this,mail.getEditText().getText().toString().trim(),"Register Success",msg.toString());
                                javaMailAPI.execute();
                                Intent listUserIntet = new Intent(UserController.this, ListActivity.class);
                                listUserIntet.putExtra("click", "0");
                                UserController.this.startActivity(listUserIntet);
                                UserController.this.finish();
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
                                    Toast.makeText(UserController.this, "Erreur: Informations incorrects", Toast.LENGTH_LONG).show();
                                }
                            } catch (NullPointerException ex) {
                                Toast.makeText(UserController.this, "Server issue try later", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            );
            MySingleton.getInstance(UserController.this).addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {
            Toast.makeText(UserController.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("wsrong", e.getMessage());
        }

    }

    void getType() {
        List<String> types = new ArrayList<>();
        types.add("ADMIN");
        types.add("MONITOR");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserController.this, R.layout.support_simple_spinner_dropdown_item, types);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        userType.setAdapter(adapter);
    }

    public String generateRandomPassword() {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }

}