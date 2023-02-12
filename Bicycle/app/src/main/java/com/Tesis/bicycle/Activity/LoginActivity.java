package com.Tesis.bicycle.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Tesis.bicycle.Dto.ApiRest.auth.LoginRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.TokenDto;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LoginActivity extends AppCompatActivity {

    private EditText editMail,editPassword;
    private Button btnInitSession;
    private UserViewModel viewModel;
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
        viewModel=new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void init() {
        editMail = findViewById(R.id.edtMail);//cambiar a ingles
        editPassword = findViewById(R.id.edtPassword);
        txtInputUser = findViewById(R.id.txtInputUsuario);
        txtInputPassword = findViewById(R.id.txtInputPassword);
        txtNewUser = findViewById(R.id.txtNuevoUsuario);
        btnInitSession = findViewById(R.id.btnIniciarSesion);
        LoginRequest loginRequest=new LoginRequest();
        loginRequest.setUsername(editMail.getText().toString());
        loginRequest.setPassword(editPassword.getText().toString());
        btnInitSession.setOnClickListener(v->{
            viewModel.login(loginRequest).observe(this,response ->{
                if(response.getToken()==null){
                    Toast.makeText(this,"accedio",Toast.LENGTH_SHORT).show();
                    String tokenDto=response.getToken();
                    SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor edit=preferences.edit();
                    final Gson g=new GsonBuilder()
                            .registerTypeAdapter()
                }else{
                    Toast.makeText(this,"ocurrio un erro",Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}