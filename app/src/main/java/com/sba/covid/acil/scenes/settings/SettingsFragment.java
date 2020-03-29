/*
 *  * Created by Serhat Bekir AK on 26.03.2020 23:58
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 26.03.2020 23:29
 */

package com.sba.covid.acil.scenes.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sba.covid.acil.R;
import com.sba.covid.acil.helpers.core.BaseFragment;
import com.sba.covid.acil.helpers.utilities.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsFragment extends BaseFragment {

    @BindView(R.id.muni_facebook) public ImageView muni_facebook;
    @BindView(R.id.facebook) public ImageView facebook;
    @BindView(R.id.twitter) public ImageView twitter;
    @BindView(R.id.instagram) public ImageView instagram;
    @BindView(R.id.whatsapp) public ImageView whatsapp;
    @BindView(R.id.muni_whatsapp) public ImageView muni_whatsapp;
    @BindView(R.id.backButton) public ImageView backButton;
    @BindView(R.id.share) public RelativeLayout shareButton;

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.settings_fragment, container, false);
        ButterKnife.bind(this, view);
        backButton.setOnClickListener((View v) -> popFragment());

        muni_whatsapp.setOnClickListener((View v) -> Utilities.showWhatsApp(mActivity,"905326682864"));
        muni_facebook.setOnClickListener((View v) -> Utilities.showFacebook(mActivity, "AyvalikBelediyesi", ""));

        facebook.setOnClickListener((View v) -> Utilities.showFacebook(mActivity, "ayvalikyerelinisiyatif", ""));
        twitter.setOnClickListener((View v) -> Utilities.showTwitter(mActivity, "ayvalikyerel"));
        instagram.setOnClickListener((View v) -> Utilities.showInstagram(mActivity, "ayvalikyerel"));
        whatsapp.setOnClickListener((View v) -> Utilities.showWhatsApp(mActivity,"905326682864"));
        shareButton.setOnClickListener((View v) -> Utilities.share(mActivity,getString(R.string.app_share)));


        return view;
    }

}
