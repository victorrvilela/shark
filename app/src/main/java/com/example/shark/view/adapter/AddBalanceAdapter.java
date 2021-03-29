package com.example.shark.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shark.R;
import com.example.shark.services.Utils;
import com.example.shark.view.ui.holders.AddBalanceLineHolder;
import com.parse.ParseObject;

import java.util.List;

public class AddBalanceAdapter extends RecyclerView.Adapter<AddBalanceLineHolder> {
    private List<ParseObject> mList;


    public AddBalanceAdapter(List<ParseObject> list) {
        mList = list;


    }

    @Override
    public AddBalanceLineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddBalanceLineHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_add_balance, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddBalanceLineHolder holder, int position) {
        holder.addValue.setText(String.format("%s", Utils.formatCurrency(mList.get(position).getInt("value"))));
        holder.valueToPay.setText(String.format("%s", Utils.formatCurrency(mList.get(position).getInt("price"))));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }
}
