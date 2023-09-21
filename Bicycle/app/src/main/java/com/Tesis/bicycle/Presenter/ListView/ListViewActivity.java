package com.Tesis.bicycle.Presenter.ListView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    protected ImageView imgLayoutEmptySelect;

    protected TextView txtLayoutEmptySelect;

    protected LinearLayout layoutEmptySelect;

    protected String text="Your haven't any route save";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        recyclerView= (RecyclerView) findViewById(R.id.rcvScrollView);
        imgLayoutEmptySelect=findViewById(R.id.imgLayoutEmptySelect);
        txtLayoutEmptySelect=findViewById(R.id.txtLayoutEmptySelect);
        layoutEmptySelect=findViewById(R.id.layoutEmptySelect);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        accessTokenRoomViewModel=new ViewModelProvider(this).get(AccessTokenRoomViewModel.class);
    }

   public abstract void getListView();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}