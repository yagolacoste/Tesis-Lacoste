package com.Tesis.bicycle.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.Tesis.bicycle.Dto.AppUserHasRouteDetailsDto;
import com.Tesis.bicycle.Dto.RouteDetailsDto;
import com.Tesis.bicycle.MainActivity;
import com.Tesis.bicycle.Presenter.ApiRestConecction;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.Service.ApiRest.AppUserHasRouteApiRestService;

import org.osmdroid.util.GeoPoint;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowPointLocationList extends Activity {

    ListView lv_saveLocations;
    List<RouteDetailsDto>routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_point_location_list);

        lv_saveLocations=findViewById(R.id.lv_showLocations);

        getRoutesByUser();



    }

    ///Lista las rutas por usuarios
    public void  getRoutesByUser() {
        AppUserHasRouteApiRestService appUserHasRouteApiRestService = ApiRestConecction.getServiceAppUserHasRoute();
        Call<AppUserHasRouteDetailsDto> call = appUserHasRouteApiRestService.getRouteById(1L);//es el usuario 1 por defecto
        call.enqueue(new Callback<AppUserHasRouteDetailsDto>() {
            @Override
            public void onResponse(Call<AppUserHasRouteDetailsDto> call, Response<AppUserHasRouteDetailsDto> response) {
                if(response.isSuccessful()){
                    routes=response.body().getRoutes();
                    lv_saveLocations.setAdapter(new ArrayAdapter<RouteDetailsDto>(ShowPointLocationList.this, android.R.layout.simple_list_item_1,routes));

                    lv_saveLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent=new Intent(ShowPointLocationList.this,OpenStreetMap.class);
                            intent.putExtra("Route",routes.get(i));
                            startActivity(intent);
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<AppUserHasRouteDetailsDto> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }
}