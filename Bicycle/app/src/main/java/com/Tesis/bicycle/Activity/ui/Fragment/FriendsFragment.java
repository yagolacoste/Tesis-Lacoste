package com.Tesis.bicycle.Activity.ui.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModelProvider;

import com.Tesis.bicycle.Activity.AddFriendActivity;
import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.UserAppDto;
import com.Tesis.bicycle.Presenter.Adapter.UserRecyclerViewAdapter;
import com.Tesis.bicycle.ViewModel.UserViewModel;

import java.util.List;

public class FriendsFragment extends BaseListViewFragment{
    private static final String ARG_TAB_NAME = "ARG_TAB_NAME";

    private List<UserAppDto> friends;

    private UserViewModel userViewModel;

    private UserRecyclerViewAdapter adapter;

    private String action;

    public static FriendsFragment newInstance(@StringRes int tabName) {
        FriendsFragment frg = new FriendsFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_TAB_NAME, tabName);
        frg.setArguments(args);

        return frg;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);
        userViewModel=new ViewModelProvider(this).get(UserViewModel.class);
        Bundle args = getArguments();
        floatingactionbutton.setActivated(true);
        getListView();
        floatingactionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Intent i=new Intent(view.getContext(), AddFriendActivity.class);
                 startActivity(i);
            }
        });
        return view;
    }

    @Override
    public void getListView() {
        accessTokenRoomViewModel.getFirst().observe(getViewLifecycleOwner(),response->{
            if(response.getAccessToken()!=null){
                userViewModel.getFriends(response.getId()).observe(getViewLifecycleOwner(),resp->{
                    if(!resp.isEmpty()){
                        friends = resp;
                        adapter=new UserRecyclerViewAdapter(friends);
//                        if(action==null){
//                            adapter.setOnItemClickListener(this);
//                        }
                        recyclerView.setAdapter(adapter);
                    }
                    else
                        Toast.makeText(context,"Not exist Friends for route: ",Toast.LENGTH_SHORT).show();
                });
            }
        });

    }


}
