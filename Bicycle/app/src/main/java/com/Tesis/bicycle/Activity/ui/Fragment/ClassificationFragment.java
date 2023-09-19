package com.Tesis.bicycle.Activity.ui.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModelProvider;

import com.Tesis.bicycle.Dto.ApiRest.Statistics.ClassificationDto;
import com.Tesis.bicycle.Presenter.Adapter.ClassificationRecyclerAdapter;
import com.Tesis.bicycle.Presenter.Adapter.StatisticRecyclerViewAdapter;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.StatisticsViewModel;
import com.Tesis.bicycle.ViewModel.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ClassificationFragment extends BaseListViewFragment{


    private static final String ARG_TAB_NAME = "ARG_TAB_NAME";

    private List<ClassificationDto> classificationList;
    private ClassificationRecyclerAdapter adapter;
    private StatisticsViewModel statisticsViewModel;

    private FloatingActionButton fab_main,fab_cronometre,fab_speedometer,fab_battle,fab_distance;

    private LinearLayout layoutButtonMenuAction;
    protected float translationYaxis=100f;
    protected float alpha=0f;

    public static ClassificationFragment newInstance(@StringRes int tabName) {
        ClassificationFragment frg = new ClassificationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TAB_NAME, tabName);
        frg.setArguments(args);

        return frg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);
        statisticsViewModel=new ViewModelProvider(requireActivity()).get(StatisticsViewModel.class);
        fab_main=view.findViewById(R.id.fab_main);
        fab_cronometre=view.findViewById(R.id.fab_cronometre);
        fab_speedometer=view.findViewById(R.id.fab_speedometer);
        fab_battle=view.findViewById(R.id.fab_battle);
        fab_distance=view.findViewById(R.id.fab_distance);
        layoutButtonMenuAction=view.findViewById(R.id.layoutButtonMenuAction);
        fab_cronometre.setAlpha(alpha);
        fab_speedometer.setAlpha(alpha);
        fab_battle.setAlpha(alpha);
        fab_distance.setAlpha(alpha);
        fab_cronometre.setTranslationY(translationYaxis);
        fab_speedometer.setTranslationY(translationYaxis);
        fab_battle.setTranslationY(translationYaxis);
        fab_distance.setTranslationY(translationYaxis);
        floatingactionbutton.setVisibility(View.INVISIBLE);
        floatingactionbutton.setActivated(false);
       // layoutButtonMenuAction.setVisibility(View.VISIBLE);
        imgLayoutEmpty.setImageResource(R.drawable.ic_status);
        setText("You haven't classification");
        txtLayoutEmpty.setText(text);
        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(menuOpen){
                    closeMenu();
                }else openMenu();
            }
        });

        fab_battle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    List<ClassificationDto> sortedList =classificationList.stream()
                            .sorted(Comparator.comparingInt(c->c.getAchievementsDto().getBattleWinner()))
                            .collect(Collectors.toList());
                    Collections.reverse(sortedList); // Ordena en orden inverso

                    // Actualiza la lista original con la lista ordenada
                    classificationList.clear();
                    classificationList.addAll(sortedList);

                    adapter = new ClassificationRecyclerAdapter(classificationList);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
        fab_distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    List<ClassificationDto> sortedList = classificationList.stream()
                            .sorted(Comparator.comparing(c -> c.getAchievementsDto().getDistanceMax()))
                            .collect(Collectors.toList());

                    Collections.reverse(sortedList); // Ordena en orden inverso

                    // Actualiza la lista original con la lista ordenada
                    classificationList.clear();
                    classificationList.addAll(sortedList);

                    adapter = new ClassificationRecyclerAdapter(classificationList);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
        fab_cronometre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    List<ClassificationDto> sortedList =  classificationList.stream()
                            .sorted(Comparator.comparing(achievement ->
                                    achievement.getAchievementsDto().getTimeMin()))
                            .collect(Collectors.toList());
//                    Collections.reverse(sortedList); // Ordena en orden inverso

                    // Actualiza la lista original con la lista ordenada
                    classificationList.clear();
                    classificationList.addAll(sortedList);

                    adapter = new ClassificationRecyclerAdapter(classificationList);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
        fab_speedometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    List<ClassificationDto> sortedList = classificationList.stream()
                            .sorted(Comparator.comparingDouble(c->c.getAchievementsDto().getSpeedMax()))
                            .collect(Collectors.toList());
                    Collections.reverse(sortedList); // Ordena en orden inverso

                    // Actualiza la lista original con la lista ordenada
                    classificationList.clear();
                    classificationList.addAll(sortedList);

                    adapter = new ClassificationRecyclerAdapter(classificationList);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
        getListView();
        return view;
    }

    private void openMenu() {
        menuOpen=!menuOpen;
        fab_main.setImageResource(R.drawable.ic_close);
        //fab_main.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fab_cronometre.animate().translationY(alpha).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fab_speedometer.animate().translationY(alpha).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fab_battle.animate().translationY(alpha).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fab_distance.animate().translationY(alpha).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
    }

    private void closeMenu() {
        menuOpen=!menuOpen;
        fab_main.setImageResource(R.drawable.ic_filter);
        //fab_main.animate().translationY(100f).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        fab_cronometre.animate().translationY(translationYaxis).alpha(alpha).setInterpolator(interpolator).setDuration(300).start();
        fab_speedometer.animate().translationY(translationYaxis).alpha(alpha).setInterpolator(interpolator).setDuration(300).start();
        fab_battle.animate().translationY(translationYaxis).alpha(alpha).setInterpolator(interpolator).setDuration(300).start();
        fab_distance.animate().translationY(translationYaxis).alpha(alpha).setInterpolator(interpolator).setDuration(300).start();
    }

    @Override
    public void getListView() {
        accessTokenRoomViewModel.getFirst().observe(getViewLifecycleOwner(),response->{
            if(response.getAccessToken()!=null){
                statisticsViewModel.getAchievements(response.getId()).observe(getViewLifecycleOwner(), resp->{
                    if(!resp.isEmpty()){
                        layoutEmpty.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        layoutButtonMenuAction.setVisibility(View.VISIBLE);
//                        fab_main.setVisibility(View.VISIBLE);
//                        fab_cronometre.setVisibility(View.VISIBLE);
//                        fab_speedometer.setVisibility(View.VISIBLE);
//                        fab_battle.setVisibility(View.VISIBLE);
//                        fab_distance.setVisibility(View.VISIBLE);
                        classificationList = resp;
                        adapter=new ClassificationRecyclerAdapter(resp);
                        recyclerView.setAdapter(adapter);
                    }
                    else{
                        layoutEmpty.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
//                        fab_main.setVisibility(View.GONE);
//                        fab_cronometre.setVisibility(View.GONE);
//                        fab_speedometer.setVisibility(View.GONE);
//                        fab_battle.setVisibility(View.GONE);
//                        fab_distance.setVisibility(View.GONE);
                        layoutButtonMenuAction.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}
