package com.Tesis.bicycle.Presenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Dto.ApiRest.Statistics.ClassificationDto;
import com.Tesis.bicycle.Presenter.ApiRestConnection;
import com.Tesis.bicycle.Presenter.Client.ClientRetrofit;
import com.Tesis.bicycle.R;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ClassificationRecyclerAdapter extends RecyclerView.Adapter<ClassificationRecyclerAdapter.ViewHolder>{

    List<ClassificationDto> classifications;

    public ClassificationRecyclerAdapter(List<ClassificationDto> classifications) {
        this.classifications = classifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_clasification,parent,false);
        ClassificationRecyclerAdapter.ViewHolder viewHolder =new ClassificationRecyclerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassificationRecyclerAdapter.ViewHolder holder, int position) {
        String url = ApiRestConnection.URL_STORED_DOCUMENT + "download?fileName=" + classifications.get(position).getProfileUserDto().getPhoto();
        final Picasso picasso = new Picasso.Builder(holder.itemView.getContext())
                .downloader(new OkHttp3Downloader(ClientRetrofit.getHttp()))
                .build();
        picasso.load(url)
                .error(R.drawable.image_not_found)
                .into(holder.clImageViewUserPicture);
        holder.clTextViewName.setText(classifications.get(position).getProfileUserDto().getNameComplete());
        holder.clTextViewBattleWinner.setText(String.valueOf(classifications.get(position).getAchievementsDto().getBattleWinner()));
        holder.clTextViewSpeed.setText(String.valueOf(classifications.get(position).getAchievementsDto().getSpeedMax()));
        holder.clTextViewTime.setText(String.valueOf(classifications.get(position).getAchievementsDto().getTimeMin()));
        holder.clTextViewDistance.setText(String.valueOf(classifications.get(position).getAchievementsDto().getDistanceMax()));
    }

    @Override
    public int getItemCount() {
        return classifications.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        private TextView clTextViewBattleWinner,clTextViewDistance,clTextViewSpeed,clTextViewTime,clTextViewName;

        private ImageView clImageViewUserPicture;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clTextViewBattleWinner=itemView.findViewById(R.id.cl_textView_battle_winner);
            clTextViewDistance=itemView.findViewById(R.id.cl_textView_distance);
            clTextViewSpeed=itemView.findViewById(R.id.cl_textView_speed);
            clTextViewTime=itemView.findViewById(R.id.cl_textView_time);
            clImageViewUserPicture=itemView.findViewById(R.id.cl_imageView_user_picture);
            clTextViewName=itemView.findViewById(R.id.cl_textView_name);
        }

    }
}
