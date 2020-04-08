/*
 *  * Created by Serhat Bekir AK on 26.03.2020 00:15
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 25.03.2020 14:03
 */

package com.mobilegion.sba.acil.numaralar.helpers.core;

import androidx.fragment.app.Fragment;

public interface MyFragmentManager {

    public Fragment getFragment();

    public void addFragment(Fragment f);

    public void replaceFragment(Fragment f);

    public void removeAllFragment();

    public void closeKeyboard();

    public void showProgress();

    public void hideProgress();

    public boolean isOnline();

}
