package com.Tesis.bicycle.Activity.ui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import com.Tesis.bicycle.Dto.ApiRest.Statistics.StatisticsApiRest;
import com.Tesis.bicycle.Dto.Room.TrackedDto;
import com.Tesis.bicycle.Presenter.Adapter.RouteRecyclerViewAdapter;
import com.Tesis.bicycle.Presenter.Adapter.TrackedRecyclerViewAdapter;
import com.Tesis.bicycle.Presenter.Notifications;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.StatisticsViewModel;
import com.Tesis.bicycle.ViewModel.TrackedRoomViewModel;

import java.util.List;

public class AboutFragment  extends BaseListViewFragment{

    private TrackedRoomViewModel trackedRoomViewModel;

    private TrackedRecyclerViewAdapter adaptorRoute;

    private StatisticsViewModel statisticsViewModel;

    private Notifications notifications;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_about, container, false);
        View view= super.onCreateView(inflater, container, savedInstanceState);
        this.trackedRoomViewModel=new ViewModelProvider(requireActivity()).get(TrackedRoomViewModel.class);
        this.statisticsViewModel=new ViewModelProvider(requireActivity()).get(StatisticsViewModel.class);
        this.notifications=new Notifications(requireActivity());
        floatingactionbutton.setVisibility(View.INVISIBLE);
        imgLayoutEmpty.setImageResource(R.drawable.ic_torgue_edit);//icono de la tortuga

        setText(getString(R.string.about_fragment));
        txtLayoutEmpty.setText(text);

        getListView();
        return view;
    }

        @Override
            public void getListView() {
                trackedRoomViewModel.getAll().observe(getViewLifecycleOwner(), new Observer<List<TrackedDto>>() {
                    @Override
                    public void onChanged(List<TrackedDto> resp) {
                        // Este método se llama cuando hay cambios en la lista
                        if (!resp.isEmpty()) {
                            layoutEmpty.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            adaptorRoute = new TrackedRecyclerViewAdapter(resp, getContext(), AboutFragment.this);
                            adaptorRoute.setListener(new TrackedRecyclerViewAdapter.OnItemClickListener() {
                                @Override
                                public void onDiscardClick(int position) {
                                    updateListView(position);
                                }

                                @Override
                                public void onSaveCardClick(int position) {
                                    TrackedDto trackedDto = adaptorRoute.getResp().get(position);
                                    StatisticsApiRest statisticsApiRest = new StatisticsApiRest(trackedDto);
                                    statisticsViewModel.addStatistic(statisticsApiRest).observe(getViewLifecycleOwner(),resp->{
                                        if(resp){
                                            notifications.successMessage("Excellent!!!","You added other route ");
                                            updateListView(position);
                                        }else {
                                            notifications.warningMessage("Your not connexion! you");
                                        }
                                    });


                                }
                            });
                            recyclerView.setAdapter(adaptorRoute);
                        } else {
                            Toast.makeText(context, "Not exist routes for user: " + 1, Toast.LENGTH_SHORT).show();
                            layoutEmpty.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }
                });
            }


            private void updateListView(int position){
                TrackedDto trackedDto = adaptorRoute.getResp().get(position);
                trackedRoomViewModel.deleteById(trackedDto.getRouteId());
                adaptorRoute.updateList(trackedRoomViewModel.getAll().getValue());
                if(trackedRoomViewModel.getAll().getValue().isEmpty()){
                    layoutEmpty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
    }