package com.example.shark.view.ui.activiteis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shark.R;
import com.example.shark.services.Utils;
import com.example.shark.services.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.btn_signup)
    TextView btnSignup;
    @BindView(R.id.btn_forgot)
    TextView btnForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        Utils.setupUI(this, findViewById(R.id.container));

        password.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btnSubmit.performClick();
                    return true;
                }
                return false;
            }
        });


        //TODO retirar conta
        username.setText("tester.shark@shark.com.br");
        password.setText("shark123");
    }

    @OnClick({R.id.btn_submit, R.id.btn_signup, R.id.btn_forgot})
    public void onViewClicked(View view) {
        Utils.hideSoftKeyboard(this);

        switch (view.getId()) {
            case R.id.btn_submit:
                blockButtons();
                loading.setVisibility(View.VISIBLE);
                if (Validation.isValidEmail(this, username.getText().toString()) &&
                        Validation.isValidPassword(this, password.getText().toString())) {
                    loading.setVisibility(View.GONE);
                    logged();
                    enableButtons();
                } else {
                    loading.setVisibility(View.GONE);
                    enableButtons();
                }
                break;
            case R.id.btn_signup:
//                blockButtons();
//                Intent signupActivity = new Intent(getApplicationContext(), SignupActivity.class);
//                startActivity(signupActivity);
                break;
            case R.id.btn_forgot:
//                blockButtons();
//                Intent forgotActivity = new Intent(getApplicationContext(), ForgotActivity.class);
//                startActivity(forgotActivity);
                break;
        }
    }

    public void blockButtons() {
        btnSubmit.setClickable(false);
        btnSignup.setClickable(false);
        btnForgot.setClickable(false);
    }

    public void enableButtons() {
        btnSubmit.setClickable(true);
        btnSignup.setClickable(true);
        btnForgot.setClickable(true);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        enableButtons();
    }

    private void logged() {
        getPackageManager().clearPackagePreferredActivities(getPackageName());
        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        mainActivity.addFlags(FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mainActivity.setAction(Intent.ACTION_MAIN);
        mainActivity.addCategory(Intent.CATEGORY_HOME);
        finish();
        startActivity(mainActivity);
    }
}