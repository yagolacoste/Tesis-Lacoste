package com.Tesis.bicycle.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
import com.Tesis.bicycle.Service.ApiRest.AppUserHasRouteRestService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowPointLocationList extends Activity {

    List<RouteDetailsDto>routes;
    List<StatisticsDto>statistics;

    private RecyclerView recyclerView;
    private RouteRecyclerViewAdapter adaptorRoute;
    private StatisticRecyclerViewAdapter adaptorStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_point_location_list);

        //lv_saveLocations=findViewById(R.id.lv_showLocations);
        recyclerView= (RecyclerView) findViewById(R.id.lv_showLocations);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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
        AppUserHasRouteRestService appUserHasRouteRestService = ApiRestConecction.getServiceAppUserHasRoute(getApplicationContext());
        Call<AppUserHasRouteDetailsDto> call = appUserHasRouteRestService.getRouteById(1L);//es el usuario 1 por defecto
        call.enqueue(new Callback<AppUserHasRouteDetailsDto>() {
            @Override
            public void onResponse(Call<AppUserHasRouteDetailsDto> call, Response<AppUserHasRouteDetailsDto> response) {
                if (response.isSuccessful()) {
                    routes = response.body().getRoutes();
                    // lv_saveLocations.setAdapter(new ArrayAdapter<RouteDetailsDto>(ShowPointLocationList.this, android.R.layout.));
                    adaptorRoute = new RouteRecyclerViewAdapter(routes);
                    recyclerView.setAdapter(adaptorRoute);
                }
            }
//
            @Override
            public void onFailure(Call<AppUserHasRouteDetailsDto> call, Throwable t) {

                    Toast.makeText(getApplicationContext(),"NO hay conexion a internet",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getStatisticByRoute(String routeId){
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AppUserHasRouteRestService appUserHasRouteRestService = ApiRestConecction.getServiceAppUserHasRoute(getApplicationContext());
        Call<List<StatisticsDto>> call = appUserHasRouteRestService.getStatisticsByRoute(routeId);
        call.enqueue(new Callback<List<StatisticsDto>>() {
            @Override
            public void onResponse(Call<List<StatisticsDto>> call, Response<List<StatisticsDto>> response) {
                if(response.isSuccessful()){
                    statistics=response.body();
                    adaptorStatistics=new StatisticRecyclerViewAdapter(statistics);
                    recyclerView.setAdapter(adaptorStatistics);
                }
            }

            @Override
            public void onFailure(Call<List<StatisticsDto>> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }

}