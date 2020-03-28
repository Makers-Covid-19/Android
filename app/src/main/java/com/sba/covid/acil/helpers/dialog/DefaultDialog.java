/*
 *  * Created by Serhat Bekir AK on 28.03.2020 02:06
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 28.03.2020 02:06
 */

package com.sba.covid.acil.helpers.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.sba.covid.acil.MainActivity;
import com.sba.covid.acil.R;

public class DefaultDialog {
    public static void showNetworkDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.network_close));
        builder.setMessage(context.getResources().getString(R.string.network_message));
        builder.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        builder.show();
    }
}
