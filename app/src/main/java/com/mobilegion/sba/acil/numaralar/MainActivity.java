/*
 *  * Created by Serhat Bekir AK on 08.04.2020 22:00
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 08.04.2020 22:00
 */

package com.mobilegion.sba.acil.numaralar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.mobilegion.sba.acil.numaralar.api.model.main.MainPhones;
import com.mobilegion.sba.acil.numaralar.helpers.Constants;
import com.mobilegion.sba.acil.numaralar.helpers.core.BaseActivity;
import com.mobilegion.sba.acil.numaralar.helpers.core.MyFragmentManager;
import com.mobilegion.sba.acil.numaralar.scenes.phone.HomeFragment;

public class MainActivity extends BaseActivity implements MyFragmentManager, FragmentManager.OnBackStackChangedListener {

    //@BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigation;
    @BindView(R.id.progress) ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorSplash, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorSplash));
        }

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        Constants.FIREBASEID = task.getResult().getToken();
                    }
                });
        if (tinydb.getString(Constants.SELECT_DISTRICT).equals("")) { //Default Data
            String json = "{\"majorPhones\":[],\"publicPhones\":[],\"globalPhones\":[{\"id\":28,\"name\":\"Jandarma\",\"category\":{\"id\":4,\"name\":\"Koordinasyon\"},\"neighborhood\":null,\"province\":{\"id\":0,\"name\":\"Ülke Geneli\"},\"district\":null,\"phone\":\"156\"},{\"id\":29,\"name\":\"Corona Danışma Hattı\",\"category\":{\"id\":4,\"name\":\"Koordinasyon\"},\"neighborhood\":null,\"province\":{\"id\":0,\"name\":\"Ülke Geneli\"},\"district\":null,\"phone\":\"184\"},{\"id\":26,\"name\":\"Ambulans\",\"category\":{\"id\":4,\"name\":\"Koordinasyon\"},\"neighborhood\":null,\"province\":{\"id\":0,\"name\":\"Ülke Geneli\"},\"district\":null,\"phone\":\"112\"},{\"id\":27,\"name\":\"Polis\",\"category\":{\"id\":4,\"name\":\"Koordinasyon\"},\"neighborhood\":null,\"province\":{\"id\":0,\"name\":\"Ülke Geneli\"},\"district\":null,\"phone\":\"155\"}]}";
            MainPhones model = new Gson().fromJson(json, MainPhones.class);
            tinydb.putString(Constants.SELECT_CITY, "-1");
            tinydb.putString(Constants.SELECT_DISTRICT, "-1");
            tinydb.putListHomePhone(model, "-1");
        }



        replaceFragment(new HomeFragment());
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
        if (!(fragment instanceof HomeFragment))
            super.onBackPressed();
        else
            this.finish();
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
