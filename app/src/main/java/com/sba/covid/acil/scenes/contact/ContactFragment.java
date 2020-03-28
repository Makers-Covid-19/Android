/*
 *  * Created by Serhat Bekir AK on 26.03.2020 23:58
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 26.03.2020 23:29
 */

package com.sba.covid.acil.scenes.contact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sba.covid.acil.R;
import com.sba.covid.acil.helpers.core.BaseFragment;

import butterknife.ButterKnife;

public class ContactFragment extends BaseFragment {

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contact_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
