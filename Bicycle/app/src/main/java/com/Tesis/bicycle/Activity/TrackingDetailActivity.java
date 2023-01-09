package com.Tesis.bicycle.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Dto.Room.AppUserHasRouteDTO;
import com.Tesis.bicycle.Dto.Room.RouteDTO;
import com.Tesis.bicycle.Model.ApiRest.AppUserHasRouteApiRest;
import com.Tesis.bicycle.Model.Room.AppUserHasRoute;
import com.Tesis.bicycle.Model.Room.Route;
import com.Tesis.bicycle.Presenter.ApiRestConecction;
import com.Tesis.bicycle.Presenter.AppDataBase;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.Service.ApiRest.AppUserHasRouteApiRestService;
import com.Tesis.bicycle.Service.Room.AppUserHasRouteService;
import com.Tesis.bicycle.Service.Room.RouteService;

import org.json.JSONArray;
import org.json.JSONException;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackingDetailActivity extends AppCompatActivity {

    private TextView tv_distance_value,tv_speed_value,tv_time_value,tv_date_value,tv_session_value;
    private Button btn_save,btn_discard;
    private MapView myOpenMapView;
    private AppDataBase db;
    private CompassOverlay mCompassOverlay;
//    private AppUserHasRouteDTO appUserHasRoute;
//    private RouteDTO routeDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_detail);

        //Inicializo los textview
        tv_distance_value=findViewById(R.id.tv_distance_value);
        tv_speed_value=findViewById(R.id.tv_speed_value);
        tv_time_value=findViewById(R.id.tv_time_value);
        tv_date_value=findViewById(R.id.tv_date_value);
        tv_session_value=findViewById(R.id.tv_session_value);
        btn_save=findViewById(R.id.btn_save);
        btn_discard=findViewById(R.id.btn_discard);
        db= Room.databaseBuilder(getApplicationContext(),AppDataBase.class, Constants.NAME_DATA_BASE)
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        myOpenMapView=findViewById(R.id.v_map);



        this.initLayer(this);
        AppUserHasRouteDTO appUserHasRoute=db.appUserHasRouteService().getAll().get(db.appUserHasRouteService().countAppUserHasRoute()-1);
        RouteDTO routeDTO=db.routeService().getById(appUserHasRoute.getRoute());
        List<GeoPoint>transform=this.transformCoordinate(routeDTO.getCoordinates());
        this.drawRoute(transform);
        tv_distance_value.setText(String.valueOf(appUserHasRoute.getKilometres()));
        tv_speed_value.setText(String.valueOf(appUserHasRoute.getSpeed()));
        tv_time_value.setText(String.valueOf(appUserHasRoute.getTimeSpeed()));
        tv_date_value.setText(String.valueOf(appUserHasRoute.getTimesSession()));
        tv_session_value.setText(String.valueOf(appUserHasRoute.getId()));

        String action=getIntent().getAction();
        if(!(action!=null && action.equals(Constants.REPLAY_MY_ROUTE))){
            inputNameAndDescription(routeDTO);
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUserHasRouteApiRest appUserHasRouteApiRest=new AppUserHasRouteApiRest();
                appUserHasRouteApiRest.setAppUser(1L);
                appUserHasRouteApiRest.setKilometres(appUserHasRoute.getKilometres());
                appUserHasRouteApiRest.setSpeed(appUserHasRoute.getSpeed());
                appUserHasRouteApiRest.setTimeSpeed(appUserHasRoute.getTimeSpeed());
                appUserHasRouteApiRest.setTimeSession(appUserHasRoute.getTimesSession());
                appUserHasRouteApiRest.setWeather("");
                appUserHasRouteApiRest.setDescription(routeDTO.getDescription());
                appUserHasRouteApiRest.setName(routeDTO.getName());
                appUserHasRouteApiRest.setRoute(1L+"-"+routeDTO.getId());
                appUserHasRouteApiRest.setCoordinates(routeDTO.getCoordinates());
                sendData(appUserHasRouteApiRest);
                Intent i=new Intent(TrackingDetailActivity.this,Menu.class);
                startActivity(i);
            }
        });
        btn_discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //borrar y volver a la pagina anterior
            }
        });
    }

    private void inputNameAndDescription(RouteDTO route) {
        androidx.appcompat.app.AlertDialog.Builder myDialog=new androidx.appcompat.app.AlertDialog.Builder(TrackingDetailActivity.this);
        myDialog.setTitle("Input name and description for the new route");
        LinearLayout layout=new LinearLayout(this);
        EditText name=new EditText(TrackingDetailActivity.this);
        name.setInputType(InputType.TYPE_CLASS_TEXT);
        name.setHint("Name");
        layout.addView(name);
        EditText description=new EditText(TrackingDetailActivity.this);
        description.setInputType(InputType.TYPE_CLASS_TEXT);
        description.setHint("Description");
        layout.addView(description);

        myDialog.setView(layout);

        myDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                route.setName(name.getText().toString());
                route.setDescription(description.getText().toString());
            }
        });
        myDialog.show();
    }

    private void drawRoute(List<GeoPoint> routes) {
        Marker startMarker=new Marker(myOpenMapView);
        startMarker.setPosition(routes.get(0));

        myOpenMapView.getOverlays().add(startMarker);
        RoadManager roadManager=new OSRMRoadManager(TrackingDetailActivity.this,"OBP_Tuto/1.0");
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


    //Init osmodroid map with position
    private void initLayer(Context ctx) {
        //Seteo de mapa en tandil statico
        GeoPoint tandil=new GeoPoint(-37.32359319563445,-59.12548631254784);
        myOpenMapView.setTileSource(TileSourceFactory.MAPNIK);
        myOpenMapView.setBuiltInZoomControls(true);
        myOpenMapView.setMultiTouchControls(true);
        IMapController mapController=myOpenMapView.getController();
        mapController.setZoom(12);
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


    private List<GeoPoint> transformCoordinate(String coordinate){
        List<GeoPoint> result=new ArrayList<>();
        try {
            JSONArray coordinates=new JSONArray(coordinate);
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


    //add statistics in server
    public void sendData(AppUserHasRouteApiRest appUserHasRouteApiRest){
        AppUserHasRouteApiRestService appUserHasRouteApiRestService= ApiRestConecction.getServiceAppUserHasRoute();
        Call<Void> call=appUserHasRouteApiRestService.AddStatistics(appUserHasRouteApiRest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(TrackingDetailActivity.this,"add is success",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }
}