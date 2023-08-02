package com.Tesis.bicycle.Activity.ui.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.Battle.BattleDto;
import com.Tesis.bicycle.Dto.ApiRest.Statistics.StatisticsDto;
import com.Tesis.bicycle.Presenter.Adapter.RankingAdapter;
import com.Tesis.bicycle.R;

import java.util.List;

public class RankingFragment extends BaseListViewFragment{

    private List<StatisticsDto> ranking;

    private RankingAdapter rankingAdapter;

    private BattleDto battleDto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);
        Bundle args = getArguments();
        battleDto= (BattleDto) args.getSerializable(Constants.BATTLE_ITEM);
        floatingactionbutton.setVisibility(View.INVISIBLE);
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
               replaceFragment(new CommunityFragment());
            }
        });
        getListView();
        return view;
    }

    @Override
    public void getListView() {
        ranking=battleDto.getRanking();
        rankingAdapter=new RankingAdapter(ranking);
        recyclerView.setAdapter(rankingAdapter);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }



}