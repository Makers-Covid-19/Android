/*
 *  * Created by Serhat Bekir AK on 25.03.2020 23:18
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 25.03.2020 23:18
 */

package com.sba.covid.acil;

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
