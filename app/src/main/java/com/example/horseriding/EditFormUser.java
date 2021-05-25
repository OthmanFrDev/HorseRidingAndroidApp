package com.example.horseriding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EditFormUser extends AppCompatActivity {
    TextInputLayout nom,prenom,mail,tel,lastmdp,newmdp1,newmdp2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_form_user);
        nom=findViewById(R.id.inputnom);
        prenom=findViewById(R.id.inputprenom);
        mail=findViewById(R.id.inputemail);
        tel=findViewById(R.id.inputtel);
        lastmdp=findViewById(R.id.inputlastpass);
        newmdp1=findViewById(R.id.inputnewpass1);
        newmdp2=findViewById(R.id.inputnewpass2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        User u= (User) getIntent().getSerializableExtra("user");
        nom.getEditText().setText(u.getUserLname());
        prenom.getEditText().setText(u.getUserFname());
        mail.getEditText().setText(u.getUserEmail());
        tel.getEditText().setText(u.getUserPhone());
        lastmdp.getEditText().setText(u.getUserPasswd());
    }

    public void saveInfo(View view) {

    }
}