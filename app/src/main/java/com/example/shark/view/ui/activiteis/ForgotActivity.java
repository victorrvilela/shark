package com.example.shark.view.ui.activiteis;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.shark.R;
import com.example.shark.services.Utils;
import com.example.shark.services.Validation;
import com.example.shark.view.ui.BaseActivity;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
    @BindView(R.id.submit)
    AppCompatButton submit;

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

    @OnClick(R.id.submit)
    public void onSubmitClicked() {
        if (Validation.isValidEmail(this, username.getText().toString())) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Users");
            query.fromLocalDatastore();
            query.whereEqualTo("email", username.getText().toString());
            query.getFirstInBackground((object, e1) -> {
                loading.setVisibility(View.GONE);
                if (e1 == null && object != null) {
                    Utils.showToaster(this, getString(R.string.success_forgot));
                    finish();
                } else {
                    Utils.showToaster(this, this.getResources().getString(R.string.invalid_username));
                }
            });
        }
    }
}