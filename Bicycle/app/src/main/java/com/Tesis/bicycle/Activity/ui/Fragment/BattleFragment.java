package com.Tesis.bicycle.Activity.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModelProvider;

import com.Tesis.bicycle.Activity.NewBattleActivity;
import com.Tesis.bicycle.Activity.TrackingActivity;
import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.Battle.BattleDto;
import com.Tesis.bicycle.Presenter.Adapter.BattleRecyclerViewAdapter;
import com.Tesis.bicycle.Presenter.Adapter.OnItemClickListener;
import com.Tesis.bicycle.ViewModel.StatisticsViewModel;
import com.Tesis.bicycle.ViewModel.UserViewModel;

import java.io.IOException;
import java.util.List;

public class BattleFragment extends BaseListViewFragment implements BattleRecyclerViewAdapter.OnItemClickListener {
    private static final String ARG_TAB_NAME = "ARG_TAB_NAME";
    private BattleRecyclerViewAdapter battleRecyclerViewAdapter;
    private UserViewModel userViewModel;

    public static BattleFragment newInstance(@StringRes int tabName) {
        BattleFragment frg = new BattleFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_TAB_NAME, tabName);
        frg.setArguments(args);

        return frg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);
        userViewModel=new ViewModelProvider(this).get(UserViewModel.class);
        floatingactionbutton.setActivated(true);
         getListView();
        floatingactionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent i=new Intent(view.getContext(), NewBattleActivity.class);
               // startActivity(i);
            }
        });
        return view;
    }

    @Override
    public void getListView() {
        accessTokenRoomViewModel.getFirst().observe(getViewLifecycleOwner(),resp->{
            if(resp.getId()!=null){
                try {
                    userViewModel.getBattlesByUser(resp.getId()).observe(getViewLifecycleOwner(),response->{
                        if(response!=null){
                            battleRecyclerViewAdapter=new BattleRecyclerViewAdapter(response,resp.getId());
                            battleRecyclerViewAdapter.setOnItemClickListener(this);
                            recyclerView.setAdapter(battleRecyclerViewAdapter);
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void onItemClick(BattleDto battleDto) {
        Intent resultIntent = new Intent(context, TrackingActivity.class);
        resultIntent.setAction(Constants.REPLAY_BATTLE);
        resultIntent.putExtra(Constants.BATTLE_ITEM, battleDto);
        startActivity(resultIntent);
    }
}
