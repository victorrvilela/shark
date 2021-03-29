package com.example.shark.view.ui.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shark.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoricLineHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.delivery_date)
    public TextView deliveryDate;
    @BindView(R.id.delivery_plate)
    public TextView deliveryPlate;
    @BindView(R.id.delivery_price)
    public TextView deliveryPrice;
    @BindView(R.id.delivery_city)
    public TextView deliveryCity;


    public HistoricLineHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
