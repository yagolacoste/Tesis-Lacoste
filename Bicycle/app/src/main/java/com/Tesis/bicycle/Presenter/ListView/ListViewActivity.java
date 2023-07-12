package com.Tesis.bicycle.Presenter.ListView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.AccessTokenRoomViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public  abstract class ListViewActivity extends AppCompatActivity {

    protected RecyclerView recyclerView;

    protected AccessTokenRoomViewModel accessTokenRoomViewModel;

    protected FloatingActionButton floatingactionbutton;

//    protected TabLayout tabLayout;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        //lv_saveLocations=findViewById(R.id.lv_showLocations);
        recyclerView= (RecyclerView) findViewById(R.id.rcvScrollView);
        floatingactionbutton=findViewById(R.id.btnAdd);
//        tabLayout = findViewById(R.id.tabLayout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        accessTokenRoomViewModel=new ViewModelProvider(this).get(AccessTokenRoomViewModel.class);
    }

   public abstract void getListView();

}