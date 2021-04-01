package com.example.shark.view.ui.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.shark.R;
import com.parse.ParseObject;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MarkerDetailsFragment extends DialogFragment {

    public View view;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.value_content)
    LinearLayout valueContent;
    @BindView(R.id.marker_toll)
    EditText markerToll;
    @BindView(R.id.title_marker)
    TextView titleMarker;
    @BindView(R.id.btn_submit_marker)
    Button btnSubmit;
    @BindView(R.id.btn_cancel_marker)
    Button btnCancel;
    private String current;
    public MarkerDetailsFragment() {
        current = "";
    }

    public static MarkerDetailsFragment newInstance() {
        MarkerDetailsFragment frag = new MarkerDetailsFragment();
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

        View view = inflater.inflate(R.layout.fragment_filter_details, null);
        ButterKnife.bind(this, view);
//        Bundle mArgs = getArguments();
//        String mCity = mArgs.getString("key");
        textWatcher();
        return view;
    }

    public void textWatcher() {
        markerToll.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int aft) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    markerToll.removeTextChangedListener(this);

                    String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
                    String cleanString = s.toString().replaceAll(replaceable, "");

                    double parsed;
                    try {
                        parsed = Double.parseDouble(cleanString);
                    } catch (NumberFormatException e) {
                        parsed = 0.00;
                    }
                    String formatted = NumberFormat.getCurrencyInstance().format((parsed/100));

                    current = formatted;
                    markerToll.setText(formatted);
                    markerToll.setSelection(formatted.length());
                    markerToll.addTextChangedListener(this);
                }
            }
        });
    }


    @OnClick(R.id.btn_cancel_marker)
    public void onBtnCancelClicked() {
        dismiss();
    }

    @OnClick(R.id.btn_submit_marker)
    public void onBtnSubmitClicked() {
        String pattern = "dd/MM";
        DateFormat df = new SimpleDateFormat(pattern);
        String todayAsString = df.format(new Date());

        String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
        String cleanString = markerToll.getText().toString().replaceAll(replaceable, "");

        ParseObject objectDelivery = new ParseObject("Pedagios");
        objectDelivery.put("plate", "CTH-4546");
        objectDelivery.put("date", todayAsString);
        objectDelivery.put("city", "Teste");
        objectDelivery.put("price", (Double.parseDouble(cleanString)/100));
        objectDelivery.pinInBackground();


        dismiss();
    }
}