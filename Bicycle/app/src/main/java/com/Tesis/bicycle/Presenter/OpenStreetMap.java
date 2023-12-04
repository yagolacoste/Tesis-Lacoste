package com.Tesis.bicycle.Presenter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.Tesis.bicycle.R;



import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.bonuspack.utils.BonusPackHelper;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.drawing.MapSnapshot;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OpenStreetMap {

    private MapView myOpenMapView;
    private CompassOverlay mCompassOverlay;
    private  Marker startMarker;
    private Polyline roadOverlay;
    private Context context;

    private int color =Color.argb(128, 0, 0, 255);

    RotationGestureOverlay mRotationGestureOverlay;


    public OpenStreetMap(MapView myOpenMapView) {
        this.myOpenMapView = myOpenMapView;
        mRotationGestureOverlay = new RotationGestureOverlay(myOpenMapView.getContext(), myOpenMapView);
        this.mCompassOverlay = new CompassOverlay(myOpenMapView.getContext(), new InternalCompassOrientationProvider(myOpenMapView.getContext()), myOpenMapView);
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
//        mRotationGestureOverlay = new RotationGestureOverlay(ctx, myOpenMapView);
        mRotationGestureOverlay.setEnabled(true);
        myOpenMapView.setMultiTouchControls(true);
        myOpenMapView.getOverlays().add(mRotationGestureOverlay);
//        this.mCompassOverlay = new CompassOverlay(ctx, new InternalCompassOrientationProvider(ctx), myOpenMapView);
        this.mCompassOverlay.enableCompass();
        myOpenMapView.getOverlays().add(this.mCompassOverlay);
        myOpenMapView.invalidate();
    }


    public  void updatePosition(Location location){
        if(location!=null){
            GeoPoint point=new GeoPoint(location.getLatitude(),location.getLongitude());
            startMarker.setPosition(point);
            startMarker.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
            myOpenMapView.getOverlays().add((myOpenMapView.getOverlays().size()-1),startMarker);
            IMapController mapController=myOpenMapView.getController();
            mapController.setZoom(19);
            mapController.setCenter(point);
            myOpenMapView.invalidate();
        }
    }

    public void removeMarker(){
        myOpenMapView.getOverlays().remove(startMarker);
        myOpenMapView.invalidate();
    }


public void draw(List<GeoPoint> points) {

    // Crear y agregar la línea al mapa
    Polyline polyline = new Polyline();
    polyline.setPoints(points);
    polyline.setColor(color);
    polyline.setWidth(15f);
    myOpenMapView.getOverlays().add(polyline);
    if(points.size()>1){
    // Agregar marcadores de inicio y fin
        Marker startMarker = new Marker(myOpenMapView);
        startMarker.setIcon(context.getResources().getDrawable(R.drawable.ic_location));
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setPosition(points.get(0));
        myOpenMapView.getOverlays().add(startMarker);

        Marker endMarker = new Marker(myOpenMapView);
        endMarker.setIcon(context.getResources().getDrawable(R.drawable.flag));
        endMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        endMarker.setPosition(points.get(points.size() - 1));
        myOpenMapView.getOverlays().add(endMarker);

        // Actualizar el mapa
        myOpenMapView.invalidate();
    }else{
        Marker startMarker = new Marker(myOpenMapView);
        startMarker.setIcon(context.getResources().getDrawable(R.drawable.ic_location));
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setPosition(points.get(0));
        myOpenMapView.getOverlays().add(startMarker);
        myOpenMapView.invalidate();
    }
}


    public void drawStatic(List<GeoPoint> points) {
        myOpenMapView.setBuiltInZoomControls(false);
        myOpenMapView.setClickable(false);
        myOpenMapView.setEnabled(false);
        myOpenMapView.getController().stopAnimation(true);
        this.mCompassOverlay.disableCompass();
        draw(points);
        autoZoom(points);
        myOpenMapView.invalidate();
    }



    private void autoZoom(List<GeoPoint> points){
        double minLat = Double.MAX_VALUE;
        double maxLat = -Double.MAX_VALUE;
        double minLon = Double.MAX_VALUE;
        double maxLon = -Double.MAX_VALUE;

        for (GeoPoint point : points) {
            double latitude = point.getLatitude();
            double longitude = point.getLongitude();

            if (latitude < minLat) {
                minLat = latitude;
            }
            if (latitude > maxLat) {
                maxLat = latitude;
            }
            if (longitude < minLon) {
                minLon = longitude;
            }
            if (longitude > maxLon) {
                maxLon = longitude;
            }
        }

        myOpenMapView.getController().zoomToSpan(Math.abs(maxLat - minLat), Math.abs(maxLon - minLon));
        myOpenMapView.getController().setCenter(new GeoPoint((maxLat + minLat) / 2, (maxLon + minLon) / 2));
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


}
