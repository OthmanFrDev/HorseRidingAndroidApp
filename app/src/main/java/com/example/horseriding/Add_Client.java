package com.example.horseriding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

public class Add_Client extends AppCompatActivity {
    TextInputLayout nom,prenom,mail,tel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        nom=findViewById(R.id.inputnom);
        prenom=findViewById(R.id.inputprenom);
        mail=findViewById(R.id.inputemail);
        tel=findViewById(R.id.inputtel);
    }

    public void ajouterClient(View view) {
        String email=mail.getEditText().getText().toString().trim();
        JavaMailAPI javaMailAPI=new JavaMailAPI(Add_Client.this,email,"waaa feeeennn a binommiii","wa raaaaaaak rbaaaaaaaahttiiiii");
        javaMailAPI.execute();
    }
}