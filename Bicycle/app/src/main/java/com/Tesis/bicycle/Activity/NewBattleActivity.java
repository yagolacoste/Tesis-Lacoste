package com.Tesis.bicycle.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Tesis.bicycle.Activity.ui.Fragment.BattleListFragment;
import com.Tesis.bicycle.Activity.ui.Fragment.CommunityFragment;
import com.Tesis.bicycle.Activity.ui.Fragment.ViewPagerAdapter;
import com.Tesis.bicycle.Constants;
import com.Tesis.bicycle.Dto.ApiRest.Battle.NewBattleDto;
import com.Tesis.bicycle.Dto.ApiRest.RouteDetailsDto;
import com.Tesis.bicycle.Dto.ApiRest.UserAppDto;
import com.Tesis.bicycle.Presenter.ListView.RouteListViewActivity;
import com.Tesis.bicycle.Presenter.ListView.UserListViewActivity;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.AccessTokenRoomViewModel;
import com.Tesis.bicycle.ViewModel.BattleViewModel;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class NewBattleActivity extends AppCompatActivity {

    private EditText edtDate;
    private LinearLayout containsUser,containsRoute;
    private Button btnAddUser,btnSelectRoute,btnCompete;
    private RouteDetailsDto routeDetailsDto;
    private int ultimoAnio, ultimoMes, ultimoDiaDelMes;

    private List<UserAppDto> users;
    private AccessTokenRoomViewModel accessTokenRoomViewModel;
    private BattleViewModel battleViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_battle);
        initViewModel();
        init();
    }

    private void initViewModel() {
        accessTokenRoomViewModel=new ViewModelProvider(this).get(AccessTokenRoomViewModel.class);
        battleViewModel=new ViewModelProvider(this).get(BattleViewModel.class);
    }

    private void init() {
        btnSelectRoute=findViewById(R.id.btnSelectRoute);
        containsRoute=findViewById(R.id.containsRoute);
        edtDate=findViewById(R.id.edtDate);
        btnAddUser=findViewById(R.id.btnAddUser);
        containsUser=findViewById(R.id.containsUser);
        btnCompete=findViewById(R.id.btnCompete);
        users=new ArrayList<>();
        btnSelectRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(), RouteListViewActivity.class);
                startActivityForResult(i,Constants.REQUEST_CODE);
            }
        });
        final Calendar calendario = Calendar.getInstance();
        ultimoAnio = calendario.get(Calendar.YEAR);
        ultimoMes = calendario.get(Calendar.MONTH);
        ultimoDiaDelMes = calendario.get(Calendar.DAY_OF_MONTH);
        refreshDateEnEditText();

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialogoFecha = new DatePickerDialog(v.getContext(), listenerDeDatePicker, ultimoAnio, ultimoMes, ultimoDiaDelMes);
                dialogoFecha.show();
            }
        });
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(), UserListViewActivity.class);
                startActivityForResult(i,Constants.REQUEST_CODE);
            }
        });

        btnCompete.setOnClickListener(view -> {
            if(routeDetailsDto!=null && routeDetailsDto.getId()!=null){
                if(edtDate.getText()!=null){
                    if(!users.isEmpty()){
                        try {
                            sendInformation();
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),"Falta agregar participantes",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Falta agregar una fecha",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getApplicationContext(),"Falta agregar una ruta",Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void sendInformation() throws ParseException {
        NewBattleDto battleDto=new NewBattleDto();
        battleDto.setRouteId(routeDetailsDto.getId());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date=edtDate.getText().toString();
            Date date1=dateFormat.parse(date);
            battleDto.setCompleteDate(date1);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            battleDto.setUsers(users.stream().map(UserAppDto::getId).collect(Collectors.toList()));
        }
        accessTokenRoomViewModel.getFirst().observe(this,resp->{
            battleDto.getUsers().add(resp.getId());
            battleDto.setCantParticipant( battleDto.getUsers().size());
            if(resp.getAccessToken()!=null){
                try {
                    battleViewModel.addBattle(battleDto).observe(this,response->{
                    });
                    BackToList();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void BackToList() {
        this.finish();
    }


    private DatePickerDialog.OnDateSetListener listenerDeDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int anio, int mes, int diaDelMes) {
            ultimoAnio = anio;
            ultimoMes = mes;
            ultimoDiaDelMes = diaDelMes;
            refreshDateEnEditText();
        }
    };
    public void refreshDateEnEditText() {
        String fecha = String.format(Locale.getDefault(), "%02d-%02d-%02d", ultimoAnio, ultimoMes+1, ultimoDiaDelMes);
        edtDate.setText(fecha);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String action=data.getAction();
            if(action.equals(Constants.ROUTE_SELECT)) {
                routeDetailsDto = (RouteDetailsDto) data.getSerializableExtra(Constants.ROUTE_ITEM);
                View customView = getLayoutInflater().inflate(R.layout.card_route_item_select, null);
                TextView titleTextView = customView.findViewById(R.id.txtValueNameRouteBattle);
                TextView descriptionTextView = customView.findViewById(R.id.txtValueDistanceRouteBattle);
                titleTextView.setText(routeDetailsDto.getName());
                descriptionTextView.setText(routeDetailsDto.getDistance());
                containsRoute.addView(customView);
                btnSelectRoute = findViewById(R.id.btnSelectRoute);
                containsRoute.removeView(btnSelectRoute);
            }else{
               UserAppDto userAppDto= (UserAppDto) data.getSerializableExtra(Constants.USER_ITEM);
               if(!users.contains(userAppDto)){
                View customView=getLayoutInflater().inflate(R.layout.card_user_item_select, null);
                TextView nameUser=customView.findViewById(R.id.txtValueNameUserItem);
                TextView ageUser=customView.findViewById(R.id.txtValueAgeUserItem);
                nameUser.setText(userAppDto.getFirstName()+" "+userAppDto.getLastName());
                ageUser.setText(String.valueOf(userAppDto.getAge()));
                users.add(userAppDto);
                containsUser.addView(customView);
               }else
                   Toast.makeText(getApplicationContext(),"Ya se agrego ese usuario",Toast.LENGTH_SHORT).show();

                //containsUser.addView(btnAddUser);
            }
        }
    }
}