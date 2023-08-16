package com.Tesis.bicycle.Presenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.Tesis.bicycle.Dto.ApiRest.FriendshipRequest.FriendshipRequestDto;
import com.Tesis.bicycle.Dto.ApiRest.Statistics.StatisticsDto;
import com.Tesis.bicycle.Presenter.ApiRestConnection;
import com.Tesis.bicycle.Presenter.Client.ClientRetrofit;
import com.Tesis.bicycle.R;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    private List<FriendshipRequestDto> friendshipRequest;

    public RequestAdapter(List<FriendshipRequestDto> friendshipRequest) {
        friendshipRequest = friendshipRequest;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_friendship_request,parent,false);
        RequestAdapter.ViewHolder viewHolder=new RequestAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FriendshipRequestDto friendshipRequestDto=friendshipRequest.get(position);
        holder.nameRequest.setText(String.valueOf(friendshipRequestDto.getNameComplete()));
        String url = ApiRestConnection.URL_STORED_DOCUMENT + "download?fileName=" + friendshipRequest.get(position).getFileName();
        final Picasso picasso = new Picasso.Builder(holder.itemView.getContext())
                .downloader(new OkHttp3Downloader(ClientRetrofit.getHttp()))
                .build();
        picasso.load(url)
                .error(R.drawable.image_not_found)
                .into(holder.profilePhotoRequest);
    }

    @Override
    public int getItemCount() {
        return friendshipRequest.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView profilePhotoRequest;
        private TextView nameRequest;
        private Button rejectedRequest,acceptedRequest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePhotoRequest=itemView.findViewById(R.id.profilePhotoRequest);
            nameRequest=itemView.findViewById(R.id.nameRequest);
            rejectedRequest=itemView.findViewById(R.id.rejectedRequest);
            acceptedRequest=itemView.findViewById(R.id.acceptedRequest);

        }


    }
}
