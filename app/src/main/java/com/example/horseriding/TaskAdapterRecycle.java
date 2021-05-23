package com.example.horseriding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapterRecycle extends RecyclerView.Adapter<TaskAdapterRecycle.ViewHolder> {
    private Context context;
    private List<Task> list;
    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView txtName,txtRole;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.nameuserlist);
            txtRole=itemView.findViewById(R.id.userrolelist);
        }

    }
    public TaskAdapterRecycle(Context c, List<Task> list){
        this.context=c;
        this.list=list;
    }


    @NonNull
    @Override
    public TaskAdapterRecycle.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.activity_ligne__list__user_,parent,false);
        return new TaskAdapterRecycle.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapterRecycle.ViewHolder holder, int position) {
        Task t=list.get(position);
        holder.txtName.setText(t.getTitle());
        holder.txtRole.setText(String.valueOf(t.getTaskId()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}