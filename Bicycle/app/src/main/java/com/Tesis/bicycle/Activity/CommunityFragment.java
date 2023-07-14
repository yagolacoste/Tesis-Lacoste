package com.Tesis.bicycle.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Tesis.bicycle.Activity.ui.Fragment.RecyclerViewFragment;
import com.Tesis.bicycle.Activity.ui.Fragment.ViewPagerAdapter;
import com.Tesis.bicycle.R;
import com.google.android.material.tabs.TabLayout;

public class CommunityFragment extends Fragment {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;



    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_community, container, false);

        viewPager2 = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

        FragmentActivity fragmentActivity = requireActivity();
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentActivity);
        adapter.addActivity(RecyclerViewFragment.newInstance("Statistics", requireContext()));

        // Configurar el adaptador en el ViewPager
        viewPager2.setAdapter(adapter);


        tabLayout.getTabAt(0).setText(adapter.getFragments().get(0).getClass().getSimpleName());
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        return view;
    }
}