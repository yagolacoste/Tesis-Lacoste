package com.Tesis.bicycle.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Dto.ApiRest.StatisticsDto;
import com.Tesis.bicycle.Presenter.Adapter.MyRoutesRecyclerViewAdapter;
import com.Tesis.bicycle.Presenter.Adapter.RouteRecyclerViewAdapter;
import com.Tesis.bicycle.Presenter.Adapter.StatisticRecyclerViewAdapter;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.AccessTokenRoomViewModel;
import com.Tesis.bicycle.ViewModel.AppUserHasRouteViewModel;

import java.util.List;

public class ShowPointLocationList extends AppCompatActivity {

    protected RecyclerView recyclerView;

    protected AccessTokenRoomViewModel accessTokenRoomViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_point_location_list);

        //lv_saveLocations=findViewById(R.id.lv_showLocations);
        recyclerView= (RecyclerView) findViewById(R.id.lv_showLocations);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        accessTokenRoomViewModel=new ViewModelProvider(this).get(AccessTokenRoomViewModel.class);

//        String action=getIntent().getAction();
//        if(action!=null&& action.equals(Constants.ACTION_REPLAY_MY_ROUTES)) {
//            recyclerViewReplay=true;
//            getRoutesByUser();
//        }
//        else
//            if(action!=null && action.equals(Constants.VIEW_STATISTICS)){
//                String routeId=getIntent().getStringExtra(Constants.ROUTE_ID);
//               // getStatisticByRoute(routeId);
//            }
//            else  if(action!=null && action.equals(Constants.ACTION_VIEW_MY_ROUTES)){
//                recyclerViewReplay=false;
//                getRoutesByUser();
//            }

    }

}