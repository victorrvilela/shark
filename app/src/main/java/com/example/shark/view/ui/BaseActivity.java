package com.example.shark.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shark.services.Utils;
import com.example.shark.view.ui.activiteis.LoginActivity;

import java.util.Locale;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        Locale.setDefault(new Locale("pt", "BR"));
    }

    protected abstract int getLayoutResourceId();

    public void logOut() {
        Log.d(Utils.TAG, "logOut: ");
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }


}