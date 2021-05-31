package com.example.horseriding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ClientAdapterRecycle extends RecyclerView.Adapter<ClientAdapterRecycle.ViewHolder> implements Filterable {
    private Context context;
    private List<Client> list;
    private List<Client> listFiltred;
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key=constraint.toString();
                if(key.isEmpty())listFiltred=list;
                else{
                    List<Client> lFiltred=new ArrayList<>();
                    for(Client c:list){
                        if(c.getfName().toLowerCase().contains(key.toLowerCase())||
                                c.getlName().toLowerCase().contains(key.toLowerCase())){
                            lFiltred.add(c);
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
                listFiltred= (List<Client>) results.values;
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
    public ClientAdapterRecycle(Context c, List<Client> list){
        this.context=c;
        this.list=list;
    }


    @NonNull
    @Override
    public ClientAdapterRecycle.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.activity_ligne__list__user_,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Client u=list.get(position);
        holder.txtName.setText(u.getClientEmail());
        holder.txtRole.setText(String.valueOf(u.getClientId()));
    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
