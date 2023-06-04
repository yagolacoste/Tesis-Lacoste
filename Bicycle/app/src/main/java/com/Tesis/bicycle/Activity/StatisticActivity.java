package com.Tesis.bicycle.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;


public class StatisticActivity extends AppCompatActivity {

    private MapView myOpenMapView;

    private TextView tv_speed,tv_time,tv_distance;

    private RouteDetailsDto routeDetailsDto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        myOpenMapView=findViewById(R.id.v_map);
        tv_speed=findViewById(R.id.tv_speed);
        tv_distance=findViewById(R.id.tv_distance);
        tv_time=findViewById(R.id.tv_timeSpeed);
        List<GeoPoint>points=getCoordinates();
        drawRoute(points);
        viewStatistic();


    }

    private void viewStatistic() {


    }


    private void drawRoute(List<GeoPoint> routes) {
        Marker startMarker=new Marker(myOpenMapView);
        startMarker.setPosition(routes.get(0));

        myOpenMapView.getOverlays().add(startMarker);
        RoadManager roadManager=new OSRMRoadManager(StatisticActivity.this,"OBP_Tuto/1.0");
        ((OSRMRoadManager)roadManager).setMean(OSRMRoadManager.MEAN_BY_BIKE);
        Road road=roadManager.getRoad((ArrayList<GeoPoint>) routes);
        Polyline roadOverlay=RoadManager.buildRoadOverlay(road, 0x800000FF, 25.0f);


        // Polyline roadOverlay=RoadManager.buildRoadOverlay(road).setWidth(2.0f);
        myOpenMapView.getOverlays().add(roadOverlay);
        Marker endMarker=new Marker(myOpenMapView);
        endMarker.setPosition(routes.get(routes.size()-1));

        myOpenMapView.getOverlays().add(endMarker);

        myOpenMapView.invalidate();
    }

    //Transformation coordinates in GeoPoint
    public List<GeoPoint> getCoordinates(){
        routeDetailsDto= (RouteDetailsDto) getIntent().getSerializableExtra("Route");
        List<GeoPoint> result=new ArrayList<>();
        String resultCoordinates=routeDetailsDto.getCoordinates().toString();
        try {
            JSONArray coordinates=new JSONArray(resultCoordinates);
            for(int i=0;i<coordinates.length();i++){
//                JSONObject point=coordinates.getJSONObject(i);
                String aux=coordinates.getString(i);
                String[] split=aux.split(",");
                result.add(new GeoPoint(Double.valueOf(split[0]),Double.valueOf(split[1])));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}