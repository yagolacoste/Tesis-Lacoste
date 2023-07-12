package com.Tesis.bicycle.Activity.ui;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Class<?>> activityClasses = new ArrayList<>();
    private final List<String> activityTitles = new ArrayList<>();
    private final Context context;

    public ViewPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
    }

    public void addActivity(Class<?> activityClass, String title) {
        activityClasses.add(activityClass);
        activityTitles.add(title);
    }



    @Override
    public int getCount() {
        return activityClasses.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return activityTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        Class<?> activityClass = activityClasses.get(position);
        try {
            return (Fragment) activityClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
