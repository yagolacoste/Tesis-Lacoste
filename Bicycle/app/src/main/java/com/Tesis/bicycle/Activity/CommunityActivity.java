package com.Tesis.bicycle.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.Tesis.bicycle.Activity.ui.ViewPagerAdapter;
import com.Tesis.bicycle.Presenter.ListView.BattleListView;
import com.Tesis.bicycle.R;
import com.google.android.material.tabs.TabLayout;

public class CommunityActivity extends AppCompatActivity {
//
//    private ViewPager viewPager;
//    private TabLayout tabLayout;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_community);
//
//        viewPager=findViewById(R.id.viewPager);
//        tabLayout = findViewById(R.id.tabLayout);
//
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),CommunityActivity.this);
//        adapter.addActivity(AddFriendActivity.class, "Pestaña 1");
//        adapter.addActivity(BattleListView.class, "Pestaña 2");
//
//        // Configurar el adaptador en el ViewPager
//        viewPager.setAdapter(adapter);
//
//        // Conectar el TabLayout con el ViewPager
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                int position = tab.getPosition();
//                switch (position) {
//                    case 0: // Si se selecciona la pestaña 1, iniciar AddFriendActivity
//                        startActivity(new Intent(getApplicationContext(), AddFriendActivity.class));
//                        break;
//                    case 1: // Si se selecciona la pestaña 2, iniciar BattleListView
//                        startActivity(new Intent(getApplicationContext(), BattleListView.class));
//                        break;
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//
//            // Implementa los otros métodos de la interfaz OnTabSelectedListener según tus necesidades
//            // ...
//        });
//
//    }
}