package com.Tesis.bicycle.Activity;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.Tesis.bicycle.Dto.ApiRest.Request.RequestDto;
import com.Tesis.bicycle.Model.FriendshipRequestStatus;
import com.Tesis.bicycle.Presenter.Adapter.UserSearchAdapter;
import com.Tesis.bicycle.Presenter.ListView.UserListViewActivity;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.AccessTokenRoomViewModel;
import com.Tesis.bicycle.ViewModel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.stream.Collectors;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddFriendActivity extends FragmentActivity {

    private EditText editTextNameUser;

    private List<RequestDto> users;
    private List<RequestDto>usersFilter;
    private RecyclerView rvUsers;
    private UserSearchAdapter adapter;
    private TextInputLayout txtViewNameUser;
    private AccessTokenRoomViewModel accessTokenRoomViewModel;
    private UserViewModel userViewModel;

    private LinearLayout layoutVacio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        Init();
    }

    private void Init() {
        accessTokenRoomViewModel = new ViewModelProvider(this).get(AccessTokenRoomViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        editTextNameUser = findViewById(R.id.editTextNameUser);
        rvUsers=findViewById(R.id.rvUsers);
        txtViewNameUser = findViewById(R.id.txtViewNameUser);

        LinearLayoutManager lm = new LinearLayoutManager(this);
        rvUsers.setLayoutManager(lm);
        editTextNameUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(""+s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loadData();

    }


    private void search(String s) {
        users.clear();
        for(RequestDto user:usersFilter){
            if(user.getNameComplete().toLowerCase().contains(s.toLowerCase())){
                users.add(user);
            }
        }
        adapter.setUsers(users);
        rvUsers.setAdapter(adapter);

    }

    private void loadData() {
        accessTokenRoomViewModel.getFirst().observe(this,response->{
            if(response.getAccessToken()!=null){
                userViewModel.searchUsers(response.getId(), FriendshipRequestStatus.PENDING.getValue()).observe(this, resp->{
                    users=resp;
                    users=users.stream().filter(user->!user.getUserDest().equals(response.getId())).collect(Collectors.toList());
                    usersFilter=resp;
                    adapter=new UserSearchAdapter(this,users);
                    rvUsers.setAdapter(adapter);
                });
            }
        });
    }


    private void storedData() {
        if (validate()) {
            try {
                String email = editTextNameUser.getText().toString();
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
        email = editTextNameUser.getText().toString();
        if (email.isEmpty()) {
            txtViewNameUser.setError("Enter your Name");
            retorno = false;
        } else {
            txtViewNameUser.setErrorEnabled(false);
        }
        return retorno;
    }

    public void warningMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.WARNING_TYPE).setTitleText("Notificación del Sistema")
                .setContentText(message).setConfirmText("Ok").show();
    }


}