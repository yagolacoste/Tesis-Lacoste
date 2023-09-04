package com.Tesis.bicycle.Presenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ReceivedRequestAdapter extends RecyclerView.Adapter<ReceivedRequestAdapter.ViewHolder> {

    private List<RequestDto> mySent;

    private List<RequestDto> myReceived;
    private Context context;

    private Long userOrigin;

    private FriendshipRequestViewModel friendshipRequestViewModel;

    private ViewModelStoreOwner viewModelStoreOwner;

    private OnRequestSentListener onRequestSentListener;

    public ReceivedRequestAdapter(List<RequestDto> mySent,List<RequestDto> myReceived, Context context, Long userOrigin, ViewModelStoreOwner viewModelStoreOwner) {
        this.mySent = mySent;
        this.myReceived=myReceived;
        this.context = context;
        this.userOrigin = userOrigin;
        this.friendshipRequestViewModel = new ViewModelProvider(viewModelStoreOwner).get(FriendshipRequestViewModel.class);
        this.viewModelStoreOwner = viewModelStoreOwner;

    }



    public void setOnRequestSentListener(OnRequestSentListener listener) {
        onRequestSentListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_friendship_request, parent, false);
        ReceivedRequestAdapter.ViewHolder viewHolder = new ReceivedRequestAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestDto requestDto;

        if (position < mySent.size()) {
            requestDto = mySent.get(position);
            if (requestDto.getStatus() == FriendshipRequestStatus.PENDING.getValue()) {
                if (requestDto.getUserDest() != userOrigin) {
                    holder.acceptedRequest.setActivated(false);
                    holder.acceptedRequest.setVisibility(View.INVISIBLE);
                    holder.rejectedRequest.setActivated(false);
                    holder.rejectedRequest.setVisibility(View.INVISIBLE);
                    TextView textView = new TextView(context);
                    holder.linearLayoutRequest.removeAllViews();
                    textView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    textView.setText(FriendshipRequestStatus.PENDING.getTitle());
                    holder.linearLayoutRequest.addView(textView);
                }
            }
        } else {
            requestDto = myReceived.get(position - mySent.size());
            if (requestDto.getStatus() == FriendshipRequestStatus.PENDING.getValue()) {
                holder.acceptedRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FriendshipRequestDto friendshipRequestDto = new FriendshipRequestDto();
                        friendshipRequestDto.setUserOrigin(requestDto.getUserDest());
                        friendshipRequestDto.setUserDest(userOrigin);
                        friendshipRequestViewModel.acceptedRequest(friendshipRequestDto).observe((LifecycleOwner) viewModelStoreOwner, resp -> {
                            if (resp) {
                                if (onRequestSentListener != null) {
                                    onRequestSentListener.onRequestSent(true);
                                }
                                Toast.makeText(context, "Se acepto la solicitud", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(context, "error de solicitud", Toast.LENGTH_LONG).show();
                        });
                    }
                });
                holder.rejectedRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FriendshipRequestDto friendshipRequestDto = new FriendshipRequestDto();
                        friendshipRequestDto.setUserOrigin(requestDto.getUserDest());
                        friendshipRequestDto.setUserDest(userOrigin);
                        friendshipRequestViewModel.rejectedRequest(friendshipRequestDto).observe((LifecycleOwner) viewModelStoreOwner, resp -> {
                            if (resp) {
                                if (onRequestSentListener != null) {
                                    onRequestSentListener.onRequestSent(true);
                                }
                                Toast.makeText(context, "Se rechazo la solicitud", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(context, "error de solicitud", Toast.LENGTH_LONG).show();
                        });
                    }
                });
            }
        }

        holder.nameRequest.setText(String.valueOf(requestDto.getNameComplete()));
        String url = ApiRestConnection.URL_STORED_DOCUMENT + "download?fileName=" + requestDto.getFileName();
        final Picasso picasso = new Picasso.Builder(holder.itemView.getContext())
                .downloader(new OkHttp3Downloader(ClientRetrofit.getHttp()))
                .build();
        picasso.load(url)
                .rotate(270)
                .error(R.drawable.image_not_found)
                .into(holder.profilePhotoRequest);

        if (requestDto.getStatus() == FriendshipRequestStatus.ACCEPTED.getValue()) {
            holder.acceptedRequest.setActivated(false);
            holder.acceptedRequest.setVisibility(View.INVISIBLE);
            holder.rejectedRequest.setActivated(false);
            holder.rejectedRequest.setVisibility(View.INVISIBLE);
            TextView textView = new TextView(context);
            holder.linearLayoutRequest.removeAllViews();
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            textView.setText(FriendshipRequestStatus.ACCEPTED.getTitle());
            holder.linearLayoutRequest.addView(textView);
        }

    }


    @Override
    public int getItemCount() {
        return mySent.size()+myReceived.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView profilePhotoRequest;
        private TextView nameRequest;
        private Button rejectedRequest, acceptedRequest;

        private View rootView;

        private LinearLayout linearLayoutRequest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePhotoRequest = itemView.findViewById(R.id.profilePhotoRequest);
            nameRequest = itemView.findViewById(R.id.nameRequest);
            rejectedRequest = itemView.findViewById(R.id.rejectedRequest);
            acceptedRequest = itemView.findViewById(R.id.acceptedRequest);
            linearLayoutRequest = itemView.findViewById(R.id.linearLayoutRequest);

        }


    }
}
