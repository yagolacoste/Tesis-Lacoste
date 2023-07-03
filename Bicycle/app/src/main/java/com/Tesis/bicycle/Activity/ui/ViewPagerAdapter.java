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

    private final List<Class<?>> fragmentClasses = new ArrayList<>();
    private final List<String> fragmentTitles = new ArrayList<>();

    private Context context;

    public ViewPagerAdapter(FragmentManager fragmentManager,Context context) {
        super(fragmentManager);
        this.context=context;
    }


    public void addFragment(Class<?> fragmentClass, String title) {
        fragmentClasses.add(fragmentClass);
        fragmentTitles.add(title);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Class<?> fragmentClass = fragmentClasses.get(position);
        return ActivityFragment.newInstance(fragmentClass);
    }

    @Override
    public int getCount() {
        return fragmentClasses.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }
}
