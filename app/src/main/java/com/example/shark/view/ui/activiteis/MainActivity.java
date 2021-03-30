package com.example.shark.view.ui.activiteis;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.shark.R;
import com.example.shark.services.Utils;
import com.example.shark.view.ui.BaseActivity;
import com.example.shark.view.ui.fragments.HomeFragment;
import com.example.shark.view.ui.fragments.MapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.example.shark.view.ui.fragments.MapFragment.LOCATION_PERMISSION_REQUEST_CODE;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rootFrame)
    FrameLayout rootFrame;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    float currentLatitude = 0;
    float currentLongitude = 0;
    String currentProvider = "";
    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {

            currentLatitude = (float) location.getLatitude();
            currentLongitude = (float) location.getLongitude();
            currentProvider = location.getProvider();
            centerCameraToPosition(currentLatitude, currentLongitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
            currentProvider = provider;
        }

        @Override
        public void onProviderDisabled(String provider) {
            currentProvider = "";

        }
    };
    private Boolean GPSsignal = false;
    private LocationManager locationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        ButterKnife.bind(this);
        checkPermission();
        setSupportActionBar(toolbar);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        Utils.setupUI(MainActivity.this, drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (!isFinishing()) {
            Log.d(Utils.TAG, "teste: 2");

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rootFrame, new HomeFragment());
            ft.commitAllowingStateLoss();
        }

    }

    public void centerCameraToPosition(double lat, double lng) {
        LatLng latLng = new LatLng(lat, lng);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(17)
                .build();

        MapFragment.mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private boolean checkPermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //Ask for the permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return false;
        }

        return true;
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        //to prevent current item select over and over
        if (item.isChecked()) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }
        displaySelectedScreen(id);

        return false;
    }

    private void displaySelectedScreen(int id) {

        drawerLayout.closeDrawer(GravityCompat.START);
        if (id == R.id.nav_logout) {
            this.logOut();
        } else if (id != R.id.nav_home) {
//            if (id == R.id.nav_scheduling) {
//                Toast.makeText(this, R.string.prompt_development, Toast.LENGTH_LONG).show();
//            } else {
            Intent intent = new Intent(getBaseContext(), SecondaryActivity.class);
            intent.putExtra("id", id);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkPermission()) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    0,
                    10, locationListenerGPS);
            buildAlertMessageNoGps();
        }
    }


    private void buildAlertMessageNoGps() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Seu GPS está desligado, deseja liga-lo?")
                    .setCancelable(false)
                    .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            GPSsignal = true;
//                            dialog.dismiss();

                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.dismiss();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (!isFinishing()) {
                        Log.d(Utils.TAG, "teste: 1");
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.rootFrame, new HomeFragment());
                        ft.commitAllowingStateLoss();
                    }
                }

                return;
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
