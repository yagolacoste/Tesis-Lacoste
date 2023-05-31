package com.Tesis.bicycle.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.Tesis.bicycle.Dto.ApiRest.auth.request.TokenRefreshRequest;
import com.Tesis.bicycle.Presenter.ApiRestConecction;
import com.Tesis.bicycle.Presenter.Client.ClientRetrofit;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.ViewModel.AccessTokenRoomViewModel;
import com.Tesis.bicycle.ViewModel.AuthViewModel;
import com.Tesis.bicycle.ViewModel.StoredDocumentViewModel;
import com.Tesis.bicycle.ViewModel.UserViewModel;
import com.Tesis.bicycle.databinding.ActivityInicioBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;


public class InicioActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityInicioBinding binding;
    private AuthViewModel authViewModel;
    private AccessTokenRoomViewModel accessTokenRoomViewModel;
    private StoredDocumentViewModel storedDocumentViewModel;
    private UserViewModel userViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInicioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarInicio.toolbar);
        binding.appBarInicio.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.layout.fragment_home,R.id.nav_newroute, R.id.nav_myroutes, R.id.nav_statistics)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        initViewModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.closeSession:
                this.logOut();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initViewModel();
        loadData();
    }

    private void loadData() {
        accessTokenRoomViewModel.getFirst().observe(this,response->{
            if(response.getAccessToken()!=null){
                userViewModel.getById(response.getId()).observe(this,resp->{
                    if(resp!=null){
                        final View vistaHeader = binding.navView.getHeaderView(0);
                        final TextView tvName=vistaHeader.findViewById(R.id.tvName);
                        final TextView tvEmail=vistaHeader.findViewById(R.id.tvEmail);
                        final CircleImageView photo=vistaHeader.findViewById(R.id.imgProfilePhoto);
                        tvName.setText(resp.getFirstName()+" "+resp.getLastName());
                        tvEmail.setText(resp.getEmail());
                        String url =ApiRestConecction.URL_STORED_DOCUMENT + "download?fileName=" + resp.getFileName();
                        final Picasso picasso = new Picasso.Builder(this)
                                .downloader(new OkHttp3Downloader(ClientRetrofit.getHttp()))
                                .build();
                        picasso.load(url)
                                .error(R.drawable.image_not_found)
                                .into(photo);
                    }
                });
            }
        });

    }

    private void logOut() {
        accessTokenRoomViewModel.getAccessToken().observe(this,response->{
            if(response!=null){
                String token=response.getRefreshToken();
                TokenRefreshRequest refreshToken=new TokenRefreshRequest(token);
                authViewModel.logoutUser(refreshToken);
            }
        });
        accessTokenRoomViewModel.logout();
        this.finish();
        this.overridePendingTransition(R.anim.left_in,R.anim.left_out);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void initViewModel() {
        authViewModel =new ViewModelProvider(this).get(AuthViewModel.class);
        accessTokenRoomViewModel=new ViewModelProvider(this).get(AccessTokenRoomViewModel.class);
        storedDocumentViewModel=new ViewModelProvider(this).get(StoredDocumentViewModel.class);
        userViewModel=new ViewModelProvider(this).get(UserViewModel.class);
    }
}