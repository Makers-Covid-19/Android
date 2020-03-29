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
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.sba.covid.acil.MainActivity;
import com.sba.covid.acil.R;

public class DefaultDialog {

    public static void showNetworkDialog(Context context, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.network_close));
        builder.setMessage(content);
        builder.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        builder.show();
    }

    public static void showNetworkDialog(Context context) {
        showNetworkDialog(context, context.getResources().getString(R.string.network_message));
    }


    public static void permissionsCallPhoneDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.permission_title));
        builder.setMessage(context.getResources().getString(R.string.permission_info));
        builder.setPositiveButton(context.getResources().getString(R.string.show_permission), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
