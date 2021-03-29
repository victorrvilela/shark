package com.example.shark.view.ui.activiteis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.shark.R;
import com.example.shark.services.Utils;
import com.example.shark.services.Validation;
import com.parse.ParseObject;
import com.santalu.maskedittext.MaskEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Activity activity;

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.phone)
    MaskEditText phone;
    @BindView(R.id.CPF)
    MaskEditText cpf;
    @BindView(R.id.plate)
    MaskEditText plate;
    @BindView(R.id.btn_submit_sign)
    AppCompatButton btnSubmitSign;
    @BindView(R.id.container)
    LinearLayout container;

    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Utils.setupUI(SignupActivity.this, findViewById(R.id.container));
        activity = this;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void logged() {
        getPackageManager().clearPackagePreferredActivities(getPackageName());
        Intent mainActivity = new Intent(getApplicationContext(), LoginActivity.class);
        mainActivity.addFlags(FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | FLAG_ACTIVITY_NEW_TASK);
        mainActivity.setAction(Intent.ACTION_MAIN);
        mainActivity.addCategory(Intent.CATEGORY_HOME);
        finish();
        startActivity(mainActivity);
    }


    @OnClick(R.id.btn_submit_sign)
    public void onBtnSubmitSignClicked() {
        ParseObject objectDelivery = new ParseObject("Users");
        objectDelivery.put("name", name.getText().toString());
        objectDelivery.put("cpf", cpf.getRawText());
        objectDelivery.put("email", username.getText().toString());
        objectDelivery.put("password", password.getText().toString());
        objectDelivery.put("phone", phone.getRawText());
        objectDelivery.put("plate", plate.getRawText());

        loading.setVisibility(View.VISIBLE); //to show
        if (Validation.isValidUserName(activity, name.getText().toString()) &&
                Validation.isValidCPF(activity, cpf.getRawText()) &&
                Validation.isValidEmail(activity, username.getText().toString()) &&
                Validation.isValidPassword(activity, password.getText().toString()) &&
                Validation.isValidPhoneNumber(activity, phone.getRawText()) &&
                Validation.isValidPlate(activity, plate.getRawText())) {
            objectDelivery.pinInBackground(e -> {
                if (e == null) {
                    loading.setVisibility(View.GONE);
                    logged();
                }
            });
        }
        loading.setVisibility(View.GONE);

    }

}