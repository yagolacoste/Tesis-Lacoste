package com.Tesis.bicycle.Activity.ui.Fragment;

import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Tesis.bicycle.R;

public class AchievementsFragment extends Fragment {

    private static final String ARG_TAB_NAME = "ARG_TAB_NAME";

    private TextView acTextViewCompete,acTextViewSpeed,recordSpeedDate,acTextViewDistance,recordDistanceDate,acTextViewTime,recordTimeDate;


    public AchievementsFragment() {

    }


    public static AchievementsFragment newInstance(@StringRes int tabName) {
        AchievementsFragment fragment = new AchievementsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TAB_NAME, tabName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_achievements, container, false);
        acTextViewCompete=view.findViewById(R.id.ac_textView_compete);
        acTextViewSpeed=view.findViewById(R.id.ac_textView_speed);
        recordSpeedDate=view.findViewById(R.id.record_speed_date);
        acTextViewDistance=view.findViewById(R.id.ac_textView_distance);
        recordDistanceDate=view.findViewById(R.id.record_distance_date);
        acTextViewTime=view.findViewById(R.id.ac_textView_time);
        recordTimeDate=view.findViewById(R.id.record_time_date);
        return view;
    }
}