package com.Tesis.bicycle.Activity.ui.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.Tesis.bicycle.Presenter.Adapter.RouteRecyclerViewAdapter;
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
        imgLayoutEmpty.setImageAlpha(R.drawable.ic_ranking_30);
        setText("There isn't a classification");
        txtLayoutEmpty.setText(text);
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
        replaceFragment(new CommunityFragment());
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }



}
