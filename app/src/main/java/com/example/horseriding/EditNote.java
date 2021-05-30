package com.example.horseriding;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDateTime;
public class EditNote extends AppCompatActivity {
    private TextView current_date_label;
    private EditText contenue;
    private DatabaseHandler db;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        //DataBase creation
        db = new DatabaseHandler(EditNote.this);
        //Setting ToolBar Back Button
        Toolbar toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Initializing feilds
        current_date_label=findViewById(R.id.current_date_label);
        contenue=findViewById(R.id.contenue);
        current_date_label.setText(LocalDateTime.now().getDayOfMonth()+" "+LocalDateTime.now().getMonth()+" "+LocalDateTime.now().getYear()+" à "+LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute());
        if(getIntent().getIntExtra("code",0)==2)
        {
            int id=getIntent().getIntExtra("id",0);
            Note r=new Note();
            r=db.readNote(id);
            contenue.setText(r.getNotes());
            current_date_label.setText(LocalDateTime.parse(r.getDate()).getDayOfMonth()+" "+LocalDateTime.parse(r.getDate()).getMonth()+" "+LocalDateTime.parse(r.getDate()).getYear()+" à "+LocalDateTime.parse(r.getDate()).getHour()+":"+LocalDateTime.parse(r.getDate()).getMinute());
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean onOptionsItemSelected(MenuItem item){
        if(contenue.getText().toString().isEmpty())
        {
            EditNote.this.finish();
            return true;
        }

        else if(getIntent().getIntExtra("code",0)==1)
        {
            Note remarque=new Note(LocalDateTime.now().toString(),contenue.getText().toString());
            db.saveNote(remarque);
            Log.d("jes","added remarque");
            EditNote.this.finish();
            Intent myIntent = new Intent(getApplicationContext(),NoteController.class);
            startActivity(myIntent);
            return true;
        }
        else if(getIntent().getIntExtra("code",0)==2)
        {
            int id=getIntent().getIntExtra("id",0);
            db.updateNote(id,contenue.getText().toString());
            Log.d("jes","updated remarque");
            EditNote.this.finish();
            return true;
        }
        else {
            EditNote.this.finish();
            return true;
        }
    }
}