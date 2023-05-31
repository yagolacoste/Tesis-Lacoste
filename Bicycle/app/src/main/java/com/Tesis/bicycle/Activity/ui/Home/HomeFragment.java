package com.Tesis.bicycle.Activity.ui.Home;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Tesis.bicycle.R;
import com.Tesis.bicycle.databinding.FragmentHomeBinding;
import com.Tesis.bicycle.databinding.FragmentStatisticBinding;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }



}