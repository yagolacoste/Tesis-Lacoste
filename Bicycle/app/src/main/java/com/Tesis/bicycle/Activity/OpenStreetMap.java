package com.Tesis.bicycle.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.Tesis.bicycle.Dto.RouteDetailsDto;
import com.Tesis.bicycle.MainActivity;
import com.Tesis.bicycle.R;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.bonuspack.routing.RoadNode;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.FolderOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class OpenStreetMap extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView myOpenMapView;
    private MapController myMapController;
    List<GeoPoint> points=new ArrayList<GeoPoint>();
    private MyLocationNewOverlay mLocationOverlay;
    private CompassOverlay mCompassOverlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_open_street_map);

        //Obtengo los geopoints almacenados en la base del servidor
        points=getCoordinates();


        GeoPoint tandil=new GeoPoint(-37.32359319563445,-59.12548631254784);

        myOpenMapView= (MapView) findViewById(R.id.v_map);
        myOpenMapView.setTileSource(TileSourceFactory.MAPNIK);

        myOpenMapView.setBuiltInZoomControls(true);
        myOpenMapView.setMultiTouchControls(true);
        IMapController mapController=myOpenMapView.getController();
        mapController.setZoom(19);
        mapController.setCenter(tandil);

        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(ctx, myOpenMapView);
        mRotationGestureOverlay.setEnabled(true);
        myOpenMapView.setMultiTouchControls(true);
        myOpenMapView.getOverlays().add(mRotationGestureOverlay);


        if (checkPermissions(true)) {
            initLayer();

        }

//        while(i< points.size()-1){
            Marker startMarker = new Marker(myOpenMapView);
            startMarker.setPosition(points.get(0));
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            startMarker.setTitle("Start point");
            startMarker.setDraggable(true);
           // startMarker.setOnMarkerDragListener(new OnMarkerDragListenerDrawer());
            myOpenMapView.getOverlays().add(startMarker);

//        }
        RoadManager roadManager = new OSRMRoadManager(this, "OBP_Tuto/1.0");
        ((OSRMRoadManager)roadManager).setMean(OSRMRoadManager.MEAN_BY_BIKE);
        Road road=roadManager.getRoad((ArrayList<GeoPoint>) points);
        Polyline roadOverlay=RoadManager.buildRoadOverlay(road);
        myOpenMapView.getOverlays().add(roadOverlay);

        FolderOverlay roadMarkers = new FolderOverlay();
        myOpenMapView.getOverlays().add(roadMarkers);
        Drawable nodeIcon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_bicycle, null);
        for(int i=0;i<road.mNodes.size();i++){
            RoadNode node = road.mNodes.get(i);
            Marker nodeMarker = new Marker(myOpenMapView);
            nodeMarker.setPosition(node.mLocation);
            nodeMarker.setIcon(nodeIcon);
            nodeMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);

            nodeMarker.setTitle("Step " + i);
            nodeMarker.setSnippet(node.mInstructions);
            nodeMarker.setSubDescription(Road.getLengthDurationText(this, node.mLength, node.mDuration));
            Drawable iconContinue = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_bicycle, null);
            nodeMarker.setImage(iconContinue);

            roadMarkers.add(nodeMarker);
            myOpenMapView.invalidate();
        }




        myOpenMapView.invalidate();


    }

    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        myOpenMapView.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        myOpenMapView.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    public List<GeoPoint> getCoordinates(){
        RouteDetailsDto route= (RouteDetailsDto) getIntent().getSerializableExtra("Route");
        List<GeoPoint> result=new ArrayList<>();
        String resultCoordinates=route.getCoordinates();
        try {
            JSONObject coordinates=new JSONObject(resultCoordinates);

            for(int i =0; i<coordinates.length();i++){
                String aux=coordinates.getString(String.valueOf(i));
                String[] split=aux.split(",");
                result.add(new GeoPoint(Double.valueOf(split[0]),Double.valueOf(split[1])));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    return result;
    }


    private boolean checkPermissions(boolean request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                || (ContextCompat.checkSelfPermission(this,Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED)
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                if (request)
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACTIVITY_RECOGNITION}, OpenStreetMap.REQUEST_PERMISSIONS_REQUEST_CODE);
                return false;
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (request)
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, OpenStreetMap.REQUEST_PERMISSIONS_REQUEST_CODE);
                return false;
            }
        }


        return true;
    }


    @Override
    public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {
        super.onRequestPermissionsResult(code, permissions, results);

        if (code == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (this.checkPermissions(false)) {
                //startLocationUpdates();
                initLayer();
                //
            }
        }
    }

    private void initLayer() {
//        this.mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), myOpenMapView);
//        this.myOpenMapView.getOverlays().add(this.mLocationOverlay);
//        this.mLocationOverlay.setEnabled(true);
//        this.mLocationOverlay.enableMyLocation();
//        this.mLocationOverlay.enableFollowLocation();

        this.mCompassOverlay = new CompassOverlay(this, new InternalCompassOrientationProvider(this), myOpenMapView);
        this.mCompassOverlay.enableCompass();
        myOpenMapView.getOverlays().add(this.mCompassOverlay);
    }
}