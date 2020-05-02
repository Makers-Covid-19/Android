/*
 *  * Created by Serhat Bekir AK on 26.03.2020 00:49
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 26.03.2020 00:49
 */

package com.mobilegion.acil.numaralar.helpers.core;

import android.os.Bundle;

import com.mobilegion.acil.numaralar.helpers.db.tinydb.TinyDB;
import com.mobilegion.acil.numaralar.helpers.utilities.Utilities;

import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public TinyDB tinydb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TinyDB.dbContext = getApplicationContext();
        tinydb = TinyDB.getInstance();
        if(tinydb.getString("language").equals(""))
            tinydb.putString("language",Locale.getDefault().getLanguage());
        Utilities.updateLanguage(this, tinydb.getString("language"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
}
