package com.example.shark.view.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.fragment.app.DialogFragment;

import com.example.shark.R;
import com.example.shark.services.DatePickerUniversal;
import com.example.shark.services.FinalDatePickerUniversal;
import com.example.shark.services.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterDetailsFragment extends DialogFragment {

    public View view;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.value_content)
    LinearLayout valueContent;
    @BindView(R.id.content_main)
    LinearLayout contentMain;
    @BindView(R.id.filter_start)
    EditText filterStart;
    @BindView(R.id.filter_end)
    EditText filterEnd;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.ll_edit)
    LinearLayout llEdit;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    private Calendar mCalendar;

    public FilterDetailsFragment() {
        //
    }

    public static FilterDetailsFragment newInstance() {
        FilterDetailsFragment frag = new FilterDetailsFragment();
        return frag;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_filter_details, null);
        ButterKnife.bind(this, view);
        valueContent.setVisibility(View.VISIBLE);
        new DatePickerUniversal(filterStart, filterEnd, "dd/MM/yyyy");
        return view;
    }

    @OnClick(R.id.filter_start)
    public void onFilterStartClicked() {
        new DatePickerUniversal(filterStart, filterEnd, "dd/MM/yyyy");
    }

    @OnClick(R.id.filter_end)
    public void onFilterEndClicked() {
        if (filterStart.getText().toString().isEmpty()) {
            Utils.showToaster(getActivity(), "Selecione a data inicial!");
            new DatePickerUniversal(filterStart, filterEnd, "dd/MM/yyyy");
        } else {
            mCalendar = Calendar.getInstance(TimeZone.getDefault());
            String sDay = String.valueOf(filterStart.getText()).substring(0, 2);
            String sMonth = String.valueOf(filterStart.getText()).substring(3, 5);
            String sYear = String.valueOf(filterStart.getText()).substring(6, 10);
            int day, month, year;
            try {
                day = Integer.parseInt(sDay);
                month = Integer.parseInt(sMonth) - 1;
                year = Integer.parseInt(sYear);
            } catch (NumberFormatException e) {
                day = mCalendar.get(Calendar.DAY_OF_MONTH);
                month = mCalendar.get(Calendar.DATE) + 1;
                year = mCalendar.get(Calendar.YEAR);
            }
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, day);
            SimpleDateFormat mFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date date = f.parse(mFormat.format(mCalendar.getTime()));
                new FinalDatePickerUniversal(filterEnd, date, "dd/MM/yyyy", view, mCalendar);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.btn_cancel)
    public void onBtnCancelClicked() {
        dismiss();
    }

    @OnClick(R.id.btn_submit)
    public void onBtnSubmitClicked() {
        try {
            Date firstDate;
            Date secondDate;
            SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
            firstDate = dates.parse(filterStart.getText().toString());
            secondDate = dates.parse(filterEnd.getText().toString());
            if (filterStart.getText().toString().isEmpty()) {
                Utils.showToaster(getActivity(), "Selecione a data inicial!");
            } else if (filterEnd.getText().toString().isEmpty()) {
                Utils.showToaster(getActivity(), "Selecione a data final!");
            } else if (firstDate.compareTo(secondDate) > 0) {
                Utils.showToaster(getActivity(), "Data inicial maior que data final. Selecione novamente!");
                filterStart.setText(String.format("%s", ""));
                filterEnd.setText(String.format("%s", ""));
            } else {
                Intent i = new Intent()
                        .putExtra("start", filterStart.getText().toString())
                        .putExtra("end", filterEnd.getText().toString());
                Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                dismiss();
            }
        } catch (ParseException e) {
            Utils.showToaster(getActivity(), "Data inicial maior que data final. Selecione novamente!");
            filterStart.setText(String.format("%s", ""));
            filterEnd.setText(String.format("%s", ""));
            e.printStackTrace();
        }

    }
}