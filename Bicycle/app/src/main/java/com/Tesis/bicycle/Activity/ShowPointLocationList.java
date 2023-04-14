package com.Tesis.bicycle.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.AppUserHasRouteDetailsDto;
import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Dto.ApiRest.StatisticsDto;
import com.Tesis.bicycle.Presenter.ApiRestConecction;
import com.Tesis.bicycle.Presenter.Adapter.RouteRecyclerViewAdapter;
import com.Tesis.bicycle.Presenter.Adapter.StatisticRecyclerViewAdapter;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.Repository.AppUserHasRouteApiRestRepository;
import com.Tesis.bicycle.Service.ApiRest.AppUserHasRouteRestService;
import com.Tesis.bicycle.ViewModel.AccessTokenRoomViewModel;
import com.Tesis.bicycle.ViewModel.AppUserHasRouteViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowPointLocationList extends AppCompatActivity {

    List<RouteDetailsDto>routes;
    List<StatisticsDto>statistics;

    private RecyclerView recyclerView;
    private RouteRecyclerViewAdapter adaptorRoute;
    private StatisticRecyclerViewAdapter adaptorStatistics;
    private AccessTokenRoomViewModel accessTokenRoomViewModel;
    private AppUserHasRouteViewModel appUserHasRouteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_point_location_list);

        //lv_saveLocations=findViewById(R.id.lv_showLocations);
        recyclerView= (RecyclerView) findViewById(R.id.lv_showLocations);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        accessTokenRoomViewModel=new ViewModelProvider(this).get(AccessTokenRoomViewModel.class);
        appUserHasRouteViewModel=new ViewModelProvider(this).get(AppUserHasRouteViewModel.class);


        String action=getIntent().getAction();
        if(action!=null&& action.equals(Constants.ACTION_REPLAY_MY_ROUTES))
            getRoutesByUser();
        else
            if(action!=null && action.equals(Constants.VIEW_STATISTICS)){
                String routeId=getIntent().getStringExtra(Constants.ROUTE_ID);
                getStatisticByRoute(routeId);
            }


    }

    ///Lista las rutas por usuarios
    public void  getRoutesByUser() {
        accessTokenRoomViewModel.getFirst().observe(this,response->{
            if(response.getAccessToken()!=null){
                appUserHasRouteViewModel.getRouteById(response.getId()).observe(this,resp->{
                    if(!resp.getRoutes().isEmpty()){
                        routes = resp.getRoutes();
                        adaptorRoute = new RouteRecyclerViewAdapter(routes);
                        recyclerView.setAdapter(adaptorRoute);
                    }
                    else
                        Toast.makeText(this,"Not exist routes for user: "+resp.getUserId(),Toast.LENGTH_SHORT).show();
                });
            }
        });

    }

    public void getStatisticByRoute(String routeId){//se va a cambiar cuando haya varios usuarios
        accessTokenRoomViewModel.getFirst().observe(this,response->{
            if(response.getAccessToken()!=null){
                appUserHasRouteViewModel.getStatisticByRoute(routeId).observe(this,resp->{
                    if(!resp.isEmpty()){
                        statistics = resp;
                        adaptorStatistics=new StatisticRecyclerViewAdapter(statistics);
                        recyclerView.setAdapter(adaptorStatistics);
                    }
                    else
                        Toast.makeText(this,"Not exist statistics for route: ",Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

}