package com.Tesis.bicycle.Presenter.ListView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.StatisticsDto;
import com.Tesis.bicycle.Presenter.Adapter.StatisticRecyclerViewAdapter;
import com.Tesis.bicycle.ViewModel.StatisticsViewModel;

import java.util.List;

public class StatisticsListViewActivity extends ListViewActivity {
    List<StatisticsDto> statistics;
    private StatisticRecyclerViewAdapter adaptorStatistics;
    private StatisticsViewModel statisticsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        statisticsViewModel =new ViewModelProvider(this).get(StatisticsViewModel.class);
        floatingactionbutton.setVisibility(View.INVISIBLE);
        getListView();

    }

    @Override
    public void getListView() {
        String routeId=getIntent().getStringExtra(Constants.ROUTE_ID);
        accessTokenRoomViewModel.getFirst().observe(this,response->{
            if(response.getAccessToken()!=null){
                statisticsViewModel.getStatisticByRoute(routeId).observe(this, resp->{
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
