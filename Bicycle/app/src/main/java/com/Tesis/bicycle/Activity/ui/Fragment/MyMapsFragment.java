package com.Tesis.bicycle.Activity.ui.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.Tesis.bicycle.Activity.MapActivityActivity;
import com.Tesis.bicycle.Activity.TrackingActivity;
import com.Tesis.bicycle.Activity.ui.NavInitActivity;
import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Presenter.Adapter.MyRoutesRecyclerViewAdapter;
import com.Tesis.bicycle.Presenter.Adapter.OnItemClickListener;
import com.Tesis.bicycle.Presenter.Adapter.RouteRecyclerViewAdapter;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.StatisticsViewModel;

import java.util.List;

public class MyMapsFragment extends BaseListViewFragment implements OnItemClickListener{

    List<RouteDetailsDto> routes;

    private RouteRecyclerViewAdapter adaptorRoute;

    private StatisticsViewModel statisticsViewModel;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);
        statisticsViewModel =new ViewModelProvider(requireActivity()).get(StatisticsViewModel.class);
        floatingactionbutton.setVisibility(View.INVISIBLE);
        imgLayoutEmpty.setImageAlpha(R.drawable.ic_route);
        setText("Your haven't any route save");
        txtLayoutEmpty.setText(text);
        getListView();
        return view;
    }

    @Override
    public void getListView() {
        accessTokenRoomViewModel.getFirst().observe(getViewLifecycleOwner(),response->{
            if(response.getAccessToken()!=null){
                statisticsViewModel.getRouteById(response.getId()).observe(getViewLifecycleOwner(), resp->{
                    //checkEmpty();
                    if(!resp.isEmpty()){
                        routes = resp;
                        layoutEmpty.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        adaptorRoute = new RouteRecyclerViewAdapter(routes);
                        adaptorRoute.setListener(this);
                        recyclerView.setAdapter(adaptorRoute);
                    }else {
                        Toast.makeText(context, "Not exist routes for user: " + response.getId(), Toast.LENGTH_SHORT).show();
                        layoutEmpty.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                });
            }
        });
    }


    @Override
    public void onItemClick(RouteDetailsDto ruta) {
        Intent resultIntent = new Intent(context, MapActivityActivity.class);
        resultIntent.putExtra(Constants.ROUTE, ruta);
        startActivity(resultIntent);
    }
}
