package com.example.horseriding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class SeanceAdapterRecycle extends RecyclerView.Adapter<SeanceAdapterRecycle.ViewHolder> {
    private Context context;
    private List<Seance> list;
    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView txtName,txtRole;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.nameuserlist);
            txtRole=itemView.findViewById(R.id.userrolelist);
        }

    }
    public SeanceAdapterRecycle(Context c, List<Seance> list){
        this.context=c;
        this.list=list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.activity_ligne__list__user_,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SeanceAdapterRecycle.ViewHolder holder, int position) {
        Seance s=list.get(position);
        holder.txtName.setText(s.getComments()+" ");
        holder.txtRole.setText(String.valueOf(s.getSeanceId()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

