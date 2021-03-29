package com.example.shark.view.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shark.R;
import com.example.shark.view.ui.activiteis.SettingsInfosActivity;
import com.example.shark.view.ui.activiteis.SettingsPasswordActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsFragment extends Fragment {

    @BindView(R.id.change_infos)
    LinearLayout changeInfos;
    @BindView(R.id.change_password)
    LinearLayout changePassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(R.string.menu_settings);
    }

    @OnClick({R.id.change_infos, R.id.change_password})
    public void onViewClicked(View view) {
        Class classIntent = null;
        switch (view.getId()) {

            case R.id.change_infos:
                classIntent = SettingsInfosActivity.class;
                break;
            case R.id.change_password:
                classIntent = SettingsPasswordActivity.class;
                break;
        }
        Intent intent = new Intent(getActivity(), classIntent);

        startActivity(intent);
    }
}