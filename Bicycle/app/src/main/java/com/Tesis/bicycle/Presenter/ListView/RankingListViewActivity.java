package com.Tesis.bicycle.Presenter.ListView;

import android.os.Bundle;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.Battle.BattleDto;
import com.Tesis.bicycle.Dto.ApiRest.StatisticsDto;
import com.Tesis.bicycle.Presenter.Adapter.RankingAdapter;

import java.util.List;

public class RankingListViewActivity extends ListViewActivity {

    private List<StatisticsDto> ranking;

    private RankingAdapter rankingAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BattleDto battleDto= (BattleDto) getIntent().getSerializableExtra(Constants.BATTLE_ITEM);
        getRanking(battleDto);
    }

    private void getRanking(BattleDto battleDto) {
        ranking=battleDto.getRanking();
        rankingAdapter=new RankingAdapter(ranking);
        recyclerView.setAdapter(rankingAdapter);
    }
}
