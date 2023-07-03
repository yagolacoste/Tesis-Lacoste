package com.Tesis.bicycle.Activity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.Tesis.bicycle.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavInitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_init);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_inicio);
        bottomNavigationView.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.bottom_inicio:
                    return true;
                    //continue with UI
            }

            return false;
        });
    }
}