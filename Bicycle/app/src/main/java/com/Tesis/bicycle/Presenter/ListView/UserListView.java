package com.Tesis.bicycle.Presenter.ListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.Tesis.bicycle.Activity.ShowPointLocationList;
import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Dto.ApiRest.UserAppDto;
import com.Tesis.bicycle.Presenter.Adapter.MyRoutesRecyclerViewAdapter;
import com.Tesis.bicycle.Presenter.Adapter.StatisticRecyclerViewAdapter;
import com.Tesis.bicycle.Presenter.Adapter.UserRecyclerViewAdapter;
import com.Tesis.bicycle.ViewModel.UserViewModel;

import java.util.List;

public class UserListView extends ShowPointLocationList implements UserRecyclerViewAdapter.OnItemClickListener{

    private List<UserAppDto> friends;

    private UserViewModel user;

    private UserRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user=new ViewModelProvider(this).get(UserViewModel.class);
        getUsers();
    }

    private void getUsers() {
        accessTokenRoomViewModel.getFirst().observe(this,response->{
            if(response.getAccessToken()!=null){
                user.getFriends(response.getId()).observe(this,resp->{
                    if(!resp.isEmpty()){
                        friends = resp;
                        adapter=new UserRecyclerViewAdapter(friends);
                        adapter.setOnItemClickListener(this);
                        recyclerView.setAdapter(adapter);
                    }
                    else
                        Toast.makeText(this,"Not exist Friends for route: ",Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    @Override
    public void onItemClick(UserAppDto user) {
        Intent resultIntent = new Intent();
        resultIntent.setAction(Constants.USER_SELECT);
        resultIntent.putExtra(Constants.USER_ITEM, user);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}


