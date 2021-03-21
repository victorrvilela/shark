//package com.example.shark.view.ui.fragments;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.annotation.Nullable;
//import androidx.fragment.app.DialogFragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import com.example.shark.R;
//import com.example.shark.services.Utils;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//
//
//public class HistoricFragment extends BaseFragment {
//
//
//    @BindView(R.id.recycler_view)
//    RecyclerView recyclerView;
//    @BindView(R.id.full_content)
//    LinearLayout fullContent;
//    @BindView(R.id.loading)
//    ProgressBar loading;
//    @BindView(R.id.ll_loading)
//    FrameLayout llLoading;
//    @BindView(R.id.freight)
//    TextView freight;
//    @BindView(R.id.deliveries)
//    TextView deliveries;
//
//
//    private Boolean clicked;
//    private HistoricsAdapter mAdapter;
//    private SimpleDateFormat format;
//    private int deliveries_number;
//    private double deliveries_total_value = 0;
//    private boolean first_query = false;
//
//
//    public HistoricFragment() {
//        clicked = false;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_historic, container, false);
//        ButterKnife.bind(this, view);
//        setHasOptionsMenu(false);
//
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        //you can set the title for your toolbar here for different fragments different titles
//        currentActivity.setTitle(R.string.title_historic);
//        setHasOptionsMenu(false);
//
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.mymenu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.mybutton) {
//            showPopup();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    public void showPopup() {
//        if (first_query) {
//            DialogFragment newFragment = FilterDetailsFragment.newInstance();
//            newFragment.setTargetFragment(this, DIALOG_FRAGMENT);
//            newFragment.show(getFragmentManager(), "dialog");
//        }
//    }
//
//
//    private void setupRecycler(List<ParseObject> list) {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        mAdapter = new HistoricsAdapter(currentActivity, defaultList);
//        recyclerView.setAdapter(mAdapter);
//        recyclerView.addOnItemTouchListener(
//                new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        DialogFragment newFragment = DeliveriesDetailsFragment.newInstance(defaultList.get(position), false);
//                        newFragment.setCancelable(true);
//                        newFragment.show(getFragmentManager(), "dialog");
//                    }
//
//                    @Override
//                    public void onLongItemClick(View view, int position) {
//                    }
//                })
//        );
//        llLoading.setVisibility(View.GONE);
//        fullContent.setVisibility(View.VISIBLE);
//    }
//
//    private void parseQuery(String startDate, String finishDate) {
//
//        Date fDay = new Date();
//        Date lDay = new Date();
//        try {
//            fDay = format.parse(startDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        try {
//            lDay = format.parse(finishDate);
//            lDay.setTime(lDay.getTime() + 86399000); // last second of the day
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        deliveries_total_value = 0;
//        deliveries_number = objectstList.size();
//        for (int i = 0; i < deliveries_number; i++) {
//            deliveries_total_value = deliveries_total_value + (objectstList.get(i).getDouble("final_price"));
//        }
//        setupRecycler(objectstList);
//        setHasOptionsMenu(true);
//        deliveries.setText(String.format("%s", deliveries_number));
//        freight.setText(String.format("%s", Utils.formatCurrency(deliveries_total_value)));
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case DIALOG_FRAGMENT:
//                if (resultCode == Activity.RESULT_OK) {
//                    deliveries.setText(String.format("%s", 0));
//                    freight.setText(String.format("%s", Utils.formatCurrency(0)));
//                    fullContent.setVisibility(View.GONE);
//                    llLoading.setVisibility(View.VISIBLE);
//                    Bundle bundle = data.getExtras();
//                    String inicial = bundle.getString("start");
//                    String fim = bundle.getString("end");
//                    parseQuery(inicial, fim);
//                } else if (resultCode == Activity.RESULT_CANCELED) {
//                }
//                break;
//        }
//    }
//}