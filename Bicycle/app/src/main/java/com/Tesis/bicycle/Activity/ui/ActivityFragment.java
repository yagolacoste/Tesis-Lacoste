package com.Tesis.bicycle.Activity.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Tesis.bicycle.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityFragment extends Fragment {

    private static final String ARG_ACTIVITY_CLASS = "activity_class";

    public static ActivityFragment newInstance(Class<?> activityClass) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ACTIVITY_CLASS, activityClass);

        ActivityFragment fragment = new ActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_activity, container, false);

        Class<?> activityClass = (Class<?>) getArguments().getSerializable(ARG_ACTIVITY_CLASS);
        Intent intent = new Intent(getActivity(), activityClass);
        startActivity(intent);

        return rootView;
    }
}