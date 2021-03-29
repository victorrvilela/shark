package com.example.shark.view.ui.holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shark.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinancialLineHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_date)
    public TextView textDate;
    @BindView(R.id.text_type)
    public TextView textType;
    @BindView(R.id.text_value)
    public TextView textValue;

    public FinancialLineHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
