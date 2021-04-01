package com.example.shark.view.ui.activiteis;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

import com.example.shark.R;
import com.example.shark.services.Utils;
import com.example.shark.services.Validation;
import com.example.shark.view.ui.BaseActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsPasswordActivity extends BaseActivity {
    @BindView(R.id.password)
    TextInputEditText password;


    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.btn_submit)
    AppCompatButton btnSubmit;
    @BindView(R.id.new_password)
    TextInputEditText newPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setupUI(this, findViewById(R.id.SettingsPassword));
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_settings_password;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        loading.setVisibility(View.VISIBLE);
        if (!(password.getText().toString().isEmpty())) {
            if (Validation.isValidPassword(this, password.getText().toString()) &&
                    password.getText().toString().equals(newPassword.getText().toString())) {

                AlertDialog alertDialog = Utils.showConfirmAlert(this,
                        "Alerta",
                        "Deseja mesmo mudar a senha?");
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(view -> {
                    alertDialog.dismiss();
                    loading.setVisibility(View.GONE);
                });


                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
                    ParseQuery<ParseObject> parseUsers = ParseQuery.getQuery("Login");
                    parseUsers.fromLocalDatastore();
                    parseUsers.findInBackground((object, e) -> {
                        if (e == null) {
                            if (object.size() == 1) {
                                    ParseQuery<ParseObject> parseLogin = ParseQuery.getQuery("Users");
                                    parseLogin.fromLocalDatastore();
                                    parseLogin.whereEqualTo("email", object.get(0).getString("user"));
                                    parseLogin.getFirstInBackground((login, e1) -> {
                                        if (e1 == null) {
                                            login.put("password", newPassword.getText().toString());
                                            alertDialog.dismiss();
                                            loading.setVisibility(View.GONE);
                                            finish();
                                        }
                                    });
                            }
                        }
                    });

                });

                alertDialog.setOnCancelListener(
                        new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                loading.setVisibility(View.GONE);
                            }
                        }
                );
            } else {
                loading.setVisibility(View.GONE);
                if (password.length() > 5 && !password.getText().toString().equals(newPassword.getText().toString()))
                    Utils.showToaster(this, "As senhas não conferem.");
            }
        } else {
            loading.setVisibility(View.GONE);
            Utils.showToaster(this, "Digite uma senha válida");
        }
    }

}