/*
 *  * Created by Serhat Bekir AK on 25.03.2020 23:13
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 25.03.2020 23:05
 */

package com.sba.covid.acil;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sba.covid.acil.helpers.Constants;
import com.sba.covid.acil.helpers.core.BaseActivity;
import com.sba.covid.acil.helpers.core.MyFragmentManager;
import com.sba.covid.acil.helpers.dialog.DefaultDialog;
import com.sba.covid.acil.scenes.contact.ContactFragment;
import com.sba.covid.acil.scenes.phone.HomeFragment;
import com.sba.covid.acil.scenes.settings.SettingsFragment;

public class MainActivity extends BaseActivity implements MyFragmentManager, FragmentManager.OnBackStackChangedListener {

    //@BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigation;
    @BindView(R.id.progress) ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        if (!tinydb.getString(Constants.DISTRICT).equals("")) {
            replaceFragment(new HomeFragment());
        } else {
            if (!isOnline())
                DefaultDialog.showNetworkDialog(this);
            else
                replaceFragment(new HomeFragment());
        }
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public Fragment getFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.frameLayoutMain);
    }

    @Override
    public void addFragment(Fragment f) {
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayoutMain, f).addToBackStack(f.getClass().toString()).commit();
    }

    @Override
    public void replaceFragment(Fragment f) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, f).addToBackStack(f.getClass().toString()).commit();
    }

    @Override
    public void removeAllFragment() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    @Override
    public void closeKeyboard() {
        try {
            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

    @Override
    public void onBackStackChanged() {
        closeKeyboard();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frameLayoutMain);
        if (!(fragment instanceof HomeFragment) && !(fragment instanceof ContactFragment))//&& !(fragment instanceof SettingsFragment)
            super.onBackPressed();
    }

//    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    switch (item.getItemId()) {
//                        case R.id.navigation_phone:
//                            replaceFragment(new HomeFragment());
//                            return true;
//                        case R.id.navigation_contact:
//                            replaceFragment(new ContactFragment());
//                            return true;
//                        case R.id.navigation_setting:
//                            replaceFragment(new SettingsFragment());
//                            return true;
//                    }
//                    return false;
//                }
//            };

}
