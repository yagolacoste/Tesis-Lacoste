package com.Tesis.bicycle.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.R;


public class Menu extends AppCompatActivity {


    private Button btn_free,btn_showPoint,btn_statistics;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn_free=findViewById(R.id.btn_free);
        btn_showPoint=findViewById(R.id.btn_showRoute);
        btn_statistics=findViewById(R.id.btn_statistics);

        btn_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Menu.this, TrackingActivity.class);
                startActivity(i);
            }
        });

        btn_showPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Menu.this, ShowPointLocationList.class);
                i.setAction(Constants.ACTION_REPLAY_ROUTE);
                startActivity(i);
            }
        });

        btn_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Menu.this, ShowPointLocationList.class);
                i.setAction(Constants.ACTION_VIEW_STATISTICS);
                startActivity(i);
            }
        });

    }
}