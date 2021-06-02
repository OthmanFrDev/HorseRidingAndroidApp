package com.example.horseriding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClientList extends AppCompatActivity {
    RecyclerView listClient;
    EditText sInput;
    ClientAdapterRecycle ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);
        listClient=findViewById(R.id.listClient);
        sInput=findViewById(R.id.searchinput);

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Client> clients=new ArrayList<>() ;
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, WS.URL+"clients", null, new Response.Listener<JSONArray>() {
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


                ca=new ClientAdapterRecycle(ClientList.this,clients);
                listClient.setLayoutManager(new LinearLayoutManager(ClientList.this));
                listClient.setAdapter(ca);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }

        );


        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
        sInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //ua.getFilter().filter(s);
                    ca.getFilter().filter(s.toString());
                // sa.getFilter().filter(s);
                //ta.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

}