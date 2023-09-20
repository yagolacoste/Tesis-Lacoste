package com.Tesis.bicycle.Presenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Tesis.bicycle.Dto.ApiRest.Statistics.StatisticsDto;
import com.Tesis.bicycle.R;


import java.util.List;

public class StatisticRecyclerViewAdapter extends RecyclerView.Adapter<StatisticRecyclerViewAdapter.ViewHolder> {

    private List<StatisticsDto> statistics;

    public StatisticRecyclerViewAdapter(List<StatisticsDto> statistics) {
        this.statistics = statistics;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView distance,speed,speedTime,timeSession;
        private View rootView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            distance=(TextView) itemView.findViewById(R.id.txtViewValueDistance);
            speed=(TextView) itemView.findViewById(R.id.txtViewValueAvgSpeed);
            speedTime=(TextView) itemView.findViewById(R.id.txtValueTime);
            timeSession=(TextView) itemView.findViewById(R.id.txtValueDateCompete);
            rootView=itemView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_statistics,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.distance.setText(statistics.get(position).getDistanceString());
        holder.speed.setText(statistics.get(position).getAvgSpeedString()+" km/h");
        holder.speedTime.setText(String.valueOf(statistics.get(position).getTime()));
        holder.timeSession.setText(String.valueOf(statistics.get(position).getTimeCreated()));
    }

    @Override
    public int getItemCount() {
        return statistics.size();
    }
}
