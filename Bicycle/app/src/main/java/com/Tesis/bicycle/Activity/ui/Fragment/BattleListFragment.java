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
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.UserViewModel;

public class BattleListFragment extends BaseListViewFragment implements BattleRecyclerViewAdapter.OnItemClickListener {
    private static final String ARG_TAB_NAME = "ARG_TAB_NAME";
    private BattleRecyclerViewAdapter battleRecyclerViewAdapter;
    private UserViewModel userViewModel;


    public static BattleListFragment newInstance(@StringRes int tabName) {
        BattleListFragment frg = new BattleListFragment();

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
        imgLayoutEmpty.setImageResource(R.drawable.ic_competing_conver);
        setText("Your haven't battles");
        txtLayoutEmpty.setText(text);
        getListView();
        floatingactionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(), NewBattleActivity.class);
                startActivity(i);
            }

        });
        return view;
    }

    @Override
    public void getListView() {
        accessTokenRoomViewModel.getFirst().observe(getViewLifecycleOwner(),resp->{
            if(resp.getId()!=null){
                    userViewModel.getBattlesByUser(resp.getId()).observe(getViewLifecycleOwner(),response->{
                        if(!response.isEmpty()){
                            layoutEmpty.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            battleRecyclerViewAdapter=new BattleRecyclerViewAdapter(response);
                            battleRecyclerViewAdapter.setOnItemClickListener(this);
                            recyclerView.setAdapter(battleRecyclerViewAdapter);
                        }
                        else {
                            layoutEmpty.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    });
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
