package com.Tesis.bicycle.Presenter.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.Tesis.bicycle.Activity.NewBattleActivity;
import com.Tesis.bicycle.Dto.ApiRest.Battle.BattleDto;
import com.Tesis.bicycle.Presenter.Adapter.BattleRecyclerViewAdapter;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.AccessTokenRoomViewModel;
import com.Tesis.bicycle.ViewModel.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;

public class BattleListView extends AppCompatActivity {

    private BattleRecyclerViewAdapter battleRecyclerViewAdapter;
    private RecyclerView recyclerView;
    private UserViewModel userViewModel;
    private AccessTokenRoomViewModel accessTokenRoomViewModel;

    private FloatingActionButton floatingactionbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_battle);
        init();
    }

    private void init(){
        recyclerView= (RecyclerView) findViewById(R.id.rcvListBattle);
        floatingactionbutton=findViewById(R.id.btnAddBattle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userViewModel=new ViewModelProvider(this).get(UserViewModel.class);
        accessTokenRoomViewModel=new ViewModelProvider(this).get(AccessTokenRoomViewModel.class);
        accessTokenRoomViewModel.getFirst().observe(this,resp->{
            if(resp.getId()!=null){
                try {
                    userViewModel.getBattlesByUser(resp.getId()).observe(this,response->{
                        if(response!=null){
                            battleRecyclerViewAdapter=new BattleRecyclerViewAdapter(response);
                            recyclerView.setAdapter(battleRecyclerViewAdapter);
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        floatingactionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(), NewBattleActivity.class);
                startActivity(i);
            }
        });

    }
}