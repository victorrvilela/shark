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
        parseQuery();
        return view;
    }

    private void parseQuery() {
        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("CashBook");
        parseQuery.fromLocalDatastore();
        parseQuery.addDescendingOrder("date");
        parseQuery.findInBackground((objects, e) -> {
            if (e == null) {
                int balance = 0;
                for (int i = 0; i < objects.size(); i++) {
                    balance += objects.get(i).getDouble("value");
                }
                accountBalance.setText(String.format("%s", Utils.formatCurrency(balance)));
                setupRecycler(objects, currentActivity);
                llLoading.setVisibility(View.GONE);

            }
        });
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

    @Override
    public void onResume() {
        super.onResume();
        parseQuery();
    }
}
