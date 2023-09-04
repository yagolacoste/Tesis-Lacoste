package com.Tesis.bicycle.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Tesis.bicycle.Activity.ui.NavInitActivity;
import com.Tesis.bicycle.Dto.ApiRest.auth.request.LoginRequest;
import com.Tesis.bicycle.Dto.ApiRest.auth.response.JwtResponse;
import com.Tesis.bicycle.Model.Room.RefreshToken;
import com.Tesis.bicycle.Presenter.Notifications;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.AccessTokenRoomViewModel;
import com.Tesis.bicycle.ViewModel.AuthViewModel;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private EditText editMail,editPassword;
    private Button btnInitSession;
    private AuthViewModel authViewModel;
    private AccessTokenRoomViewModel accessTokenRoomViewModel;
    private TextView txtNewUser,txtForgetMyPassword;
    private TextInputLayout txtInputEmail, txtInputPassword;

    private Notifications notifications;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        notifications=new Notifications(this);
        this.initViewModel();

        accessTokenRoomViewModel.getAccessToken().observe(this,response->{
            if(response!=null){
                Toast.makeText(this,"Session active",Toast.LENGTH_LONG).show();
                //startActivity(new Intent(this,MenuActivity.class));
               startActivity(new Intent(this, NavInitActivity.class));
            }
            else
                this.init();
        });
    }

    private void initViewModel() {
        authViewModel =new ViewModelProvider(this).get(AuthViewModel.class);
        accessTokenRoomViewModel=new ViewModelProvider(this).get(AccessTokenRoomViewModel.class);
    }

    private void init() {
        editMail = findViewById(R.id.edtMail);
        editPassword = findViewById(R.id.edtPassword);
        txtInputEmail = findViewById(R.id.txtInputEmail);
        txtInputPassword = findViewById(R.id.txtInputPassword);
        txtNewUser = findViewById(R.id.txtRegister);
        btnInitSession = findViewById(R.id.btnStartSession);
        txtForgetMyPassword=findViewById(R.id.txtForgetMyPassword);
        btnInitSession.setOnClickListener(v->{
        try{
            if(validate()){
                LoginRequest loginRequest=new LoginRequest();
                loginRequest.setEmail(editMail.getText().toString());
                loginRequest.setPassword(editPassword.getText().toString());
                authViewModel.login(loginRequest).observe(this, response ->{
                    if(response.getAccessToken()!=null){
                        accessTokenRoomViewModel.logout();
                        Toast.makeText(this,"Access the application",Toast.LENGTH_LONG).show();
                        JwtResponse tokenDto= response;
                        RefreshToken refreshToken=new RefreshToken(tokenDto);
                        accessTokenRoomViewModel.addAccessToken(refreshToken);
                        editMail.setText("");
                        editPassword.setText("");
                        startActivity(new Intent(this,NavInitActivity.class));
                        this.onPause();
                    }else{
                        notifications.warningMessage("Error! to access the account");
                    }
                });
            }else
                Toast.makeText(this,"Please insert email and password",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
                Toast.makeText(this,"User not exits",Toast.LENGTH_SHORT).show();
            }
        });
        txtNewUser.setOnClickListener(view -> {
            Intent i=new Intent(this,RegisterUserActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.left_in,R.anim.left_out);
        });
        editMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtInputEmail.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtInputPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private boolean validate() {
        boolean result = true;
        String user, password;
        user = editMail.getText().toString();
        password = editPassword.getText().toString();
        if (user.isEmpty()) {
            txtInputEmail.setError("Insert your email or password");
            result = false;
        } else {
            txtInputEmail.setErrorEnabled(false);
        }
        if (password.isEmpty()) {
            txtInputPassword.setError("Enter your password");
            result = false;
        } else {
            txtInputPassword.setErrorEnabled(false);
        }
        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}