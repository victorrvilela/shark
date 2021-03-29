package com.example.shark.services;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import com.example.shark.R;

import java.util.regex.Pattern;


public class Validation {

    public final static String TAG = "Shark";
    private static Pattern PATTERN_GENERIC = Pattern.compile("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}");
    private static Pattern PATTERN_NUMBERS = Pattern.compile("(?=^((?!((([0]{11})|([1]{11})|([2]{11})|([3]{11})|([4]{11})|([5]{11})|([6]{11})|([7]{11})|([8]{11})|([9]{11})))).)*$)([0-9]{11})");
    private static Pattern PATTERN_PLATE = Pattern.compile("(^[a-zA-Z]{3}[0-9][A-Za-z0-9][0-9]{2}$)");


    // validating password with retype password
    public static boolean isValidPassword(Activity context, String pass) {
        if (pass != null && pass.length() > 5) {
            return true;
        }
        if (pass == null || pass.isEmpty() || pass.trim().equals("")) {
            Toast.makeText(context, context.getResources().getString(R.string.validation_no_password), Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(context, context.getResources().getString(R.string.validation_invalid_password), Toast.LENGTH_LONG).show();
        return false;
    }

    // validating name
    public static boolean isValidUserName(Activity context, String name) {
        if (name != null && !name.trim().equals("")) {
            return true;
        }
        Toast.makeText(context, context.getResources().getString(R.string.validation_no_name), Toast.LENGTH_LONG).show();
        return false;
    }


    //validation cpf
    public static boolean isValidCPF(Activity context, String cpf) {
        if (cpf != null && PATTERN_GENERIC.matcher(cpf).matches()) {
            cpf = cpf.replaceAll("-|\\.", "");
            if (PATTERN_NUMBERS.matcher(cpf).matches()) {
                int[] numbers = new int[11];
                for (int i = 0; i < 11; i++) numbers[i] = Character.getNumericValue(cpf.charAt(i));
                int i;
                int sum = 0;
                int factor = 100;
                for (i = 0; i < 9; i++) {
                    sum += numbers[i] * factor;
                    factor -= 10;
                }
                int leftover = sum % 11;
                leftover = leftover == 10 ? 0 : leftover;
                if (leftover == numbers[9]) {
                    sum = 0;
                    factor = 110;
                    for (i = 0; i < 10; i++) {
                        sum += numbers[i] * factor;
                        factor -= 10;
                    }
                    leftover = sum % 11;
                    leftover = leftover == 10 ? 0 : leftover;
                    if (!(leftover == numbers[10])) {
                        Toast.makeText(context, context.getResources().getString(R.string.validation_invalid_cpf), Toast.LENGTH_LONG).show();
                        return false;
                    } else
                        return true;
                }
                Toast.makeText(context, context.getResources().getString(R.string.validation_invalid_cpf), Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (cpf == null || cpf.isEmpty()) {
            Toast.makeText(context, context.getResources().getString(R.string.validation_no_cpf), Toast.LENGTH_LONG).show();
            return false;
        }
        if (!PATTERN_GENERIC.matcher(cpf).matches()) {
            Toast.makeText(context, context.getResources().getString(R.string.validation_format_cpf), Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(context, context.getResources().getString(R.string.validation_invalid_cpf), Toast.LENGTH_LONG).show();
        return false;
    }

    public static boolean isValidPlate(Activity context, String plate) {
        if (plate != null && PATTERN_PLATE.matcher(plate).matches()) {
            plate = plate.replaceAll("-|\\.", "");
            if (PATTERN_PLATE.matcher(plate).matches()) {
                return true;
            }
            Toast.makeText(context, "Digite uma placa válida", Toast.LENGTH_LONG).show();
            return false;

        }

        if (plate == null || plate.isEmpty()) {
            Toast.makeText(context, "Preencha sua placa do carro", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!PATTERN_PLATE.matcher(plate).matches()) {
            Toast.makeText(context, "Sua placa deve estar no formato XXX-XXXX", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(context, context.getResources().getString(R.string.validation_invalid_cpf), Toast.LENGTH_LONG).show();
        return false;
    }


    //validation email
    public final static boolean isValidEmail(Activity context, String email) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(context, context.getResources().getString(R.string.validation_no_email), Toast.LENGTH_LONG).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, context.getResources().getString(R.string.validation_invalid_email), Toast.LENGTH_LONG).show();
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //validation phone
    public static boolean isValidPhoneNumber(Activity context, String phone) {

        if (!phone.trim().equals("") && phone.length() < 11) {
            Toast.makeText(context, context.getResources().getString(R.string.validation_invalid_phone), Toast.LENGTH_LONG).show();
            return false;
        }
        if (!phone.trim().equals("") && phone.length() > 10 && !Patterns.PHONE.matcher(phone).matches()) {
            Toast.makeText(context, context.getResources().getString(R.string.validation_invalid_phone), Toast.LENGTH_LONG).show();
            return false;
        }

        if (!phone.trim().equals("") && phone.length() > 10 && Patterns.PHONE.matcher(phone).matches()) {
            return true;
        }
        Toast.makeText(context, context.getResources().getString(R.string.validation_no_phone), Toast.LENGTH_LONG).show();
        return false;
    }


}
