package com.Tesis.bicycle.Activity.ui.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;


import com.Tesis.bicycle.Dto.ApiRest.Request.FriendshipRequestDto;
import com.Tesis.bicycle.Presenter.Adapter.OnRequestSentListener;
import com.Tesis.bicycle.Presenter.Adapter.ReceivedRequestAdapter;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.FriendshipRequestViewModel;

import java.util.List;


public class FriendshipRequestFragment extends BaseListViewFragment implements OnRequestSentListener {

    private ReceivedRequestAdapter adapter;

    private FriendshipRequestViewModel friendshipRequestViewModel;

    private String text="You haven't friends request";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);
        friendshipRequestViewModel =new ViewModelProvider(requireActivity()).get(FriendshipRequestViewModel.class);
        floatingactionbutton.setVisibility(View.INVISIBLE);
        imgLayoutEmpty.setImageResource(R.drawable.ic_notification);
        txtLayoutEmpty.setText(text);
        getListView();
        return view;
    }

    @Override
    public void getListView() {
        accessTokenRoomViewModel.getFirst().observe(getViewLifecycleOwner(),response->{
            if(response.getAccessToken()!=null){
                friendshipRequestViewModel.request(response.getId()).observe(getViewLifecycleOwner(), resp->{
                    if(resp!=null && !(resp.getMyReceived().isEmpty()&&resp.getMySent().isEmpty())){
                        layoutEmpty.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        adapter = new ReceivedRequestAdapter(resp.getMySent(),resp.getMyReceived(),getContext(),response.getId(),FriendshipRequestFragment.this);
                        adapter.setOnRequestSentListener(this);
                        recyclerView.setAdapter(adapter);

                    }else {
                        layoutEmpty.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(context, "Not exist routes for user: " + response.getId(), Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                layoutEmpty.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onRequestSent(boolean success) {
        if (success) {
            getListView();
            adapter.notifyDataSetChanged();
        }
    }
}
