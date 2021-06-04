package com.example.horseriding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteController extends AppCompatActivity {
    TextView empty_list_check;
    RecyclerView recyclerview_note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);
        empty_list_check = findViewById(R.id.empty_list_check);
        recyclerview_note = findViewById(R.id.recyclerview_note);
        Toolbar toolbar = findViewById(R.id.toolBar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        Intent myIntent = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(myIntent);
        return true;
    }

    @Override
    protected void onResume() {
        SessionManager sessionManager = new SessionManager(NoteController.this);
        super.onResume();
        DatabaseHandler db = new DatabaseHandler(NoteController.this);
        List<Note> notes = new ArrayList<>();
        notes = db.readNote();
        if (notes == null) {
            empty_list_check.setVisibility(View.VISIBLE);
            recyclerview_note.setVisibility(View.INVISIBLE);
        } else {
            Log.d("jes", String.valueOf(notes.size()));
            empty_list_check.setVisibility(View.GONE);
            recyclerview_note.setVisibility(View.VISIBLE);
            RecyclerViewAdapter_note adapter = new RecyclerViewAdapter_note(NoteController.this, notes);
            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(NoteController.this, LinearLayoutManager.VERTICAL, false);
            recyclerview_note.setLayoutManager(linearLayoutManager);
            recyclerview_note.setAdapter(adapter);
        }
    }

    public void ajouternote(View view) {
        Intent ajouterIntent = new Intent(NoteController.this, EditNote.class);

        ajouterIntent.putExtra("code", 1);


        NoteController.this.startActivity(ajouterIntent);
        NoteController.this.finish();
    }
}