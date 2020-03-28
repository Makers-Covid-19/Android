/*
 *  * Created by Serhat Bekir AK on 26.03.2020 18:48
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 26.03.2020 01:03
 */

package com.sba.covid.acil.scenes.phone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.sba.covid.acil.R;
import com.sba.covid.acil.api.core.ResponseInterface;
import com.sba.covid.acil.api.model.main.MainPhones;
import com.sba.covid.acil.api.model.main.MainPhonesRequest;
import com.sba.covid.acil.api.model.provinces.ProvinceModel;
import com.sba.covid.acil.helpers.core.BaseFragment;
import com.sba.covid.acil.scenes.settings.SettingsFragment;

import java.util.ArrayList;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements ResponseInterface {

    @BindView(R.id.rv_major_phones) public RecyclerView rvMajorPhones;
    @BindView(R.id.rv_public_phones) public RecyclerView rvPhones;
    @BindView(R.id.rv_global_phones) public RecyclerView rvGlobalPhones;
    @BindView(R.id.setting_button) public ImageView setting;

    private View view;
    private MainPhonesRequest request;
    private MajorPhonesAdapter adapterMajorPhones;
    private PublicPhonesAdapter adapterPublicPhones;
    private GlobalPhonesAdapter adapterGlobalPhones;
    private String district_id = "1161";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.phone_fragment, container, false);
        ButterKnife.bind(this, view);
        setting.setOnClickListener((View v) -> mFragmentManager.addFragment(new SettingsFragment()));
        setAdapter();
        request = new MainPhonesRequest(HomeFragment.this, mFragmentManager);
        request.request(1161);
        return view;
    }

    private void setAdapter() {
        if (mActivity.tinydb.getListHomePhone(district_id) != null) {
            setAdapterMajorPhones();
            setAdapterPublicPhones();
            setAdapterGlobalPhones();
        }
    }

    public void setAdapterMajorPhones() {
        if (mActivity.tinydb.getListHomePhone(district_id).getMajorPhones() != null) {
            adapterMajorPhones = new MajorPhonesAdapter(view.getContext(), mFragmentManager, mActivity.tinydb.getListHomePhone(district_id).getMajorPhones());
            rvMajorPhones.setItemAnimator(new DefaultItemAnimator());
            rvMajorPhones.setAdapter(adapterMajorPhones);
        }
    }

    public void setAdapterPublicPhones() {
        if (mActivity.tinydb.getListHomePhone(district_id).getPublicPhones() != null) {
            adapterPublicPhones = new PublicPhonesAdapter(view.getContext(), mFragmentManager, mActivity.tinydb.getListHomePhone(district_id).getPublicPhones());
            rvPhones.setItemAnimator(new DefaultItemAnimator());
            rvPhones.setAdapter(adapterPublicPhones);
        }
    }

    public void setAdapterGlobalPhones() {
        if (mActivity.tinydb.getListHomePhone(district_id).getGlobalPhones() != null) {
            adapterGlobalPhones = new GlobalPhonesAdapter(view.getContext(), mFragmentManager, mActivity.tinydb.getListHomePhone(district_id).getGlobalPhones());
            rvGlobalPhones.setItemAnimator(new DefaultItemAnimator());
            rvGlobalPhones.setAdapter(adapterGlobalPhones);
        }
    }

    @Override
    public void success(Object obj) {
        if (obj != null && obj instanceof MainPhones) {
            setAdapter();
        }
    }

    @Override
    public void error(Object obj) {

    }
}
