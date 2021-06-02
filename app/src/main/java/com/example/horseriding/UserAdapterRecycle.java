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

public class UserAdapterRecycle extends RecyclerView.Adapter<UserAdapterRecycle.ViewHolder> implements Filterable {
    private Context context;
    private List<User> list;
    private List<User> listFiltred;
    private RecycleViewClickListner listner;
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key=constraint.toString();
                if(key.isEmpty())listFiltred=list;
                else{
                    List<User> lFiltred=new ArrayList<>();
                    for(User u:list){
                        if(u.getUserFname().toLowerCase().contains(key.toLowerCase())||
                                u.getUserLname().toLowerCase().contains(key.toLowerCase())){
                            lFiltred.add(u);
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
                listFiltred= (List<User>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtName,txtRole;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.nameuserlist);
            txtRole=itemView.findViewById(R.id.userrolelist);
            itemView.setOnClickListener((View.OnClickListener) this);
        }
        @Override
        public void onClick(View v) {
            listner.onClickItem(v,getAdapterPosition());
        }
    }
    public UserAdapterRecycle(Context c, List<User> list,RecycleViewClickListner l){
        this.context=c;
        this.list=list;
        this.listFiltred=list;
        this.listner=l;
    }


    @NonNull
    @Override
    public UserAdapterRecycle.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.activity_ligne__list__user_,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapterRecycle.ViewHolder holder, int position) {

        User u=listFiltred.get(position);
        holder.txtName.setText(u.getUserId() +" "+
                u.getUserLname()+" "+u.getUserFname());

        holder.txtRole.setText(u.getUserType());
    }

    @Override
    public int getItemCount() {
        return listFiltred.size();
    }
    public void filterList(List<User> l){
        this.list=l;
        notifyDataSetChanged();
    }
    public interface RecycleViewClickListner{
        void onClickItem(View v,int position);
    }
}
