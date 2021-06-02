package com.example.horseriding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SeanceAdapterRecycle extends RecyclerView.Adapter<SeanceAdapterRecycle.ViewHolder> implements Filterable {
    private Context context;
    private List<Seance> list;
    private List<Seance> listFiltred;

    @Override
    public Filter getFilter() {
         return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key=constraint.toString();
                if(key.isEmpty())listFiltred=list;
                else{
                    List<Seance> lFiltred=new ArrayList<>();
                    for(Seance seance:list){
                        if(seance.getComments().toLowerCase().contains(key.toLowerCase())){
                            lFiltred.add(seance);
                        }
                    }
                    listFiltred=lFiltred;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=listFiltred;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listFiltred= (List<Seance>) results.values;
                notifyDataSetChanged();
            }
        };
    }

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
        this.listFiltred=list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.activity_ligne__list__user_,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SeanceAdapterRecycle.ViewHolder holder, int position) {
        Seance s=listFiltred.get(position);
        holder.txtName.setText(s.getComments()+" ");
        holder.txtRole.setText(String.valueOf(s.getSeanceId()));
    }

    @Override
    public int getItemCount() {
        return listFiltred.size();
    }
    public void filterList(List<Seance> l){
        this.list=l;
        notifyDataSetChanged();
    }
}

