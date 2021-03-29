package com.example.shark.services;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.shark.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Utils {

    public final static String TAG = "Shark";


    public final static int IMPORTANT_NOTIFICATION = 1;

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            View focusedView = activity.getCurrentFocus();
            if (focusedView != null) {
                inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);

            }
        }
    }

    public static void setupUI(final Activity activity, View view) {
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                if (!(innerView instanceof EditText)) {
                    view.setOnTouchListener(new View.OnTouchListener() {

                        public boolean onTouch(View v, MotionEvent event) {
                            hideSoftKeyboard(activity);
                            return false;
                        }
                    });
                }
            }
        }
    }

    public static void showToaster(Activity context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }



    public static boolean isEmpty(EditText etText) {
        return (etText.getText().toString().trim().length() == 0);
    }

    public static AlertDialog showConfirmAlert(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(context.getString(R.string.action_confirm), (dialog, which) -> {
                })
                .setNegativeButton(context.getString(R.string.action_cancel), (dialog, which) -> {
                }).show();

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorTextLight));

        return alertDialog;
    }

    public static String formatCurrency(Number value) {
        NumberFormat formated = NumberFormat.getCurrencyInstance();
        formated.setMaximumFractionDigits(2);
        formated.setCurrency(Currency.getInstance("BRL"));
        return formated.format(value);
    }

    public static String checkEmpty(String text) {
        return (text != null) ? text : "";
    }



    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }

    public static String formatHours(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("kk:mm");
        return format.format((date));
    }

    public static int createID() {
        return Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.FRENCH).format(new Date()));
    }




}