package com.Tesis.bicycle.Activity.ui.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.Tesis.bicycle.Activity.ui.NavInitActivity;
import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Presenter.Adapter.MyRoutesRecyclerViewAdapter;
import com.Tesis.bicycle.Presenter.Adapter.OnItemClickListener;
import com.Tesis.bicycle.Presenter.Adapter.RouteRecyclerViewAdapter;
import com.Tesis.bicycle.ViewModel.StatisticsViewModel;

import java.util.List;

public class MyMapsFragment extends BaseListViewFragment {

    List<RouteDetailsDto> routes;

    private RouteRecyclerViewAdapter adaptorRoute;

    private StatisticsViewModel statisticsViewModel;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);
        statisticsViewModel =new ViewModelProvider(requireActivity()).get(StatisticsViewModel.class);
        floatingactionbutton.setVisibility(View.INVISIBLE);
        getListView();
        return view;
    }

    @Override
    public void getListView() {
        accessTokenRoomViewModel.getFirst().observe(getViewLifecycleOwner(),response->{
            if(response.getAccessToken()!=null){
                statisticsViewModel.getRouteById(response.getId()).observe(getViewLifecycleOwner(), resp->{
                    if(!resp.isEmpty()){
                            routes = resp;
                            adaptorRoute = new RouteRecyclerViewAdapter(routes);
                            recyclerView.setAdapter(adaptorRoute);
                    }else {
                        Toast.makeText(context, "Not exist routes for user: " + response.getId(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}