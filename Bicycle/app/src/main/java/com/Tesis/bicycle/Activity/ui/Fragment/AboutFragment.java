package com.Tesis.bicycle.Activity.ui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Tesis.bicycle.Presenter.Adapter.RouteRecyclerViewAdapter;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.StatisticsViewModel;
import com.Tesis.bicycle.ViewModel.TrackedRoomViewModel;

public class AboutFragment  extends Fragment{

    private TrackedRoomViewModel trackedRoomViewModel;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
//        View view= super.onCreateView(inflater, container, savedInstanceState);
//        this.trackedRoomViewModel=new ViewModelProvider(requireActivity()).get(TrackedRoomViewModel.class);
//        floatingactionbutton.setVisibility(View.INVISIBLE);
//        imgLayoutEmpty.setImageAlpha(R.drawable.ic_route);//icono de la tortuga
//        setText(String.valueOf(R.string.about_fragment));
//        txtLayoutEmpty.setText(text);
////        getListView();
//        return view;
    }

//        @Override
//        public void getListView() {
//            accessTokenRoomViewModel.getFirst().observe(getViewLifecycleOwner(),response->{
//                if(response.getAccessToken()!=null){
//                    trackedRoomViewModel.getAll().observe(getViewLifecycleOwner(), resp->{
//                        //checkEmpty();
//                        if(!resp.isEmpty()){
//                            layoutEmpty.setVisibility(View.GONE);
//                            recyclerView.setVisibility(View.VISIBLE);
////                            adaptorRoute = new RouteRecyclerViewAdapter(routes);
////                            adaptorRoute.setListener(this);
////                            recyclerView.setAdapter(adaptorRoute);
//
//                        }else {
//                            Toast.makeText(context, "Not exist routes for user: " + response.getId(), Toast.LENGTH_SHORT).show();
//                            layoutEmpty.setVisibility(View.VISIBLE);
//                            recyclerView.setVisibility(View.GONE);
//                        }
//                    });
//                }
//            });
//        }
    }