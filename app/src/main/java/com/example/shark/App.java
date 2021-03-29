package com.example.shark;

import android.app.Application;
import android.content.Intent;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.shark.view.ui.activiteis.LoginActivity;
import com.google.android.libraries.places.api.Places;
import com.parse.Parse;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class App extends Application implements LifecycleObserver {


    private boolean isAppInBackground = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                        .applicationId("shark")
                        .clientKey("yourclientkey")
                        .server("https://parse.serverurl.com.br/pg/")
                        .enableLocalDataStore()
                        .build()
        );


        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        Intent mainActivity;

        getPackageManager().clearPackagePreferredActivities(getPackageName());
        mainActivity = new Intent(App.this, LoginActivity.class);
        mainActivity.addFlags(FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | FLAG_ACTIVITY_NEW_TASK);
        mainActivity.setAction(Intent.ACTION_MAIN);
        mainActivity.addCategory(Intent.CATEGORY_HOME);
        startActivity(mainActivity);


        if(!Places.isInitialized()){
            Places.initialize(getApplicationContext(), "AIzaSyA-599I-n7mK2Iy_2Z1vhipDmBaFWfACYs");
        }
    }

    //
    public boolean isAppInBackground() {
        return isAppInBackground;
    }

    private void setAppInBackground(boolean appInBackground) {
        isAppInBackground = appInBackground;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onAppBackgrounded() {
        //App in background
        this.setAppInBackground(true);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onAppForegrounded() {
        this.setAppInBackground(false);
    }


}