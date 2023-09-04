package com.Tesis.bicycle.Activity.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.Tesis.bicycle.Activity.TrackingActivity;
import com.Tesis.bicycle.Activity.ui.Fragment.CommunityFragment;
import com.Tesis.bicycle.Activity.ui.Fragment.FriendshipRequestFragment;
import com.Tesis.bicycle.Activity.ui.Fragment.MyMapsFragment;
import com.Tesis.bicycle.Activity.ui.Fragment.ProfileFragment;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.databinding.ActivityNavInitBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NavInitActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private ActivityNavInitBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNavInitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setBackground(null);

        floatingActionButton=findViewById(R.id.floatingBottomInit);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_notification);
        replaceFragment(new FriendshipRequestFragment());
        bottomNavigationView.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.bottom_notification:
                    replaceFragment(new FriendshipRequestFragment());
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    return true;
                    //continue with UI
                case R.id.bottom_my_maps:
                   // startActivity(new Intent(NavInitActivity.this, RouteListViewActivity.class));
                    replaceFragment(new MyMapsFragment());
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    return true;
                case R.id.bottom_community:
                    //startActivity(new Intent(NavInitActivity.this, CommunityActivity.class));

                    replaceFragment(new CommunityFragment());
//                    i.setAction(Constants.ACTION_VIEW_FRIENDS);
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    return true;

                case R.id.bottom_profile:
                    //startActivity(new Intent(NavInitActivity.this, CommunityActivity.class));

                    replaceFragment(new ProfileFragment());
//                    i.setAction(Constants.ACTION_VIEW_FRIENDS);
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    return true;
            }
            return false;
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(NavInitActivity.this, TrackingActivity.class);
                startActivity(i);
            }
        });
    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}