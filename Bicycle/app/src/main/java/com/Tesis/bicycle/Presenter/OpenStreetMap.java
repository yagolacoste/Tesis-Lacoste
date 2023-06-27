package com.Tesis.bicycle.Presenter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.StrictMode;
import android.preference.PreferenceManager;

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
    private Polyline roadOverlay;
    private Context context;

    private RoadManager roadManager;

    private int color =Color.argb(128, 0, 0, 255);



    public OpenStreetMap(MapView myOpenMapView) {
        this.myOpenMapView = myOpenMapView;
        roadManager=new OSRMRoadManager(myOpenMapView.getContext(), "OBP_Tuto/1.0");
        ((OSRMRoadManager)roadManager).setMean(OSRMRoadManager.MEAN_BY_BIKE);
    }

    public void initLayer(Context ctx, GeoPoint location) {
        context=ctx;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        startMarker=new Marker(myOpenMapView);
        startMarker.setIcon(ctx.getResources().getDrawable(R.drawable.ic_bicycle));
        startMarker.setPosition(location);
        myOpenMapView.setTileSource(TileSourceFactory.MAPNIK);
        myOpenMapView.setBuiltInZoomControls(true);
        myOpenMapView.setMultiTouchControls(true);
        IMapController mapController=myOpenMapView.getController();
        mapController.setZoom(16);
        mapController.setCenter(location);
        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(ctx, myOpenMapView);
        mRotationGestureOverlay.setEnabled(true);
        myOpenMapView.setMultiTouchControls(true);
        myOpenMapView.getOverlays().add(mRotationGestureOverlay);
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
        mapController.setZoom(19);
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
        Road road=roadManager.getRoad((ArrayList<GeoPoint>) points);
        roadOverlay=RoadManager.buildRoadOverlay(road, color, 25f);
        myOpenMapView.getOverlays().add(roadOverlay);
        Marker endMarker=new Marker(myOpenMapView);
        endMarker.setPosition(points.get(points.size()-1));
        myOpenMapView.getOverlays().add(endMarker);
        myOpenMapView.invalidate();
    }

    public Polyline getRoadOverlay() {
        return roadOverlay;
    }

    public void setRoadOverlay(Polyline roadOverlay) {
        this.roadOverlay = roadOverlay;
    }

    public MapView getMyOpenMapView() {
        return myOpenMapView;
    }

    public void setMyOpenMapView(MapView myOpenMapView) {
        this.myOpenMapView = myOpenMapView;
    }

    public CompassOverlay getmCompassOverlay() {
        return mCompassOverlay;
    }

    public void setmCompassOverlay(CompassOverlay mCompassOverlay) {
        this.mCompassOverlay = mCompassOverlay;
    }

    public Marker getStartMarker() {
        return startMarker;
    }

    public void setStartMarker(Marker startMarker) {
        this.startMarker = startMarker;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public RoadManager getRoadManager() {
        return roadManager;
    }

    public void setRoadManager(RoadManager roadManager) {
        this.roadManager = roadManager;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
