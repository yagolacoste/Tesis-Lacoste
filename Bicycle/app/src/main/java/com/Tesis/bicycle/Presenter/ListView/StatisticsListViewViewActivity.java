package com.Tesis.bicycle.Presenter.ListView;

import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.Tesis.bicycle.Activity.ListViewActivity;
import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.StatisticsDto;
import com.Tesis.bicycle.Presenter.Adapter.StatisticRecyclerViewAdapter;
import com.Tesis.bicycle.ViewModel.AppUserHasRouteViewModel;

import java.util.List;

public class StatisticsListViewViewActivity extends ListViewActivity {
    List<StatisticsDto> statistics;
    private StatisticRecyclerViewAdapter adaptorStatistics;
    private AppUserHasRouteViewModel appUserHasRouteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appUserHasRouteViewModel=new ViewModelProvider(this).get(AppUserHasRouteViewModel.class);
        String id=getIntent().getStringExtra(Constants.ROUTE_ID);
        getStatisticByRoute(id);

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
