package com.Tesis.bicycle.Activity.ui.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.Statistics.StatisticsDto;
import com.Tesis.bicycle.Presenter.Adapter.StatisticRecyclerViewAdapter;
import com.Tesis.bicycle.ViewModel.StatisticsViewModel;

import java.util.List;

public class StatisticsFragment  extends BaseListViewFragment {

    List<StatisticsDto> statistics;

    private StatisticRecyclerViewAdapter adaptorStatistics;
    private StatisticsViewModel statisticsViewModel;

    private String routeSelect;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);
        statisticsViewModel =new ViewModelProvider(requireActivity()).get(StatisticsViewModel.class);
        floatingactionbutton.setVisibility(View.INVISIBLE);
        Bundle args = getArguments();
        if (args != null) {
            routeSelect = args.getString(Constants.ROUTE_ID);
            // Haz lo que necesites con el routeId
        }
        getListView();
        return view;
    }

    @Override
    public void getListView() {
        accessTokenRoomViewModel.getFirst().observe(getViewLifecycleOwner(),response->{
            if(response.getAccessToken()!=null){
                statisticsViewModel.getStatisticByRoute(routeSelect).observe(getViewLifecycleOwner(), resp->{
                    if(!resp.isEmpty()){
                        statistics = resp;
                        adaptorStatistics=new StatisticRecyclerViewAdapter(statistics);
                        recyclerView.setAdapter(adaptorStatistics);
                    }
                    else
                        Toast.makeText(context,"Not exist statistics for route: ",Toast.LENGTH_SHORT).show();
                });
            }
        });
    }


}
