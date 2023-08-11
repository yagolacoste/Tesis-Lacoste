package com.Tesis.bicycle.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Presenter.OpenStreetMap;
import com.Tesis.bicycle.R;

import org.osmdroid.views.MapView;

public class MapActivityActivity extends AppCompatActivity {

    private OpenStreetMap openStreetMap;

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_activity);
        mapView=findViewById(R.id.mapView);
        openStreetMap=new OpenStreetMap(mapView);
        RouteDetailsDto routeDetailsDto= (RouteDetailsDto) getIntent().getSerializableExtra(Constants.ROUTE);
        openStreetMap.draw(routeDetailsDto.getCoordinates());
    }
}