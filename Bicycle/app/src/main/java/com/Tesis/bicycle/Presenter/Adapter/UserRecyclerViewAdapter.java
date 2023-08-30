package com.Tesis.bicycle.Presenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Dto.ApiRest.UserAppDto;
import com.Tesis.bicycle.Presenter.ApiRestConnection;
import com.Tesis.bicycle.Presenter.Client.ClientRetrofit;
import com.Tesis.bicycle.R;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder> {

    private static List<UserAppDto> users;
    private static OnItemClickListener listener;


    public UserRecyclerViewAdapter(List<UserAppDto> users) {
        this.users = users;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static void setListener(OnItemClickListener listener) {
        UserRecyclerViewAdapter.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_user,parent,false);
       ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtValueNameCompleteUser.setText(users.get(position).getFirstName()+" "+users.get(position).getLastName());
        holder.txtValueAgeUser.setText(String.valueOf(users.get(position).getAge()));
        holder.txtValueWeight.setText(String.valueOf(users.get(position).getWeight()));
        holder.txtValueHeight.setText(String.valueOf(users.get(position).getHeight()));
        String url = ApiRestConnection.URL_STORED_DOCUMENT + "download?fileName=" + users.get(position).getFileName();
        final Picasso picasso = new Picasso.Builder(holder.itemView.getContext())
                .downloader(new OkHttp3Downloader(ClientRetrofit.getHttp()))
                .build();
        picasso.load(url)
                .error(R.drawable.image_not_found)
                .into(holder.imgProfile);
        holder.txtValueEmail.setText(String.valueOf(users.get(position).getEmail()));

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public interface OnItemClickListener {
        void onItemClick(UserAppDto user);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtValueNameCompleteUser,txtValueAgeUser,txtValueWeight,txtValueHeight,txtValueEmail;
        private ImageView imgProfile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtValueNameCompleteUser= itemView.findViewById(R.id.txtValueNameCompleteUser);
            txtValueAgeUser= itemView.findViewById(R.id.txtValueAgeUser);
            txtValueWeight= itemView.findViewById(R.id.txtValueWeight);
            txtValueHeight= itemView.findViewById(R.id.txtValueHeight);
            imgProfile=itemView.findViewById(R.id.imgProfile);
            txtValueEmail=itemView.findViewById(R.id.txtValueEmail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(users.get(position));
                    }
                }
            });
        }
    }
}
