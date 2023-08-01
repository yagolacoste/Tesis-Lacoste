package com.Tesis.bicycle.Activity.ui.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModelProvider;

import com.Tesis.bicycle.Presenter.Adapter.ClassificationRecyclerAdapter;
import com.Tesis.bicycle.Presenter.Adapter.StatisticRecyclerViewAdapter;
import com.Tesis.bicycle.ViewModel.StatisticsViewModel;
import com.Tesis.bicycle.ViewModel.UserViewModel;

public class ClassificationFragment extends BaseListViewFragment{


    private static final String ARG_TAB_NAME = "ARG_TAB_NAME";

    private ClassificationRecyclerAdapter adapter;
    private StatisticsViewModel statisticsViewModel;

    public static ClassificationFragment newInstance(@StringRes int tabName) {
        ClassificationFragment frg = new ClassificationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TAB_NAME, tabName);
        frg.setArguments(args);

        return frg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);
        statisticsViewModel=new ViewModelProvider(requireActivity()).get(StatisticsViewModel.class);
        floatingactionbutton.setVisibility(View.INVISIBLE);
        floatingactionbutton.setActivated(false);
        getListView();
        return view;
    }

    @Override
    public void getListView() {
        accessTokenRoomViewModel.getFirst().observe(getViewLifecycleOwner(),response->{
            if(response.getAccessToken()!=null){
                statisticsViewModel.getAchievements().observe(getViewLifecycleOwner(), resp->{
                    if(!resp.isEmpty()){
                        adapter=new ClassificationRecyclerAdapter(resp);
                        recyclerView.setAdapter(adapter);
                    }
                    else
                        Toast.makeText(context,"Not exist classification: ",Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}
