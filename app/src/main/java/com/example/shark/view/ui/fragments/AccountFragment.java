package com.example.shark.view.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.shark.R;
import com.example.shark.services.Mask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountFragment extends BaseFragment {


    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.displayName)
    TextView displayName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.plate)
    TextView plate;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);

        setInformation();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(R.string.title_my_account);
    }

    private void setInformation() {

        displayName.setText(String.format("%s", "Usúario teste"));
        tvPhone.setText(String.format("%s", Mask.addMask("16991817460", "(##) #####-####")));
        tvEmail.setText(String.format("%s", "tester.shark@shark.com.br"));
        address.setText(String.format("%s", "Avenida dos testes, 1913"));
        plate.setText(String.format("%s", Mask.addMask("BJU3455", "###-####")));

    }



}
