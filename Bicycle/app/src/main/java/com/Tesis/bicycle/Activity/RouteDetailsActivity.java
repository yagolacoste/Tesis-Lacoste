package com.Tesis.bicycle.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
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

import java.util.ArrayList;
import java.util.List;

public class RouteDetailsActivity extends AppCompatActivity {

    private  MapView myOpenMapView;
    private CompassOverlay mCompassOverlay;
    private TextView title,description,distance,timeProm;
    private RouteDetailsDto route;

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
        distance=findViewById(R.id.tv_route_details_distance);
        timeProm=findViewById(R.id.tv_route_details_time_prom);
        myOpenMapView=findViewById(R.id.v_map);
        initLayer(this);

        route= (RouteDetailsDto) getIntent().getSerializableExtra(Constants.ROUTE);
        title.setText(route.getName());
        description.setText(route.getDescription());
        List<GeoPoint>points=getCoordinates();
        drawRoute(points);

    }


    private void initLayer(Context ctx) {
        //Seteo de mapa en tandil statico
        GeoPoint tandil=new GeoPoint(-37.32359319563445,-59.12548631254784);
        myOpenMapView.setTileSource(TileSourceFactory.MAPNIK);
        myOpenMapView.setBuiltInZoomControls(true);
        myOpenMapView.setMultiTouchControls(true);
        IMapController mapController=myOpenMapView.getController();
        mapController.setZoom(18);
        mapController.setCenter(tandil);
        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(ctx, myOpenMapView);
        mRotationGestureOverlay.setEnabled(true);
        myOpenMapView.setMultiTouchControls(true);
        myOpenMapView.getOverlays().add(mRotationGestureOverlay);
        this.mCompassOverlay = new CompassOverlay(this, new InternalCompassOrientationProvider(this), myOpenMapView);
        this.mCompassOverlay.enableCompass();
        myOpenMapView.getOverlays().add(this.mCompassOverlay);
        myOpenMapView.invalidate();

    }

    private void drawRoute(List<GeoPoint> routes) {
        Marker startMarker=new Marker(myOpenMapView);
        startMarker.setPosition(routes.get(0));

        myOpenMapView.getOverlays().add(startMarker);
        RoadManager roadManager=new OSRMRoadManager(this,"OBP_Tuto/1.0");
        ((OSRMRoadManager)roadManager).setMean(OSRMRoadManager.MEAN_BY_BIKE);
        Road road=roadManager.getRoad((ArrayList<GeoPoint>) routes);
//        Polyline roadOverlay=new Polyline();
//        roadOverlay.setWidth(20f);
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
        //routeDetailsDto= (RouteDetailsDto) getIntent().getSerializableExtra("Route");
        List<GeoPoint> result=new ArrayList<>();
        String resultCoordinates=route.getCoordinates();
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