package com.Tesis.bicycle.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Tesis.bicycle.Dto.ApiRest.auth.request.LoginRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.JwtResponse;
import com.Tesis.bicycle.Model.Room.RefreshToken;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.AccessTokenRoomViewModel;
import com.Tesis.bicycle.ViewModel.UserViewRoomModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LoginActivity extends AppCompatActivity {

    private EditText editMail,editPassword;
    private Button btnInitSession;
    private UserViewRoomModel userViewModel;
    private AccessTokenRoomViewModel accessTokenRoomViewModel;
    private TextView txtNewUser;
    private TextInputLayout txtInputUser, txtInputPassword;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.initViewModel();
        this.init();
    }

    private void initViewModel() {
        userViewModel=new ViewModelProvider(this).get(UserViewRoomModel.class);
        accessTokenRoomViewModel=new ViewModelProvider(this).get(AccessTokenRoomViewModel.class);
    }

    private void init() {
        editMail = findViewById(R.id.edtMail);//cambiar a ingles
        editPassword = findViewById(R.id.edtPassword);
        txtInputUser = findViewById(R.id.txtInputUsuario);
        txtInputPassword = findViewById(R.id.txtInputPassword);
        txtNewUser = findViewById(R.id.txtNuevoUsuario);
        btnInitSession = findViewById(R.id.btnIniciarSesion);


        btnInitSession.setOnClickListener(v->{
            LoginRequest loginRequest=new LoginRequest();
            loginRequest.setUsername(editMail.getText().toString());
            loginRequest.setPassword(editPassword.getText().toString());

            userViewModel.login(loginRequest).observe(this,response ->{
                if(response!=null){
                    Toast.makeText(this,"accedio",Toast.LENGTH_SHORT).show();
                    JwtResponse tokenDto= response;
                    RefreshToken refreshToken=new RefreshToken(tokenDto);
                    accessTokenRoomViewModel.addAccessToken(refreshToken);

//                    SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
//                    SharedPreferences.Editor edit=preferences.edit();
                    final Gson g=new GsonBuilder().create();
//                    edit.putString("User",g.toJson(tokenDto,new TypeToken<JwtResponse>(){
//                    }.getType()));
                    editMail.setText("");
                    editPassword.setText("");
                    startActivity(new Intent(this,MenuActivity.class));
                }else{
                    Toast.makeText(this,"ocurrio un erro",Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        accessTokenRoomViewModel.getAccessToken().observe(this,response->{
            if(response!=null){
                Toast.makeText(this,"session activa",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MenuActivity.class));
            }
        });

//        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this);
//        String pref= preferences.getString("User","");
//        if(!pref.equals("")){
//            Toast.makeText(this,"session activa",Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(this,MenuActivity.class));

//        }
    }
}