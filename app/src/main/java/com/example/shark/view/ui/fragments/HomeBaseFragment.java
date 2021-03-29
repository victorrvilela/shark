package com.example.shark.view.ui.fragments;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.parse.ParseObject;
import com.parse.ParseUser;

public abstract class HomeBaseFragment extends Fragment {

    public ParseUser currentUser;
    public FragmentActivity currentActivity;
    Location userLocation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = ParseUser.getCurrentUser();
        currentActivity = getActivity();


    }

    public double checkDistance(ParseObject ride) {

        Location endPoint = new Location("locationA");
        endPoint.setLatitude(ride.getDouble("lat"));
        endPoint.setLongitude(ride.getDouble("lng"));

        // resultado em metros, entao sera dividido para km
        return getUserLocation().distanceTo(endPoint) / 1000.0;
    }

    public Location getUserLocation() {
        if (userLocation != null)
            return userLocation;

        return new Location("");
    }

}
