package com.example.shark.view.ui.activiteis;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.shark.R;
import com.example.shark.services.Utils;
import com.example.shark.services.Validation;
import com.example.shark.view.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotActivity extends BaseActivity {


    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.forgot)
    ConstraintLayout forgot;
    @BindView(R.id.btn_submit)
    AppCompatButton btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Utils.setupUI(this, findViewById(R.id.forgot));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_forgot;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (Validation.isValidEmail(this, username.getText().toString())) {
            finish();
        }
    }
}