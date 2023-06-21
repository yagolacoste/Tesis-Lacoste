package com.Tesis.bicycle.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Presenter.ListView.UserListViewActivity;
import com.Tesis.bicycle.Presenter.OpenStreetMap;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.AccessTokenRoomViewModel;
import com.Tesis.bicycle.ViewModel.UserViewModel;

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

    private Button share;

    private TextView title,description,distance,timeProm;
    private RouteDetailsDto route;
    private OpenStreetMap openStreetMap;

    private AccessTokenRoomViewModel accessTokenRoomViewModel;

    private UserViewModel userViewModel;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_route_details);
        accessTokenRoomViewModel = new ViewModelProvider(this).get(AccessTokenRoomViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
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
        distance.setText(route.getDistance());
        timeProm.setText(String.valueOf(route.getTime()));
        share=findViewById(R.id.btnShare);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RouteDetailsActivity.this, UserListViewActivity.class);
                startActivityForResult(intent,Constants.REQUEST_CODE);
            }
        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == Constants.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            String action=data.getAction();
//            if(action.equals(Constants.USER_SELECT)) {
//                accessTokenRoomViewModel.getFirst().observe(RouteDetailsActivity.this, resp -> {
//                    if (resp != null) {
//                        Long id = resp.getId();
////                      //  userViewModel.saveFriend(email, id).observe(this, response -> {
////            }
//        }
//    }


}