/*
 *  * Created by Serhat Bekir AK on 26.03.2020 18:48
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 26.03.2020 01:03
 */

package com.sba.covid.acil.scenes.phone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sba.covid.acil.R;
import com.sba.covid.acil.api.core.ResponseInterface;
import com.sba.covid.acil.api.model.districts.DistrictModel;
import com.sba.covid.acil.api.model.districts.DistrictRequest;
import com.sba.covid.acil.api.model.main.MainPhones;
import com.sba.covid.acil.api.model.main.MainPhonesRequest;
import com.sba.covid.acil.api.model.provinces.ProvincesRequest;
import com.sba.covid.acil.helpers.Constants;
import com.sba.covid.acil.helpers.core.BaseFragment;
import com.sba.covid.acil.helpers.dialog.DefaultDialog;
import com.sba.covid.acil.helpers.utilities.Utilities;
import com.sba.covid.acil.scenes.settings.SettingsFragment;

import java.util.ArrayList;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class HomeFragment extends BaseFragment implements ResponseInterface {

    @BindView(R.id.rv_major_phones)
    public RecyclerView rvMajorPhones;
    @BindView(R.id.rv_public_phones)
    public RecyclerView rvPhones;
    @BindView(R.id.rv_global_phones)
    public RecyclerView rvGlobalPhones;
    @BindView(R.id.setting_button)
    public ImageView setting;
    @BindView(R.id.select_city)
    public RelativeLayout dialogCity;
    @BindView(R.id.select_district)
    public RelativeLayout dialogDistrict;
    @BindView(R.id.phones)
    public TextView phonesTitle;
    @BindView(R.id.city)
    public TextView city;
    @BindView(R.id.district)
    public TextView district;

    private View view;
    private MainPhonesRequest request;
    private ProvincesRequest requestCity;
    private DistrictRequest requestDistrict;
    private MajorPhonesAdapter adapterMajorPhones;
    private PublicPhonesAdapter adapterPublicPhones;
    private GlobalPhonesAdapter adapterGlobalPhones;
    private String selectDistrictId, selectCityId;
    private int selectDistrictItemId = -1, selectCityItemId = -1, i = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);
        setting.setOnClickListener((View v) -> mFragmentManager.addFragment(new SettingsFragment()));
        dialogCity.setOnClickListener((View v) -> showDialog(false));
        dialogDistrict.setOnClickListener((View v) -> showDialog(true));

        selectCityId = mActivity.tinydb.getString(Constants.SELECT_CITY);
        if (!selectCityId.equals("-1")) {
            city.setText(mActivity.tinydb.getListCity().get(Integer.parseInt(selectCityId)).getName());
            selectCityItemId = Integer.parseInt(selectCityId) - 1;
        }

        selectDistrictId = mActivity.tinydb.getString(Constants.SELECT_DISTRICT);
        i = 0;
        if (!selectDistrictId.equals("-1")) {
            for (DistrictModel val : mActivity.tinydb.getListDistrict(selectCityId)) {
                if ((val.getId() + "").equals(selectDistrictId)) {
                    selectDistrictItemId = i;
                    district.setText(val.getName());
                    break;
                }
                i++;
            }
        }
        setAdapters();
        setCityAndDistrictList();
        return view;
    }

    private void setCityAndDistrictList() {
        if (mActivity.tinydb.getListCity() != null && mActivity.tinydb.getListCity().size() > 0) {
            if (!selectCityId.equals("-1")) {
                if ((mActivity.tinydb.getListDistrict(selectCityId) != null && mActivity.tinydb.getListDistrict(selectCityId).size() > 0)) {
                    if (!selectDistrictId.equals("-1")) {
                        request = new MainPhonesRequest(HomeFragment.this, mFragmentManager);
                        request.request(Integer.valueOf(selectDistrictId));
                    }
                } else {
                    requestDistrict = new DistrictRequest(HomeFragment.this, mFragmentManager);
                    requestDistrict.request(Integer.parseInt(selectCityId));
                }
            }
        } else {
            requestCity = new ProvincesRequest(HomeFragment.this, mFragmentManager);
            requestCity.request();
        }
    }

    private void setAdapters() {
        if (mActivity.tinydb.getListHomePhone(selectDistrictId) != null) {
            setAdapterMajorPhones();
            setAdapterPublicPhones();
            setAdapterGlobalPhones();
        }
    }

    public void setAdapterMajorPhones() {
        if (mActivity.tinydb.getListHomePhone(selectDistrictId).getMajorPhones() != null) {
            adapterMajorPhones = new MajorPhonesAdapter(view.getContext(), mFragmentManager, mActivity.tinydb.getListHomePhone(selectDistrictId).getMajorPhones());
            rvMajorPhones.setItemAnimator(new DefaultItemAnimator());
            rvMajorPhones.setAdapter(adapterMajorPhones);
        }
    }

    public void setAdapterPublicPhones() {
        if (mActivity.tinydb.getListHomePhone(selectDistrictId).getPublicPhones() != null) {
            adapterPublicPhones = new PublicPhonesAdapter(view.getContext(), mFragmentManager, mActivity.tinydb.getListHomePhone(selectDistrictId).getPublicPhones());
            if (adapterPublicPhones.getItemCount() == 0) {
                phonesTitle.setVisibility(View.GONE);
            } else {
                phonesTitle.setVisibility(View.VISIBLE);
            }
            adapterGlobalPhones = new GlobalPhonesAdapter(view.getContext(), mFragmentManager, mActivity.tinydb.getListHomePhone(selectDistrictId).getGlobalPhones());
            rvPhones.setItemAnimator(new DefaultItemAnimator());
            rvPhones.setAdapter(adapterPublicPhones);
        }
    }

    public void setAdapterGlobalPhones() {
        if (mActivity.tinydb.getListHomePhone(selectDistrictId).getGlobalPhones() != null) {
            adapterGlobalPhones = new GlobalPhonesAdapter(view.getContext(), mFragmentManager, mActivity.tinydb.getListHomePhone(selectDistrictId).getGlobalPhones());
            rvGlobalPhones.setItemAnimator(new DefaultItemAnimator());
            rvGlobalPhones.setAdapter(adapterGlobalPhones);
        }
    }

    public void showDialog(boolean distrrictDialog) {
        if (distrrictDialog && selectCityId.equals("-1")) {
            Toasty.info(getContext(),getString(R.string.info_city_context)).show();
            return;
        }
        String[] list;
        int checkedItem;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (!distrrictDialog) {//selectCity
            builder.setTitle(getString(R.string.select_city));
            list = Utilities.ProvinceArrayToStringArray(mActivity.tinydb.getListCity());
            if (list.length == 0) {
                if (!mFragmentManager.isOnline()) {
                    DefaultDialog.showNetworkDialog(getContext(), getString(R.string.network_city_message));
                    return;
                } else {
                    requestCity = new ProvincesRequest(HomeFragment.this, mFragmentManager);
                    requestCity.request();
                    return;
                }
            }
            checkedItem = selectCityItemId;
        } else {
            builder.setTitle(getString(R.string.select_district));
            list = Utilities.DistrictArrayToStringArray(mActivity.tinydb.getListDistrict(selectCityId));
            if (list.length == 0 && !mFragmentManager.isOnline()) {
                DefaultDialog.showNetworkDialog(getContext(), getString(R.string.network_district_message));
                return;
            }
            checkedItem = selectDistrictItemId;
        }
        builder.setSingleChoiceItems(list, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!distrrictDialog) { //selectCity
                    selectCityItemId = which;
                } else {
                    selectDistrictItemId = which;
                }
            }
        });
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!distrrictDialog) { //selectCity
                    selectCityId = mActivity.tinydb.getListCity().get(selectCityItemId + 1).getId() + "";
                    city.setText(mActivity.tinydb.getListCity().get(selectCityItemId + 1).getName());
                    selectDistrictId = "-1";
                    selectDistrictItemId = -1;
                    district.setText(getString(R.string.select_district));
                    requestDistrict = new DistrictRequest(HomeFragment.this, mFragmentManager);
                    requestDistrict.request(Integer.parseInt(selectCityId));
                } else {
                    selectDistrictId = mActivity.tinydb.getListDistrict(selectCityId).get(selectDistrictItemId).getId() + "";
                    district.setText(mActivity.tinydb.getListDistrict(selectCityId).get(selectDistrictItemId).getName());
                    mActivity.tinydb.putString(Constants.SELECT_CITY, (selectCityItemId + 1) + "");
                    mActivity.tinydb.putString(Constants.SELECT_DISTRICT, selectDistrictId);
                    request = new MainPhonesRequest(HomeFragment.this, mFragmentManager);
                    request.request(Integer.valueOf(selectDistrictId));
                }
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void success(Object obj) {
        if (obj != null) {
            if (obj instanceof MainPhones) {
                setAdapters();
            } else if (obj != null && obj instanceof ArrayList<?>) {
                setCityAndDistrictList();
            }
        }
    }

    @Override
    public void error(Object obj) {
//        if (obj.equals("connectionError")) {
//            DefaultDialog.showNetworkDialog(getContext());
//        }
    }


}
