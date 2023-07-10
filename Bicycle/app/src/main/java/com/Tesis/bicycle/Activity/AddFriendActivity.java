package com.Tesis.bicycle.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.Tesis.bicycle.Presenter.ListView.UserListViewActivity;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ServiceTracking.GPSService;
import com.Tesis.bicycle.ViewModel.AccessTokenRoomViewModel;
import com.Tesis.bicycle.ViewModel.AuthViewModel;
import com.Tesis.bicycle.ViewModel.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddFriendActivity extends AppCompatActivity {

    private EditText edtEmail;
    private Button btnAddUser;

    private TextInputLayout txtEmailFriend;

    private AccessTokenRoomViewModel accessTokenRoomViewModel;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        Init();
    }

    private void Init() {
        accessTokenRoomViewModel = new ViewModelProvider(this).get(AccessTokenRoomViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        edtEmail = findViewById(R.id.edtEmailFriend);
        btnAddUser = findViewById(R.id.btnSaveFriend);
        txtEmailFriend = findViewById(R.id.txtEmailFriend);
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtEmailFriend.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnAddUser.setOnClickListener(v -> {
            storedData();
        });
    }

    private void storedData() {
        if (validate()) {
            try {
                String email = edtEmail.getText().toString();
                accessTokenRoomViewModel.getFirst().observe(AddFriendActivity.this, resp -> {
                    if (resp != null) {
                        Long id = resp.getId();
                        userViewModel.saveFriend(email, id).observe(this, response -> {
                            if (response) {
                                successMessage("Estupendo! " + "Su información ha sido guardada con éxito en el sistema.");
                                backToMenuActivity();
                            } else {
                                warningMessage("No existe el usuer");
                            }
                        });
                    }
                });
            } catch (Exception e) {
                warningMessage("Se ha producido un error : " + e.getMessage());
            }

        }
    }

    private void backToMenuActivity() {
        Intent i=new Intent(AddFriendActivity.this, UserListViewActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void successMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Good Job!")
                .setContentText(message).show();
    }

    public void errorMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText(message).show();
    }

    private boolean validate() {
        boolean retorno = true;
        String email;
        email = edtEmail.getText().toString();
        if (email.isEmpty()) {
            txtEmailFriend.setError("enter your email");
            retorno = false;
        } else {
            txtEmailFriend.setErrorEnabled(false);
        }
        return retorno;
    }

    public void warningMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.WARNING_TYPE).setTitleText("Notificación del Sistema")
                .setContentText(message).setConfirmText("Ok").show();
    }
}