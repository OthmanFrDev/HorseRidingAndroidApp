package com.example.horseriding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleCalendarAdapter extends RecyclerView.Adapter<RecycleCalendarAdapter.ViewHolder> {
    private List<Seance> listSeance;
    private Context context;
    public RecycleCalendarAdapter(Context c,List<Seance> l) {
        listSeance=l;
        context=c;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View viewRecycle = inflater.inflate(R.layout.activity_calendar_day, parent, false);
        ViewHolder viewHolder = new ViewHolder(viewRecycle);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.time_08.setText(listSeance.get(position).getComments());
        holder.time_09.setText(listSeance.get(position).getComments());
        holder.time_10.setText(listSeance.get(position).getComments());
        holder.time_11.setText(listSeance.get(position).getComments());
        holder.time_16.setText(listSeance.get(position).getComments());
        holder.time_08.setText(listSeance.get(position).getComments());
        holder.time_18.setText(listSeance.get(position).getComments());
    }

    @Override
    public int getItemCount() {
        return listSeance.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView time_08,time_09,time_10,time_11,time_12,time_13,time_14,time_15,time_16,time_17,time_18;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            time_08=itemView.findViewById(R.id.time_08);
            time_09=itemView.findViewById(R.id.time_09);
            time_10=itemView.findViewById(R.id.time_10);
            time_11=itemView.findViewById(R.id.time_11);
            time_12=itemView.findViewById(R.id.time_12);
            time_13=itemView.findViewById(R.id.time_13);
            time_14=itemView.findViewById(R.id.time_14);
            time_15=itemView.findViewById(R.id.time_15);
            time_16=itemView.findViewById(R.id.time_16);
            time_17=itemView.findViewById(R.id.time_17);
            time_18=itemView.findViewById(R.id.time_18);
        }
    }
}