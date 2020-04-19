/*
 *  * Created by Serhat Bekir AK on 26.03.2020 00:43
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 26.03.2020 00:43
 */

package com.mobilegion.sba.acil.numaralar.helpers.core;

import android.app.Activity;
import android.os.Bundle;

import com.mobilegion.sba.acil.numaralar.api.core.SCRestManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    public BaseActivity mActivity;
    protected SCRestManager SC_Request;
    protected MyFragmentManager mFragmentManager;
    private String a ="";

    @Override
    public void onAttach(@NonNull Activity context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
        mFragmentManager = (MyFragmentManager) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SC_Request = SCRestManager.getInstance();
    }

    protected void popFragment() {
        try {
            mActivity.getSupportFragmentManager().popBackStack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
