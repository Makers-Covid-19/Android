/*
 *  * Created by Serhat Bekir AK on 28.03.2020 01:52
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 28.03.2020 01:52
 */

package com.sba.covid.acil.helpers.utilities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sba.covid.acil.R;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Utilities {

    public static void loadImage(Context context, String url, ImageView holderImage) {
        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher))
                .load(url)
                .into(holderImage);
    }

    public static void callPhone(Context context, String phone) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1377);
        } else {
            try {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getDrawableByName(Context context, int type) {
        switch (type) {
            case 0:
                return context.getResources().getIdentifier("ic_all_needs", "drawable", context.getPackageName());
            case 1:
                return context.getResources().getIdentifier("ic_manav", "drawable", context.getPackageName());
            case 2:
                return context.getResources().getIdentifier("ic_faturalar", "drawable", context.getPackageName());
            case 3:
                return context.getResources().getIdentifier("ic_alisveris", "drawable", context.getPackageName());
            case 4:
                return context.getResources().getIdentifier("ic_koordinasyon", "drawable", context.getPackageName());
            case 5:
                return context.getResources().getIdentifier("ic_psikolojikdestek", "drawable", context.getPackageName());
            default:
                return context.getResources().getIdentifier("ic_launcher", "mipmap", context.getPackageName());
        }

    }

    /*SocialMedia Show*/

    public static void showTwitter(Activity activity, String userId) {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + userId)));
        } catch (Exception e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + userId)));
        }
    }

    public static void showInstagram(Activity activity, String userId) {
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/" + userId + "/")));
    }

    public static void showFacebook(Activity activity, String userId, String appLink) {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appLink)));
        } catch (Exception e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + userId + "/")));
        }
    }

    public static void showYoutube(Activity activity, String id) {
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/" + id)));

    }

    public static void showWhatsApp(Activity activity) {
        PackageManager pm = activity.getPackageManager();
        try {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = activity.getResources().getString(R.string.whatsapp_hi);
            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            waIntent.setPackage("com.whatsapp");
            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            activity.startActivity(Intent.createChooser(waIntent, "Share"));
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(activity, activity.getResources().getString(R.string.whatsapp_info), Toast.LENGTH_SHORT).show();
        }

    }
}
