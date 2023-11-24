package com.Tesis.bicycle.Presenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Tesis.bicycle.Dto.ApiRest.Statistics.StatisticsDto;
import com.Tesis.bicycle.Presenter.ApiRestConnection;
import com.Tesis.bicycle.Presenter.Client.ClientRetrofit;
import com.Tesis.bicycle.R;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    List<StatisticsDto> ranking;

    public RankingAdapter(List<StatisticsDto> ranking) {
        this.ranking = ranking;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        RankingAdapter.ViewHolder viewHolder=new RankingAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StatisticsDto statisticsDto=ranking.get(position);
        holder.userItem.setText(String.valueOf(statisticsDto.getNameComplete()));
        holder.txtTimeValue.setText(String.valueOf(statisticsDto.getTime()));
        holder.txtSpeedValue.setText(String.valueOf(statisticsDto.getAvgSpeedString())+"km/h");
        holder.txtDistValue.setText(String.valueOf(statisticsDto.getDistanceString()));
        holder.rankNumber.setText(String.valueOf(position+1));
        String url = ApiRestConnection.URL_STORED_DOCUMENT + "download?fileName=" + ranking.get(position).getFileName();
        final Picasso picasso = new Picasso.Builder(holder.itemView.getContext())
                .downloader(new OkHttp3Downloader(ClientRetrofit.getHttp()))
                .build();
        picasso.load(url)
                .error(R.drawable.image_not_found)
                .into(holder.imageRanking);
        boolean isExpandable=ranking.get(position).isExpandable();
        holder.expandible_layout.setVisibility(isExpandable ? View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return ranking.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        private TextView userItem,txtTimeValue,txtDistValue,txtSpeedValue,rankNumber;
        private LinearLayout linearLayout;
        private RelativeLayout expandible_layout;

        private CircleImageView imageRanking;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userItem=itemView.findViewById(R.id.user_item);
            txtTimeValue=itemView.findViewById(R.id.txtTimeValue);
            txtDistValue=itemView.findViewById(R.id.txtDistValue);
            txtSpeedValue=itemView.findViewById(R.id.txtSpeedValue);
            linearLayout=itemView.findViewById(R.id.lycardview);
            expandible_layout=itemView.findViewById(R.id.expandible_layout);
            rankNumber=itemView.findViewById(R.id.rankNumber);
            imageRanking=itemView.findViewById(R.id.imageRanking);



            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatisticsDto statisticsDto= ranking.get(getAdapterPosition());
                    statisticsDto.setExpandable(!statisticsDto.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }

}
