package com.Tesis.bicycle.Activity.ui.NewRoute;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.Tesis.bicycle.Activity.MenuActivity;
import com.Tesis.bicycle.Activity.TrackingActivity;
import com.Tesis.bicycle.R;
import com.Tesis.bicycle.databinding.FragmentFreeRouteBinding;

public class FreeRouteFragment extends Fragment {

    private FragmentFreeRouteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_routes,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent i=new Intent(getActivity(), TrackingActivity.class);
        startActivity(i);
    }
}