package com.example.shark.view.ui.holders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shark.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddBalanceLineHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.add_value)
    public TextView addValue;
    @BindView(R.id.value_to_pay)
    public TextView valueToPay;
    @BindView(R.id.next_linear_layout)
    public LinearLayout nextLinearLayout;


    public AddBalanceLineHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
