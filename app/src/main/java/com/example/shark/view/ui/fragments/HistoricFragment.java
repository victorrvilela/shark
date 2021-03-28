package com.example.shark.view.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shark.R;
import com.example.shark.services.Utils;
import com.example.shark.view.adapter.HistoricAdapter;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoricFragment extends BaseFragment {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.full_content)
    LinearLayout fullContent;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.ll_loading)
    FrameLayout llLoading;
    @BindView(R.id.freight)
    TextView freight;
    @BindView(R.id.deliveries)
    TextView deliveries;


    private Boolean clicked;
    private HistoricAdapter mAdapter;
    private SimpleDateFormat format;
    private int deliveries_number;
    private double deliveries_total_value = 0;
    private List<ParseObject> defaultList;


    public HistoricFragment() {
        clicked = false;
        defaultList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historic, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        parseQuery();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentActivity.setTitle(R.string.title_historic);
        setHasOptionsMenu(true);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mymenu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mybutton) {
            saveHistoric();
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearLocal(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pedagios");
        query.fromLocalDatastore().findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {
                ParseObject.unpinAllInBackground(objects);
            }
        });
    }

    private void saveHistoric(){
        ParseObject objectDelivery = new ParseObject("Pedagios");
        objectDelivery.put("plate", "BTF-3013");
        objectDelivery.put("createdAt", new Date());
        objectDelivery.put("city", "Sertãozinho");
        objectDelivery.put("price", 3);
        objectDelivery.pinInBackground();
    }

    private void setupRecycler(List<ParseObject> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new HistoricAdapter(currentActivity, list);
        recyclerView.setAdapter(mAdapter);
        llLoading.setVisibility(View.GONE);
        fullContent.setVisibility(View.VISIBLE);
    }

    private void parseQuery() {
        deliveries_total_value = 0;
        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Pedagios");
        parseQuery.fromLocalDatastore();
        llLoading.setVisibility(View.VISIBLE);
        parseQuery.findInBackground((objects, e) -> {
            if (e == null) {
                if (objects.size() > 0) {
                    deliveries_number = objects.size();
                    for (int i = 0; i < deliveries_number; i++) {
                        deliveries_total_value = deliveries_total_value + (objects.get(i).getDouble("price"));
                    }
                    deliveries.setText(String.format("%s", deliveries_number));
                    freight.setText(String.format("%s", Utils.formatCurrency(deliveries_total_value)));
                    setupRecycler(objects);
                } else {
                    deliveries.setText(String.format("%s", 0));
                    freight.setText(String.format("%s", Utils.formatCurrency(0)));
                }
            }
        });
    }

}
