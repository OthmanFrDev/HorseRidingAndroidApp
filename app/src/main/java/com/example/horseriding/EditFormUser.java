package com.example.horseriding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class EditFormUser extends AppCompatActivity {
    TextInputLayout nom,prenom,mail,tel,lastmdp,newmdp1,newmdp2;
    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_form_user);
        nom=findViewById(R.id.inputnom);
        prenom=findViewById(R.id.inputprenom);
        mail=findViewById(R.id.inputemail);
        tel=findViewById(R.id.inputtel);
        lastmdp=findViewById(R.id.inputlastpass);
        bnv=findViewById(R.id.bottom_navigation);
        newmdp1=findViewById(R.id.inputnewpass1);
        newmdp2=findViewById(R.id.inputnewpass2);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i=null;
                switch(item.getItemId()){
                    case R.id.hide_nav:findViewById(R.id.floatingActionButtonweek).setVisibility(View.VISIBLE );findViewById(R.id.bottom_navigation).setVisibility(View.INVISIBLE);break;
                    case R.id.home_nav:
                        i = new Intent(EditFormUser.this, DashboardActivity.class);EditFormUser.this.startActivity(i);break;
                    case R.id.setting_nav:Toast.makeText(EditFormUser.this,"Vous êtes dans la page de modification :D",Toast.LENGTH_SHORT).show();break;
                    case R.id.logout_nav:SessionManager sessionManager=new SessionManager(EditFormUser.this);
                        sessionManager.logout();
                        i = new Intent(EditFormUser.this, LoginActivity.class);
                        EditFormUser.this.startActivity(i);
                        EditFormUser.this.finish();break;
                }
                return true;
            }
        });
    }
    public void onclickbtn(View view) {
        findViewById(R.id.floatingActionButtonweek).setVisibility(View.INVISIBLE);findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        User u= (User) getIntent().getSerializableExtra("user");
        if(u!=null){
            nom.getEditText().setText(u.getUserLname());
            prenom.getEditText().setText(u.getUserFname());
            mail.getEditText().setText(u.getUserEmail());
            tel.getEditText().setText(u.getUserPhone());
            lastmdp.getEditText().setText(u.getUserPasswd());
        }else{
            SessionManager sessionManager=new SessionManager(this);
            HashMap<String,String> userdetail=sessionManager.getUserDetailFromSession();
            nom.getEditText().setText(userdetail.get(SessionManager.KEY_FULLNAME).split(" ")[0]+"");
            prenom.getEditText().setText(userdetail.get(SessionManager.KEY_FULLNAME).split(" ")[1]+"");
            mail.getEditText().setText(userdetail.get(SessionManager.KEY_EMAIL));
            tel.getEditText().setText((userdetail.get(SessionManager.KEY_PHONENUMBER)));
            lastmdp.getEditText().setText(userdetail.get(SessionManager.KEY_PASSWORD));
        }

    }

    public void saveInfo(View view) {

    }
}