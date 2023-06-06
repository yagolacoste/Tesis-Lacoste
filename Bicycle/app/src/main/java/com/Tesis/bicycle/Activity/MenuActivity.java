package com.Tesis.bicycle.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Presenter.ListView.RouteListView;
import com.Tesis.bicycle.R;


public class MenuActivity extends AppCompatActivity {


    private Button btn_free,btn_showPoint,btn_run,btnFriend;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn_free=findViewById(R.id.btn_free);
        btn_showPoint=findViewById(R.id.btn_showRoute);
        btn_run=findViewById(R.id.btnRun);
        btnFriend=findViewById(R.id.btnFriend);


        btn_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MenuActivity.this, TrackingActivity.class);
                startActivity(i);
            }
        });

        btn_showPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MenuActivity.this, RouteListView.class);
                i.setAction(Constants.ACTION_REPLAY_MY_ROUTES);
                startActivity(i);
            }
        });

        btn_run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MenuActivity.this, ListBattleActivity.class);
//                i.setAction(Constants.ACTION_REPLAY_MY_ROUTES);
                startActivity(i);
            }
        });
        btnFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuActivity.this, AddFriendActivity.class);
//                i.setAction(Constants.ACTION_REPLAY_MY_ROUTES);
                startActivity(i);
            }
        });
    }
}