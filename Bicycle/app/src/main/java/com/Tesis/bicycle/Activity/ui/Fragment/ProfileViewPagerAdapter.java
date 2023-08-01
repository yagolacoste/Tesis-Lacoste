package com.Tesis.bicycle.Activity.ui.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.Tesis.bicycle.R;

import java.util.HashMap;
import java.util.Map;

public class ProfileViewPagerAdapter extends FragmentStateAdapter {

    enum ProfileTab {
        PROFILE(0, R.string.tab_profile),
        ACHIEVEMENTS(1,R.string.tab_achievements),
        CLASSIFICATION(2,R.string.tab_classification);
        final int position;
        final int title;

        ProfileTab(int position,@StringRes int title) {
            this.position = position;
            this.title=title;
        }

        private static final Map<Integer, ProfileTab> map;
        static {
            map = new HashMap<>();
            for (ProfileTab t : ProfileTab.values()) {
                map.put(t.position, t);
            }
        }

        static ProfileTab byPosition(int position) {
            return map.get(position);
        }
    }
    public ProfileViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == ProfileTab.PROFILE.position)
            return UserFragment.newInstance(ProfileTab.PROFILE.title);
        else if (position == ProfileTab.ACHIEVEMENTS.position)
            return AchievementsFragment.newInstance(ProfileTab.ACHIEVEMENTS.title);
        else if (position == ProfileViewPagerAdapter.ProfileTab.CLASSIFICATION.position)
            return ClassificationFragment.newInstance(ProfileViewPagerAdapter.ProfileTab.CLASSIFICATION.title);
        else
            throw new IllegalArgumentException("unknown position " + position);
    }

    @Override
    public int getItemCount() {
       return ViewPagerAdapter.Tab.values().length;
    }
}
