package com.Tesis.bicycle.Activity.ui.Fragment;

import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Tesis.bicycle.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassificationFragment extends Fragment {

    private static final String ARG_TAB_NAME = "ARG_TAB_NAME";

    public ClassificationFragment() {
        // Required empty public constructor
    }

    public static ClassificationFragment newInstance(@StringRes int tabName) {
        ClassificationFragment fragment = new ClassificationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TAB_NAME, tabName);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_classification, container, false);
    }
}