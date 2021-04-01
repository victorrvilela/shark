package com.example.shark.view.ui.activiteis;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.shark.R;
import com.example.shark.services.Utils;
import com.example.shark.services.Validation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
        username.setText("diego@hotmail.com");
        password.setText("dev123");
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
                    ParseQuery<ParseObject> parseUsers = ParseQuery.getQuery("Users");
                    parseUsers.whereEqualTo("email", username.getText().toString());
                    parseUsers.whereEqualTo("password", password.getText().toString());
                    parseUsers.fromLocalDatastore();
                    parseUsers.findInBackground((objects, e) -> {
                        if (e == null) {
                            if (objects.size() == 1) {
                                loading.setVisibility(View.GONE);
                                logged(username.getText().toString());
                            } else {
                                Toast.makeText(this, "Usuário ou senha incorreta!", Toast.LENGTH_LONG).show();
                                loading.setVisibility(View.GONE);
                            }
                            enableButtons();
                        }
                    });
                } else {
                    loading.setVisibility(View.GONE);
                    enableButtons();
                }
                break;
            case R.id.btn_signup:
                blockButtons();
                Intent signupActivity = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(signupActivity);
                break;
            case R.id.btn_forgot:
                blockButtons();
                Intent forgotActivity = new Intent(getApplicationContext(), ForgotActivity.class);
                startActivity(forgotActivity);
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
//        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        enableButtons();
    }

    private void logged(String user) {
        ParseObject objectDelivery = new ParseObject("Login");
        objectDelivery.put("user", user);
        objectDelivery.pinInBackground();
        getPackageManager().clearPackagePreferredActivities(getPackageName());
        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        mainActivity.addFlags(FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mainActivity.setAction(Intent.ACTION_MAIN);
        mainActivity.addCategory(Intent.CATEGORY_HOME);
        populateLocalData();
        finish();
        startActivity(mainActivity);
    }



    private void populateLocalData() {
        ParseQuery<ParseObject> parsePedagios = ParseQuery.getQuery("Pedagios");
        parsePedagios.fromLocalDatastore();
        parsePedagios.findInBackground((objects, e) -> {
            if (e == null) {
                if (objects.size() == 0) {
                    createLocalPedagios("CTH-4546", "11/07", "Sertãozinho", 13.45);
                    createLocalPedagios("CTH-4546", "11/07", "Sertãozinho", 13.45);
                    createLocalPedagios("CTH-4546", "14/07", "São Paulo", 7.31);
                    createLocalPedagios("CTH-4546", "16/07", "Ouro Preto", 11.00);
                    createLocalPedagios("CTH-4546", "16/07", "Ouro Preto", 11.00);
                    createLocalPedagios("CTH-4546", "28/07", "Mariana", 6.34);
                    createLocalPedagios("CTH-4546", "29/07", "Mariana", 6.34);
                }
            }
        });

        ParseQuery<ParseObject> parsePayments = ParseQuery.getQuery("Payments");
        parsePayments.fromLocalDatastore();
        parsePayments.findInBackground((objects, e) -> {
            if (e == null) {
                if (objects.size() == 0) {
                    createLocalPayments(30, 50);
                    createLocalPayments(100, 120);
                    createLocalPayments(150, 200);
                    createLocalPayments(800, 1000);
                }
            }
        });

        ParseQuery<ParseObject> parseCashBook = ParseQuery.getQuery("CashBook");
        parseCashBook.fromLocalDatastore();
        parseCashBook.findInBackground((objects, e) -> {
            if (e == null) {
                if (objects.size() == 0) {
                    createLocalCashBook("19/11/2020", 1, -13.56);
                    createLocalCashBook("19/11/2020", 2, -4.87);
                    createLocalCashBook("19/11/2020", 2, -4.87);
                    createLocalCashBook("21/11/2020", 3, 29);
                    createLocalCashBook("27/11/2020", 1, -4.87);
                    createLocalCashBook("27/11/2020", 1, -4.87);
                }
            }
        });
    }

    private void createLocalPedagios(String plate, String date, String city, Double price) {
        ParseObject objectDelivery = new ParseObject("Pedagios");
        objectDelivery.put("plate", plate);
        objectDelivery.put("date", date);
        objectDelivery.put("city", city);
        objectDelivery.put("price", price);
        objectDelivery.pinInBackground();
    }

    private void createLocalCashBook(String date, int type, double price) {
        ParseObject objectDelivery = new ParseObject("CashBook");
        objectDelivery.put("date", date);
        objectDelivery.put("type", type);
        objectDelivery.put("value", price);
        objectDelivery.pinInBackground();
    }

    private void createLocalPayments(int price, double value) {
        ParseObject objectDelivery = new ParseObject("Payments");
        objectDelivery.put("price", price);
        objectDelivery.put("value", value);
        objectDelivery.pinInBackground();
    }
}