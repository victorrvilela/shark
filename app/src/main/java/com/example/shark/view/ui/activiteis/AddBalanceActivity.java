package com.example.shark.view.ui.activiteis;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shark.R;
import com.example.shark.services.Utils;
import com.example.shark.view.adapter.AddBalanceAdapter;
import com.example.shark.view.adapter.RecyclerItemClickListener;
import com.example.shark.view.ui.BaseActivity;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddBalanceActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_loading)
    FrameLayout llLoading;

    Activity currentActivity;
    private AddBalanceAdapter mAdapter;
    private Boolean fromPayments;


    public AddBalanceActivity() {
        currentActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setupUI(this, findViewById(R.id.add_balance));
        ButterKnife.bind(this);
        this.setTitle(getResources().getString(R.string.title_add_balance));
        parseQuery();
        currentActivity = this;
    }

    private void parseQuery() {
        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Payments");
        parseQuery.fromLocalDatastore();
        parseQuery.findInBackground((objects, e) -> {
            if (e == null) {
                setupRecycler(objects);
                llLoading.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_balance;
    }


    private void setupRecycler(List<ParseObject> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new AddBalanceAdapter(list);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AlertDialog builder = new AlertDialog.Builder(currentActivity)
                        .setMessage(String.format("%s%s%s", "Você está adicionando ", Utils.formatCurrency(list.get(position).getDouble("value")), " reais em saldo, será enviado um boleto em seu e-mail para o pagamento"))
                        .setPositiveButton(getResources().getString(R.string.action_confirm), (dialog, id) -> {
                            AlertDialog.Builder alert = new AlertDialog.Builder(currentActivity);
                            alert.setMessage(String.format("%s \"%s\"", "Foi enviado um boleto para seu e-mail", "shark.tester@shark.com.br"))
                                    .setPositiveButton("Ok", (dialog2, id2) -> {
                                        addBalance(new Date(), list.get(position).getDouble("value"),3 );
                                    }).show();
                        })
                        .setNegativeButton(getResources().getString(R.string.action_cancel), (dialog, id) -> {

                        }).show();
                builder.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(currentActivity.getResources().getColor(R.color.colorTextLight));
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }

        ));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }

    private void addBalance(Date date, double value, int type) {
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        String todayAsString = df.format(date);
        ParseObject objectDelivery = new ParseObject("CashBook");
        objectDelivery.put("date", todayAsString);
        objectDelivery.put("type", type);
        objectDelivery.put("value", value);
        objectDelivery.pinInBackground(e -> {
            if( e == null ) {
                finish();
            }
        });

    }
}