package com.example.shark.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shark.R;
import com.example.shark.services.Utils;
import com.example.shark.view.ui.holders.FinancialLineHolder;
import com.parse.ParseObject;

import java.util.List;

public class FinancialAdapter extends RecyclerView.Adapter<FinancialLineHolder> {

    private List<ParseObject> mList;
    private Context mContext;

    public FinancialAdapter(List<ParseObject> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public FinancialLineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FinancialLineHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_financial_line_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FinancialLineHolder holder, int position) {
        holder.textDate.setText(String.format("%s", mList.get(position).getString("date")));
        holder.textValue.setText(String.format("%s", Utils.formatCurrency(mList.get(position).getDouble("value"))));
        holder.textDate.setTextColor(mContext.getResources().getColor(R.color.colorGray));
        holder.textType.setTextColor(mContext.getResources().getColor(R.color.colorGray));
        if (mList.get(position).getDouble("value") < 0) {
            holder.textValue.setTextColor(mContext.getResources().getColor(R.color.colorRed));
        } else {
            holder.textValue.setTextColor(mContext.getResources().getColor(R.color.colorGray));
        }
        switch (mList.get(position).getInt("type")) {
            case 1:
                holder.textType.setText(String.format("%s", "Multa de velocidade"));
                break;
            case 2:
                holder.textType.setText(String.format("%s", "Pedágio"));
                break;
            case 3:
                holder.textType.setText(String.format("%s", "Crédito"));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }
}
