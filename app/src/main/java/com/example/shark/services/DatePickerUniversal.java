package com.example.shark.services;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DatePickerUniversal implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private EditText mEditTextStart;
    private EditText mEditTextFinish;
    private Calendar mCalendar;
    private SimpleDateFormat mFormat;


    public DatePickerUniversal(EditText editTextStart, EditText editTextFinish, String format) {
        this.mEditTextStart = editTextStart;
        mEditTextFinish = editTextFinish;
        mEditTextStart.setOnFocusChangeListener(this);
        mEditTextStart.setOnClickListener(this);
        mFormat = new SimpleDateFormat(format, Locale.getDefault());
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            showPicker(view);
        }
    }

    @Override
    public void onClick(View view) {
        showPicker(view);
    }

    private void showPicker(View view) {
        if (mCalendar == null)
            mCalendar = Calendar.getInstance();

        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int month = mCalendar.get(Calendar.MONTH);
        int year = mCalendar.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(view.getContext(), this, year, month, day);
        Field mCalendar;
        try {
            mCalendar = dialog.getClass().getDeclaredField("mCalendar");
            mCalendar.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.getDatePicker().setMaxDate(new Date().getTime());
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        this.mEditTextStart.setText(String.format("%s", mFormat.format(mCalendar.getTime())));

        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d = f.parse(mFormat.format(mCalendar.getTime()));
            Long milliseconds = d.getTime();
            new FinalDatePickerUniversal(mEditTextFinish, d, "dd/MM/yyyy", view, mCalendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}