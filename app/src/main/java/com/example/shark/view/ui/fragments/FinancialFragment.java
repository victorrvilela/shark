package com.example.shark.view.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shark.R;
import com.example.shark.services.Utils;
import com.example.shark.view.adapter.FinancialAdapter;
import com.example.shark.view.ui.activiteis.AddBalanceActivity;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FinancialFragment extends BaseFragment {


    @BindView(R.id.account_balance)
    TextView accountBalance;
    @BindView(R.id.btn_add_balance)
    AppCompatButton btnAddBalance;
    @BindView(R.id.tableRow1)
    TableRow tableRow1;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.empty_content)
    LinearLayout emptyContent;
    @BindView(R.id.ll_loading)
    FrameLayout llLoading;

    private FinancialAdapter mAdapter;
    private SimpleDateFormat format;


    public FinancialFragment() {
        format = new SimpleDateFormat("dd/MM/yyyy");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_financial, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        llLoading.setVisibility(View.VISIBLE);
        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("CashBook");
        parseQuery.fromLocalDatastore();
        parseQuery.findInBackground((objects, e) -> {
            if (e == null) {
                if (objects.size() > 0) {
                    int balance = 0;
                    for (int i = 0; i < objects.size(); i++) {
                        balance += objects.get(i).getDouble("value");
                    }
                    accountBalance.setText(String.format("%s", Utils.formatCurrency(balance)));
                    setupRecycler(objects, currentActivity);
                    llLoading.setVisibility(View.GONE);
                } else {
                    createLocalData("19/11/2020", 1, -13.56);
                    createLocalData("19/11/2020", 2, -4.87);
                    createLocalData("19/11/2020", 2, -4.87);
                    createLocalData("21/11/2020", 3, 29);
                    createLocalData("27/11/2020", 1, -4.87);
                    createLocalData("27/11/2020", 1, -4.87);
                    ParseQuery<ParseObject> secondQuery = ParseQuery.getQuery("CashBook");
                    secondQuery.fromLocalDatastore();
                    secondQuery.findInBackground((list, e1) -> {
                        if (e1 == null) {
                            if (list.size() > 0) {
                                int balance = 0;
                                for (int i = 0; i < list.size(); i++) {
                                    balance += list.get(i).getInt("value");
                                }
                                accountBalance.setText(String.format("%s", Utils.formatCurrency(balance)));
                                setupRecycler(list, currentActivity);
                                llLoading.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });


        return view;
    }

    private void createLocalData(String date, int type, double price){
        ParseObject objectDelivery = new ParseObject("CashBook");
        objectDelivery.put("date", date);
        objectDelivery.put("type", type);
        objectDelivery.put("value", price);
        objectDelivery.pinInBackground();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentActivity.setTitle(R.string.title_financial);
    }

    @OnClick(R.id.btn_add_balance)
    public void onViewClicked() {
        Intent intent = new Intent(currentActivity, AddBalanceActivity.class);
        startActivity(intent);
    }

    private void setupRecycler(List<ParseObject> mList, Context mContext) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new FinancialAdapter(mList, mContext);
        recyclerView.setAdapter(mAdapter);
    }


}
