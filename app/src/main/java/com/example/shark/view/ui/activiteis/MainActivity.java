package com.example.shark.view.ui.activiteis;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.shark.R;
import com.example.shark.services.Utils;
import com.example.shark.view.ui.BaseActivity;
import com.google.android.material.navigation.NavigationView;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);


        Utils.setupUI(MainActivity.this, drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

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
//            Intent intent = new Intent(getBaseContext(), SecondaryActivity.class);
//            intent.putExtra("id", id);
//            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}