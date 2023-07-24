package com.Tesis.bicycle.Presenter.ListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Presenter.Adapter.MyRoutesRecyclerViewAdapter;
import com.Tesis.bicycle.Presenter.Adapter.OnItemClickListener;
import com.Tesis.bicycle.Presenter.Adapter.RouteRecyclerViewAdapter;
import com.Tesis.bicycle.ViewModel.StatisticsViewModel;

import java.util.List;

public class RouteListViewActivity extends ListViewActivity implements OnItemClickListener {

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
        getListView();

    }

    @Override
    public void getListView() {
        accessTokenRoomViewModel.getFirst().observe(this,response->{
            if(response.getAccessToken()!=null){
                statisticsViewModel.getRouteById(response.getId()).observe(this, resp->{
                    if(!resp.isEmpty()){
                            routes = resp;
                            recyclerRoutesDetails = new MyRoutesRecyclerViewAdapter(routes);
                            recyclerRoutesDetails.setOnItemClickListener(this);
                            recyclerView.setAdapter(recyclerRoutesDetails);
                    }else {
                        Toast.makeText(this, "Not exist routes for user: " + response.getId(), Toast.LENGTH_SHORT).show();
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
