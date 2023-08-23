package com.Tesis.bicycle.Presenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;


import com.Tesis.bicycle.Dto.ApiRest.Request.FriendshipRequestDto;
import com.Tesis.bicycle.Dto.ApiRest.Request.RequestDto;
import com.Tesis.bicycle.Model.FriendshipRequestStatus;
import com.Tesis.bicycle.Presenter.ApiRestConnection;
import com.Tesis.bicycle.Presenter.Client.ClientRetrofit;
import com.Tesis.bicycle.R;

import com.Tesis.bicycle.ViewModel.FriendshipRequestViewModel;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;


import java.util.List;

public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.ViewHolder> {

    private List<RequestDto> users;
    private Context context;

    private Long userOrigin;

    private FriendshipRequestViewModel friendshipRequestViewModel;

    private ViewModelStoreOwner viewModelStoreOwner;

    private OnRequestSentListener onRequestSentListener;

    public void setOnRequestSentListener(OnRequestSentListener listener) {
        onRequestSentListener = listener;
    }

    public UserSearchAdapter(Context context, List<RequestDto> filteredUserList, Long userOrigin, ViewModelStoreOwner viewModelStoreOwner) {
        this.users = filteredUserList;
        this.context = context;
        this.userOrigin=userOrigin;
        this.viewModelStoreOwner=viewModelStoreOwner;
        this.friendshipRequestViewModel=new ViewModelProvider(viewModelStoreOwner).get(FriendshipRequestViewModel.class);
    }

    public List<RequestDto> getUsers() {
        return users;
    }

    public void setUsers(List<RequestDto> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_search_user,parent,false);
        UserSearchAdapter.ViewHolder viewHolder=new UserSearchAdapter.ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestDto requestDto=users.get(position);
        holder.nameUserSearch.setText(String.valueOf(requestDto.getNameComplete()));
        holder.stateUserSearch.setText(checkStatus(users.get(position)));
        String url = ApiRestConnection.URL_STORED_DOCUMENT + "download?fileName=" + users.get(position).getFileName();
        final Picasso picasso = new Picasso.Builder(holder.itemView.getContext())
                .downloader(new OkHttp3Downloader(ClientRetrofit.getHttp()))
                .build();
        picasso.load(url)
                .error(R.drawable.image_not_found)
                .into(holder.profilePhotoSearchUser);
        if(requestDto.getStatus() == FriendshipRequestStatus.PENDING.getValue()){
            holder.btnSendRequest.setActivated(false);
            holder.btnSendRequest.setVisibility(View.INVISIBLE);
        }
        holder.btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendshipRequestDto friendshipRequestDto=new FriendshipRequestDto();
                friendshipRequestDto.setUserOrigin(userOrigin);
                friendshipRequestDto.setUserDest(requestDto.getUserDest());
                friendshipRequestViewModel.sendRequest(friendshipRequestDto).observe((LifecycleOwner) viewModelStoreOwner, resp->{
                    if(resp){
                        if (onRequestSentListener != null) {
                            onRequestSentListener.onRequestSent(true);
                        }
                        Toast.makeText(context,"Se envio solicitud correcto",Toast.LENGTH_LONG).show();
                    }else  Toast.makeText(context,"error de solicitud",Toast.LENGTH_LONG).show();
                });
            }
        });


    }

    private String checkStatus(RequestDto requestDto) {
        if (requestDto.getStatus() == FriendshipRequestStatus.PENDING.getValue())
            return FriendshipRequestStatus.PENDING.getTitle();
        else if (requestDto.getStatus() == FriendshipRequestStatus.ACCEPTED.getValue())
            return FriendshipRequestStatus.ACCEPTED.getTitle();
        else if (requestDto.getStatus() == FriendshipRequestStatus.REJECTED.getValue())
            return FriendshipRequestStatus.REJECTED.getTitle();
        else if (requestDto.getStatus() == FriendshipRequestStatus.NOTFRIENDS.getValue())
            return FriendshipRequestStatus.NOTFRIENDS.getTitle();
        else
        throw new IllegalArgumentException("unknown position " + requestDto.getStatus());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView profilePhotoSearchUser;
        private TextView nameUserSearch,stateUserSearch;
        private Button btnSendRequest;

        private View rootView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePhotoSearchUser=itemView.findViewById(R.id.profilePhotoSearchUser);
            nameUserSearch=itemView.findViewById(R.id.nameUserSearch);
            stateUserSearch=itemView.findViewById(R.id.stateUserSearch);
            btnSendRequest=itemView.findViewById(R.id.btnSendRequest);
            rootView=itemView;
        }
    }
}
