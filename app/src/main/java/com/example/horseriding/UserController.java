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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserController extends AppCompatActivity {
    String url = "http://192.168.111.1:45455/users";
    Spinner userType ;
    TextInputLayout nom,prenom,mail,tel,lastmdp,desc;


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
        nom=findViewById(R.id.inputnom);
        prenom=findViewById(R.id.inputprenom);
        mail=findViewById(R.id.inputemail);
        tel=findViewById(R.id.inputtel);
        lastmdp=findViewById(R.id.inputlastpass);
        desc=findViewById(R.id.inputdescription);
//        User user = (User) intent.getSerializableExtra("user");
//        EditText userId = findViewById(R.id.editTextTextPersonName);
//        EditText lastLoginTime = findViewById(R.id.editTextTextPersonName2);
//        EditText userEmail = findViewById(R.id.editTextTextPersonName3);
//        EditText userPasswd = findViewById(R.id.editTextTextPersonName4);
//        EditText adminLevel = findViewById(R.id.editTextTextPersonName5);
//        EditText isActive = findViewById(R.id.editTextTextPersonName6);
//        EditText userFname = findViewById(R.id.editTextTextPersonName7);
//        EditText userLname = findViewById(R.id.editTextTextPersonName8);
//        EditText description = findViewById(R.id.editTextTextPersonName9);
//        EditText userType = findViewById(R.id.editTextTextPersonName10);
//        EditText userphoto = findViewById(R.id.editTextTextPersonName11);
//        EditText contractDate = findViewById(R.id.editTextTextPersonName12);
//        EditText userPhone = findViewById(R.id.editTextTextPersonName13);
//        EditText displayColor = findViewById(R.id.editTextTextPersonName14);
//        Button button = findViewById(R.id.button);

//        userId.setText(String.valueOf(user.getUserId()));
//        userEmail.setText(user.getUserEmail());
//        userFname.setText(user.getUserFname());
//        userLname.setText(user.getUserLname());
//        userPasswd.setText(user.getUserPasswd());
//        userPhone.setText(user.getUserPhone());
//        userType.setText(user.getUserType());
//        description.setText(user.getDescription());
//        userphoto.setText(user.getUserphoto());


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), ListActivity.class);
        myIntent.putExtra("click","0");
        startActivity(myIntent);
        return true;
    }

    public void PostUser(View view) {
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");

//        userId.setText(user.getUserId());
//        userEmail.setText(user.getUserEmail());
//        userFname.setText(user.getUserFname());
//        userLname.setText(user.getUserLname());
//        userPasswd.setText(user.getUserPasswd());
//        userPhone.setText(user.getUserPhone());
//        userType.setText(user.getUserType());
//        description.setText(user.getDescription());
//        userPhone.setText(user.getUserphoto());


//        Task task =new Task();
//        task.setTaskId(Integer.valueOf(taskId.getText().toString()) );
//        task.setStartDate(startDate.getText().toString());
//        task.setDurationMinut(Integer.valueOf(durationMinut.getText().toString()) );
//        task.setTitle(title.getText().toString());
//        task.setDetail(detail.getText().toString());
//        task.setIsDone(isDone.getText().toString() );
//        task.setUserFk(Integer.valueOf(userFk.getText().toString()) );
//        Log.d("task",task.toString());
        try {

            String type= (String) userType.getSelectedItem();
            int adminLevel=0;
            switch (type)
            {
                case "ADMIN":adminLevel=100; break;
                case "MONITOR":adminLevel=0;break;
            }
            JSONObject jsonBody = new JSONObject();
           //    jsonBody.put("userId", Integer.valueOf(userId.getText().toString()));
            jsonBody.put("userEmail", mail.getEditText().getText().toString());
            jsonBody.put("userPasswd", lastmdp.getEditText().getText().toString());
            jsonBody.put("adminLevel", adminLevel);
            jsonBody.put("lastLoginTime", LocalDateTime.now().toString());
            jsonBody.put("isActive", 1);
            jsonBody.put("userFname", prenom.getEditText().getText());
            jsonBody.put("userLname", nom.getEditText().getText());
            jsonBody.put("description", desc.getEditText().getText());

            jsonBody.put("userType", type);
            jsonBody.put("userphoto", "default.png");
            jsonBody.put("contractDate", LocalDateTime.now().toString());
            jsonBody.put("userPhone", tel.getEditText().getText());
            jsonBody.put("displayColor", "#0000FF");
            Log.d("jsonbody", jsonBody.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                    url ,
                    jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Do something with response
                            try {
                                Toast.makeText(UserController.this, "Ajouter", Toast.LENGTH_LONG).show();
                                Intent listUserIntet = new Intent(UserController.this, ListActivity.class);
                                listUserIntet.putExtra("click","0");
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
                                Log.e(MainActivity.class.getSimpleName(), error.toString());
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
      List<String> types=new ArrayList<>();
      types.add("ADMIN");
      types.add("MONITOR");

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(UserController.this,
                R.layout.support_simple_spinner_dropdown_item,types);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        userType.setAdapter(adapter);

    }

}