package com.Tesis.bicycle.Activity.ui.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.Tesis.bicycle.Dto.ApiRest.FriendshipRequest.FriendshipRequestDto;
import com.Tesis.bicycle.Presenter.Adapter.ReceivedRequestAdapter;
import com.Tesis.bicycle.Presenter.Adapter.RouteRecyclerViewAdapter;
import com.Tesis.bicycle.ViewModel.FriendshipRequestViewModel;
import com.Tesis.bicycle.ViewModel.StatisticsViewModel;

import java.util.List;

public class FriendshipRequestFragment extends BaseListViewFragment{

    private List<FriendshipRequestDto> friendshipRequestDtoList;

    private ReceivedRequestAdapter adapter;

    private FriendshipRequestViewModel friendshipRequestViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);
        friendshipRequestViewModel =new ViewModelProvider(requireActivity()).get(FriendshipRequestViewModel.class);
        floatingactionbutton.setVisibility(View.INVISIBLE);
        getListView();
        return view;
    }

    @Override
    public void getListView() {
        accessTokenRoomViewModel.getFirst().observe(getViewLifecycleOwner(),response->{
            if(response.getAccessToken()!=null){
                friendshipRequestViewModel.(response.getId()).observe(getViewLifecycleOwner(), resp->{
                    if(!resp.isEmpty()){
                        routes = resp;
                        adaptorRoute = new RouteRecyclerViewAdapter(routes);
                        adaptorRoute.setListener(this);
                        recyclerView.setAdapter(adaptorRoute);
                    }else {
                        Toast.makeText(context, "Not exist routes for user: " + response.getId(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
