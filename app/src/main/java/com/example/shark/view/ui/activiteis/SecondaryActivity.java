package com.example.shark.view.ui.activiteis;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.shark.R;
import com.example.shark.view.ui.BaseActivity;
import com.example.shark.view.ui.fragments.AccountFragment;
import com.example.shark.view.ui.fragments.FinancialFragment;
import com.example.shark.view.ui.fragments.HistoricFragment;
import com.example.shark.view.ui.fragments.SettingsFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondaryActivity extends BaseActivity {

    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.rootFrameSecondary)
    FrameLayout rootFrameSecondary;
    @BindView(R.id.Secondary)
    ConstraintLayout Secondary;


    private boolean scheduling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        Fragment fragment = null;
        (this.getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_close_white);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        switch (Objects.requireNonNull(getIntent().getIntExtra("id", 0))) {
            case R.id.nav_account:
                fragment = new AccountFragment();
                break;
            case R.id.nav_deliveries:
                fragment = new HistoricFragment();
                break;
            case R.id.nav_settings:
                fragment = new SettingsFragment();
                break;
            case R.id.nav_financial:
                fragment = new FinancialFragment();
                break;
            default:
                break;
        }
        //replacing the fragment
        if (fragment != null && !isFinishing()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rootFrameSecondary, fragment);
            ft.commitAllowingStateLoss();
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_secondary;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }


}