package com.example.shark.view.ui.fragments;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;



public abstract class BaseFragment extends Fragment {


    public static final int DIALOG_FRAGMENT = 1;
    public FragmentActivity currentActivity;
    Location userLocation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivity = getActivity();

    }

}
