package com.Tesis.bicycle.Activity.ui.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.Tesis.bicycle.Activity.ui.NavInitActivity;
import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.Statistics.ClassificationDto;
import com.Tesis.bicycle.Dto.ApiRest.Statistics.StatisticsDto;
import com.Tesis.bicycle.Presenter.Adapter.StatisticRecyclerViewAdapter;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.StatisticsViewModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticsFragment  extends BaseListViewFragment {

    List<StatisticsDto> statistics;

    private StatisticRecyclerViewAdapter adaptorStatistics;
    private StatisticsViewModel statisticsViewModel;

    private String routeSelect;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);
       // ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        statisticsViewModel =new ViewModelProvider(requireActivity()).get(StatisticsViewModel.class);
        floatingactionbutton.setVisibility(View.INVISIBLE);
        Bundle args = getArguments();
        if (args != null) {
            routeSelect = args.getString(Constants.ROUTE_ID);
        }
        imgLayoutEmpty.setImageAlpha(R.drawable.ic_statistics);
        setText("Your haven't any route save");
        txtLayoutEmpty.setText(text);
        getListView();
        return view;
    }

    @Override
    public void getListView() {
        accessTokenRoomViewModel.getFirst().observe(getViewLifecycleOwner(),response->{
            if(response.getAccessToken()!=null){
                statisticsViewModel.getStatisticByRoute(routeSelect).observe(getViewLifecycleOwner(), resp->{
                    if(!resp.isEmpty()){
                        layoutEmpty.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        statistics = resp;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            statistics =statistics.stream()
                                    .sorted(Comparator.comparing(c->c.getTimeCreated()))
                                    .collect(Collectors.toList());
                        }
                            Collections.reverse(statistics); // Ordena en orden inverso
                        adaptorStatistics=new StatisticRecyclerViewAdapter(statistics);
                        recyclerView.setAdapter(adaptorStatistics);
                    }
                    else{
                        layoutEmpty.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                });
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                backToMenuActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void backToMenuActivity() {
        replaceFragment(new MyMapsFragment());
    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }



}
