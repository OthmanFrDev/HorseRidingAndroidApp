package com.example.horseriding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapterRecycle extends RecyclerView.Adapter<UserAdapterRecycle.ViewHolder> {
    private Context context;
    private List<User> list;
    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView txtName,txtRole;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.nameuserlist);
            txtRole=itemView.findViewById(R.id.userrolelist);
        }

    }
    public UserAdapterRecycle(Context c, List<User> list){
        this.context=c;
        this.list=list;
    }


    @NonNull
    @Override
    public UserAdapterRecycle.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.activity_ligne__list__user_,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapterRecycle.ViewHolder holder, int position) {
        User u=list.get(position);
        holder.txtName.setText(u.getUserId() +" "+
                u.getUserLname()+" "+u.getUserFname());
        holder.txtRole.setText(u.getUserType());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
