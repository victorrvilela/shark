package com.example.shark.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shark.R;
import com.example.shark.services.Utils;
import com.example.shark.view.ui.holders.HistoricLineHolder;
import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

public class HistoricAdapter extends RecyclerView.Adapter<HistoricLineHolder> {


    private SimpleDateFormat format = new SimpleDateFormat("dd/MM");
    private List<ParseObject> mHistoric;
    private Activity mContext;

    public HistoricAdapter(Activity context, List<ParseObject> historics) {
        mHistoric = historics;
        mContext = context;
    }

    public void setmHistoric(List<ParseObject> mHistoric) {
        this.mHistoric = mHistoric;
    }

    @Override
    public HistoricLineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoricLineHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_historic_line_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricLineHolder holder, int position) {
        holder.deliveryDate.setText(String.format("%s", format.format(Objects.requireNonNull(mHistoric.get(position).getDate("createdAt")))));
        holder.deliveryPlate.setText(String.format("%s", mHistoric.get(position).getString("plate")));
        holder.deliveryCity.setText(String.format("%s", mHistoric.get(position).getString("city")));
        holder.deliveryPrice.setText(String.format("%s", Utils.formatCurrency(mHistoric.get(position).getDouble("price"))));
    }

    @Override
    public int getItemCount() {
        return mHistoric != null ? mHistoric.size() : 0;
    }

}