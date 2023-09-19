package com.Tesis.bicycle.Activity.ui.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.pm.PermissionInfoCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.AccessTokenRoomViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public abstract class BaseListViewFragment extends Fragment {

    protected RecyclerView recyclerView;

    protected AccessTokenRoomViewModel accessTokenRoomViewModel;

    protected FloatingActionButton floatingactionbutton;

    protected Boolean menuOpen=false;

    protected Context context;

    private SwipeRefreshLayout swipeRefreshLayout;

    protected ImageView imgLayoutEmpty;

    protected TextView txtLayoutEmpty;

    protected LinearLayout layoutEmpty;

    protected String text="Your haven't any route save";


    protected OvershootInterpolator interpolator=new OvershootInterpolator();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context=getActivity().getApplicationContext();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_base_list_view, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.rcvScrollViewFr);
        floatingactionbutton=view.findViewById(R.id.btnAddFr);
        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        imgLayoutEmpty=view.findViewById(R.id.imgLayoutEmpty);
        txtLayoutEmpty=view.findViewById(R.id.txtLayoutEmpty);
        layoutEmpty=view.findViewById(R.id.layoutEmpty);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        accessTokenRoomViewModel=new ViewModelProvider(this).get(AccessTokenRoomViewModel.class);




        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public abstract void getListView();

}