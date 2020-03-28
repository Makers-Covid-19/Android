/*
 *  * Created by Serhat Bekir AK on 26.03.2020 00:49
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 26.03.2020 00:49
 */

package com.sba.covid.acil.helpers.core;

import android.os.Bundle;

import com.sba.covid.acil.helpers.db.tinydb.TinyDB;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public TinyDB tinydb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TinyDB.dbContext = getApplicationContext();
        tinydb = TinyDB.getInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
}
