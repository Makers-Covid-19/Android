/*
 *  * Created by Serhat Bekir AK on 08.04.2020 22:00
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 08.04.2020 22:00
 */

package com.mobilegion.sba.acil.numaralar;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
