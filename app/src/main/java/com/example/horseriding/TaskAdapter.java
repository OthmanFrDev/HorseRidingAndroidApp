package com.example.horseriding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    public TaskAdapter(@NonNull Context context, @NonNull List<Task> objects) {
        super(context, 0, objects);
        }

@NonNull
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v;
        LayoutInflater li= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v=li.inflate(R.layout.activity_ligne__list__user_,parent,false);
        TextView txtName=v.findViewById(R.id.nameuserlist);
        TextView txtRole=v.findViewById(R.id.userrolelist);
        txtName.setText(this.getItem(position).getTitle());
        txtRole.setText(String.valueOf(this.getItem(position).getTaskId()) );
        return v;
        }
        }

