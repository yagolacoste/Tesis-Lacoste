package com.Tesis.bicycle.Activity.ui.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Tesis.bicycle.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProfileFragment extends Fragment {

    private ViewPager2 viewPagerProfile;
    private TabLayout tabLayout;




    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        viewPagerProfile = view.findViewById(R.id.viewPagerProfile);
        tabLayout = view.findViewById(R.id.tabLayout);
        FragmentActivity fragmentActivity = requireActivity();
        ProfileViewPagerAdapter adapter = new ProfileViewPagerAdapter(fragmentActivity.getSupportFragmentManager(), getLifecycle());

        // Configurar el adaptador en el ViewPager
        viewPagerProfile.setAdapter(adapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPagerProfile,
                (ProfileTab, position) -> {
                    ProfileTab.setText(ProfileViewPagerAdapter.ProfileTab.byPosition(position).title);
                });

        tabLayoutMediator.attach();
        viewPagerProfile.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });


        return view;
    }




}