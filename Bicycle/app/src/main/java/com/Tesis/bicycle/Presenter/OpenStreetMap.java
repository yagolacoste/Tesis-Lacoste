package com.Tesis.bicycle.Presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.Tesis.bicycle.Activity.TrackingActivity;
import com.Tesis.bicycle.R;

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

public class OpenStreetMap {

    private MapView myOpenMapView;
    private CompassOverlay mCompassOverlay;
    private  Marker startMarker;

    private Context context;

    public OpenStreetMap(MapView myOpenMapView) {
        this.myOpenMapView = myOpenMapView;
    }

    public void initLayer(Context ctx, Location location) {
        context=ctx;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
//        myOpenMapView=(MapView) ctx.findViewById(R.id.v_map);
        startMarker=new Marker(myOpenMapView);
        startMarker.setIcon(ctx.getResources().getDrawable(R.drawable.ic_bicycle));
        //Seteo de mapa en tandil statico
        GeoPoint tandil=new GeoPoint(location.getLatitude(),location.getLongitude());
        startMarker.setPosition(tandil);
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
        myOpenMapView.getOverlays().add(startMarker);
        this.mCompassOverlay = new CompassOverlay(ctx, new InternalCompassOrientationProvider(ctx), myOpenMapView);
        this.mCompassOverlay.enableCompass();
        myOpenMapView.getOverlays().add(this.mCompassOverlay);
        myOpenMapView.invalidate();
    }

    public  void updatePosition(Location location){
        GeoPoint point=new GeoPoint(location.getLatitude(),location.getLongitude());
        startMarker.setPosition(point);
        startMarker.setAnchor(Marker.ANCHOR_RIGHT,Marker.ANCHOR_BOTTOM);
        myOpenMapView.getOverlays().add((myOpenMapView.getOverlays().size()-1),startMarker);
        IMapController mapController=myOpenMapView.getController();
        mapController.setCenter(point);
        myOpenMapView.invalidate();
    }

    public void removeMarker(){
        myOpenMapView.getOverlays().remove(startMarker);
        myOpenMapView.invalidate();
    }


    public void draw(List<GeoPoint> points){
        Marker startMarker=new Marker(myOpenMapView);
        startMarker.setPosition(points.get(0));
        myOpenMapView.getOverlays().add(startMarker);
        RoadManager roadManager=new OSRMRoadManager(myOpenMapView.getContext(), "OBP_Tuto/1.0");
        ((OSRMRoadManager)roadManager).setMean(OSRMRoadManager.MEAN_BY_BIKE);
        Road road=roadManager.getRoad((ArrayList<GeoPoint>) points);
        Polyline roadOverlay=RoadManager.buildRoadOverlay(road, 0x800000FF, 25.0f);
        myOpenMapView.getOverlays().add(roadOverlay);
        Marker endMarker=new Marker(myOpenMapView);
        endMarker.setPosition(points.get(points.size()-1));
        myOpenMapView.getOverlays().add(endMarker);
        myOpenMapView.invalidate();
    }


}
