package com.Tesis.bicycle.Activity.ui.Fragment;

import android.content.Context;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.Tesis.bicycle.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewPagerAdapter extends FragmentStateAdapter {
    enum Tab {
        CHALLENGE(0,R.string.tab_challenge),
        FRIENDS(1,R.string.tab_friends),
        SCORE(2,R.string.tab_score);
        final int position;
        final int title;

        Tab(int position,@StringRes int title) {
            this.position = position;
            this.title=title;
        }

        private static final Map<Integer,Tab> map;
        static {
            map = new HashMap<>();
            for (Tab t : Tab.values()) {
                map.put(t.position, t);
            }
        }

        static Tab byPosition(int position) {
            return map.get(position);
        }
    }

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == Tab.CHALLENGE.position)
            return BattleFragment.newInstance(Tab.CHALLENGE.title);
        else if (position == Tab.FRIENDS.position)
            return BattleFragment.newInstance(Tab.FRIENDS.title);
//        else if (position == Tab.SCORE.position)
//            return TabNameFragment.newInstance(Tab.MUSIC.title);
//        else
            throw new IllegalArgumentException("unknown position " + position);
    }

    @Override
    public int getItemCount() {
        return Tab.values().length;
    }

}
