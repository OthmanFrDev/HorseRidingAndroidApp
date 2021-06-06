package com.example.horseriding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.time.LocalDateTime;

public class Add_Client extends AppCompatActivity {
    TextInputLayout nom,prenom,mail,tel;
    int nb=13;
    BottomNavigationView bnv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        nom=findViewById(R.id.inputnom);
        prenom=findViewById(R.id.inputprenom);
        mail=findViewById(R.id.inputemail);
        tel=findViewById(R.id.inputtel);

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
                        i = new Intent(Add_Client.this, DashboardActivity.class);
                        Add_Client.this.startActivity(i);
                        break;
                    case R.id.setting_nav:
                        i = new Intent(Add_Client.this, EditFormUser.class);
                        Add_Client.this.startActivity(i);
                        break;
                    case R.id.logout_nav:
                        SessionManager sessionManager = new SessionManager(Add_Client.this);
                        sessionManager.logout();
                        i = new Intent(Add_Client.this, LoginActivity.class);
                        Add_Client.this.startActivity(i);
                        Add_Client.this.finish();
                        break;
                }
                return true;
            }
        });
    }

    public void ajouterClient(View view) throws JSONException {
        JSONObject jsonBody = new JSONObject();
        //    jsonBody.put("userId", Integer.valueOf(userId.getText().toString()));

        String pwd=generateRandomPassword();
        StringBuilder msg=new StringBuilder();
        jsonBody.put("clientId", ++nb);
        jsonBody.put("sessionToken", "nulll");
        jsonBody.put("fName", prenom.getEditText().getText().toString());
        jsonBody.put("lName", nom.getEditText().getText().toString());
        jsonBody.put("birthDate", LocalDateTime.now().toString());
        jsonBody.put("identityDoc", "CINE");
        jsonBody.put("identityNumber", "AA000000");
        jsonBody.put("inscriptionDate", LocalDateTime.now().toString());
        jsonBody.put("ensurenceValidity", LocalDateTime.now().plusMonths(30).toString());
        jsonBody.put("licenceValidity", LocalDateTime.now().plusMonths(30).toString());
        jsonBody.put("clientEmail", mail.getEditText().getText().toString().trim());
        jsonBody.put("passwd", pwd);
        jsonBody.put("clientPhone", tel.getEditText().getText().toString());
        jsonBody.put("priceRate", 100);
        jsonBody.put("isActive", 1);
        jsonBody.put("notes", "null");
        Log.d("jsonbody", jsonBody.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, WS.URL+"clients", jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response
                        try {

                            msg.append("Bonjour Madame, Monsieur "+jsonBody.getString("fName")+" "+jsonBody.getString("lName")+" , \n");
                            msg.append("Vous pouvez maintenant télécharger l'application et vous connectez avec les informations ci-dessus.\n");
                            msg.append("Votre email :"+jsonBody.getString("clientEmail")+"\n");
                            msg.append("Votre mot de passe :"+jsonBody.getString("passwd")+"\n");
                            Toast.makeText(Add_Client.this, "Ajouter avec succes", Toast.LENGTH_LONG).show();
                            JavaMailAPI javaMailAPI=new JavaMailAPI(Add_Client.this,mail.getEditText().getText().toString().trim(),"Register Success",msg.toString());
                            javaMailAPI.execute();
                            Intent listUserIntet = new Intent(Add_Client.this, ListActivity.class);
                            listUserIntet.putExtra("click", "3");
                            Add_Client.this.startActivity(listUserIntet);
                            Add_Client.this.finish();
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
                                Toast.makeText(Add_Client.this, "Erreur: Informations incorrects", Toast.LENGTH_LONG).show();
                            }
                        } catch (NullPointerException ex) {
                            Toast.makeText(Add_Client.this, "Server issue try later", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
        MySingleton.getInstance(Add_Client.this).addToRequestQueue(jsonObjectRequest);
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
    public void onclickbtn(View view) {
        findViewById(R.id.floatingActionButtonweek).setVisibility(View.INVISIBLE);
        findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
    }

}