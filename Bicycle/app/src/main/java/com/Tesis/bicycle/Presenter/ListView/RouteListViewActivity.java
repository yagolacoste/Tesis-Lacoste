package com.Tesis.bicycle.Presenter.ListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.Tesis.bicycle.Activity.ListViewActivity;
import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Presenter.Adapter.MyRoutesRecyclerViewAdapter;
import com.Tesis.bicycle.Presenter.Adapter.RouteRecyclerViewAdapter;
import com.Tesis.bicycle.ViewModel.StatisticsViewModel;

import java.util.List;

public class RouteListViewActivity extends ListViewActivity implements MyRoutesRecyclerViewAdapter.OnItemClickListener{

    List<RouteDetailsDto> routes;
    private RouteRecyclerViewAdapter adaptorRoute;

    private MyRoutesRecyclerViewAdapter recyclerRoutesDetails;

    private StatisticsViewModel statisticsViewModel;

    boolean recyclerViewReplay=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statisticsViewModel =new ViewModelProvider(this).get(StatisticsViewModel.class);
        String action=getIntent().getAction();
        if(action!=null&& action.equals(Constants.ACTION_REPLAY_MY_ROUTES)) {
            recyclerViewReplay=true;
            getRoutesByUser();
        }
            else  if(action!=null && action.equals(Constants.ACTION_VIEW_MY_ROUTES)){
                recyclerViewReplay=false;
                getRoutesByUser();
            }
    }

    public void getRoutesByUser() {
        accessTokenRoomViewModel.getFirst().observe(this,response->{
            if(response.getAccessToken()!=null){
                statisticsViewModel.getRouteById(response.getId()).observe(this, resp->{
                    if(!resp.getRoutes().isEmpty()){
                        routes = resp.getRoutes();
                        if(recyclerViewReplay){
                            adaptorRoute = new RouteRecyclerViewAdapter(routes);
                            recyclerView.setAdapter(adaptorRoute);
                        } else{
                            recyclerRoutesDetails = new MyRoutesRecyclerViewAdapter(routes);
                            recyclerRoutesDetails.setOnItemClickListener(this);
                            recyclerView.setAdapter(recyclerRoutesDetails);
                        }
                    }else {
                        Toast.makeText(this, "Not exist routes for user: " + resp.getUserId(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public void onItemClick(RouteDetailsDto ruta) {
        Intent resultIntent = new Intent();
        resultIntent.setAction(Constants.ROUTE_SELECT);
        resultIntent.putExtra(Constants.ROUTE_ITEM, ruta);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
