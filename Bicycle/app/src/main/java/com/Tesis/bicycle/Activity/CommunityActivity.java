package com.Tesis.bicycle.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.Tesis.bicycle.Activity.ui.ViewPagerAdapter;
import com.Tesis.bicycle.Presenter.ListView.BattleListView;
import com.Tesis.bicycle.R;
import com.google.android.material.tabs.TabLayout;

public class CommunityActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        viewPager=findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),CommunityActivity.this);
        adapter.addFragment(AddFriendActivity.class, "Pestaña 1");
        adapter.addFragment(BattleListView.class, "Pestaña 2");

        // Configurar el adaptador en el ViewPager
        viewPager.setAdapter(adapter);

        // Conectar el TabLayout con el ViewPager
        tabLayout.setupWithViewPager(viewPager);
    }
}