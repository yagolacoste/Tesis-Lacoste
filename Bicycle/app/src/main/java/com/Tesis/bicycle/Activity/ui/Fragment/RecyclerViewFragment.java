package com.Tesis.bicycle.Activity.ui.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.StatisticsDto;
import com.Tesis.bicycle.Presenter.Adapter.StatisticRecyclerViewAdapter;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.AccessTokenRoomViewModel;
import com.Tesis.bicycle.ViewModel.StatisticsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFragment extends Fragment {
    Context context;
    protected RecyclerView recyclerView;

    protected AccessTokenRoomViewModel accessTokenRoomViewModel;

    protected FloatingActionButton floatingactionbutton;

    private StatisticRecyclerViewAdapter adaptorStatistics;
    private StatisticsViewModel statisticsViewModel;

    List<StatisticsDto> statistics;

    private String name;



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context=getActivity().getApplicationContext();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_recycler_view, container, false);

         recyclerView = layout.findViewById(R.id.rcvScrollView);
        floatingactionbutton=layout.findViewById(R.id.btnAdd);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        accessTokenRoomViewModel=new ViewModelProvider(this).get(AccessTokenRoomViewModel.class);
        statisticsViewModel =new ViewModelProvider(this).get(StatisticsViewModel.class);
        floatingactionbutton.setVisibility(View.INVISIBLE);
        readInfomration();

        return layout;
    }

    private void readInfomration() {
    String routeId="1-pnxZ0yRAio";
//        String routeId=getIntent().getStringExtra(Constants.ROUTE_ID);
        accessTokenRoomViewModel.getFirst().observe(getViewLifecycleOwner(), response->{
            if(response.getAccessToken()!=null){
                statisticsViewModel.getStatisticByRoute(routeId).observe(getViewLifecycleOwner(), resp->{
                    if(!resp.isEmpty()){
                        statistics = resp;
                        adaptorStatistics=new StatisticRecyclerViewAdapter(statistics);
                        recyclerView.setAdapter(adaptorStatistics);
                    }
                    else
                        Toast.makeText(getContext(),"Not exist statistics for route: ",Toast.LENGTH_SHORT).show();
                });
            }
        });
    }


}