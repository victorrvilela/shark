package com.example.shark.services;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class FinalDatePickerUniversal implements DatePickerDialog.OnDateSetListener {

    private EditText mEditTextFinal;
    private Calendar mCalendar;
    private SimpleDateFormat mFormat;

    public FinalDatePickerUniversal(EditText editText, Date date, String format, View view, Calendar mCalendario) {
        mEditTextFinal = editText;
        mFormat = new SimpleDateFormat(format, Locale.getDefault());
        showPicker(view, date, mCalendario);

    }


    private void showPicker(View view, Date date, Calendar mCalendario) {
        if (mCalendar == null)
            mCalendar = Calendar.getInstance();

        int day = mCalendario.get(Calendar.DAY_OF_MONTH);
        int month = mCalendario.get(Calendar.MONTH);
        int year = mCalendario.get(Calendar.YEAR);

        mCalendario.set(Calendar.YEAR, year);
        mCalendario.set(Calendar.MONTH, month);
        mCalendario.set(Calendar.DAY_OF_MONTH, day);

        DatePickerDialog dialog = new DatePickerDialog(view.getContext(), this, year, month, day);
        Field mCalendar;
        try {
            mCalendar = dialog.getClass().getDeclaredField("mCalendar");
            mCalendar.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Long milliseconds = date.getTime();
        dialog.getDatePicker().setMinDate(milliseconds);
        dialog.getDatePicker().setMaxDate(new Date().getTime());
        dialog.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        this.mEditTextFinal.setText(String.format("%s", mFormat.format(mCalendar.getTime())));

    }


}
