package com.Tesis.bicycle.Presenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Tesis.bicycle.Dto.ApiRest.StatisticsDto;
import com.Tesis.bicycle.R;


import java.util.List;

public class StatisticRecyclerViewAdapter extends RecyclerView.Adapter<StatisticRecyclerViewAdapter.ViewHolder> {

    public List<StatisticsDto> statistics;

    public StatisticRecyclerViewAdapter(List<StatisticsDto> statistics) {
        this.statistics = statistics;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView distance,speed,speedTime,timeSession;
        private View rootView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            distance=(TextView) itemView.findViewById(R.id.tv_card_distance);
            speed=(TextView) itemView.findViewById(R.id.tv_card_speed);
            speedTime=(TextView) itemView.findViewById(R.id.tv_card_speedTime);
            timeSession=(TextView) itemView.findViewById(R.id.tv_card_timeSession);
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
        holder.distance.setText(String.valueOf(statistics.get(position).getDistance()));
        holder.speed.setText(String.valueOf(statistics.get(position).getSpeed()));
        holder.speedTime.setText(String.valueOf(statistics.get(position).getTimeSpeed()));
        holder.timeSession.setText(String.valueOf(statistics.get(position).getTimeSession()));
    }

    @Override
    public int getItemCount() {
        return statistics.size();
    }
}
