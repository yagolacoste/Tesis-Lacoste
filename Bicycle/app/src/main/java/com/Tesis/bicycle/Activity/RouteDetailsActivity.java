package com.Tesis.bicycle.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Path;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Presenter.OpenStreetMap;
import com.Tesis.bicycle.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RouteDetailsActivity extends AppCompatActivity {

    private  MapView myOpenMapView;

    private TextView title,description,distance,timeProm;
    private RouteDetailsDto route;
    private OpenStreetMap openStreetMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_route_details);

        title=findViewById(R.id.tv_route_details_title);
        description=findViewById(R.id.tv_route_details_description);
        distance=findViewById(R.id.tv_distance_prom);
        timeProm=findViewById(R.id.tv_time_prom);
        myOpenMapView=findViewById(R.id.v_map);
        route= (RouteDetailsDto) getIntent().getSerializableExtra(Constants.ROUTE);
        openStreetMap=new OpenStreetMap(myOpenMapView);
        openStreetMap.initLayer(this,route.getCoordinates().get(0));
        openStreetMap.draw(route.getCoordinates());
        title.setText(route.getName());
        description.setText(route.getDescription());
        distance.setText(String.valueOf(route.getAvgDistance()));
        timeProm.setText(String.valueOf(route.getAvgTime()));

    }


}