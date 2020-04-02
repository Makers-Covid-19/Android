/*
 *  * Created by Serhat Bekir AK on 28.03.2020 01:52
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 28.03.2020 01:52
 */

package com.sba.covid.acil.helpers.utilities;

import android.Manifest;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.sba.covid.acil.R;
import com.sba.covid.acil.api.model.districts.DistrictModel;
import com.sba.covid.acil.api.model.provinces.ProvinceModel;
import com.sba.covid.acil.helpers.db.tinydb.TinyDB;
import com.sba.covid.acil.helpers.dialog.DefaultDialog;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import es.dmoral.toasty.Toasty;

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
        Dexter.withActivity((Activity) context)
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                        context.startActivity(intent);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            DefaultDialog.permissionsCallPhoneDialog(context);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
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

    public static String[] ProvinceArrayToStringArray(ArrayList<ProvinceModel> list) { //todo minifyEnabled crash
        ArrayList<String> stringArray = new ArrayList<>();
        for (ProvinceModel val : list) {
            if (val.getId() != 0) { //response model bug!
                stringArray.add(val.getName());
            }
        }
        return stringArray.toArray(new String[0]);
    }

    public static String[] DistrictArrayToStringArray(ArrayList<DistrictModel> list) {
        ArrayList<String> stringArray = new ArrayList<>();
        for (DistrictModel val : list) {
            stringArray.add(val.getName());
        }
        return stringArray.toArray(new String[0]);
    }

    public static Boolean clipboardCopy(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
        Toasty.info(context, context.getString(R.string.clipboard_info)).show();
        return true;
    }

    public static void changeLanguage(Activity mActivity, String lang) {
        if (!new TinyDB(mActivity).getString("language").equals(lang)) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            mActivity.getBaseContext().getResources().updateConfiguration(config, mActivity.getBaseContext().getResources().getDisplayMetrics());
            new TinyDB(mActivity).putString("language", lang);
            restartActivity(mActivity);
        }
    }

    public static void updateLanguage(Context ctx, String lang) {
        Configuration cfg = new Configuration();
        if (!TextUtils.isEmpty(lang))
            cfg.locale = new Locale(lang);
        else
            cfg.locale = Locale.getDefault();
        ctx.getResources().updateConfiguration(cfg, null);
    }

    public static void restartActivity(Activity mActivity) {
        Intent intent = mActivity.getIntent();
        mActivity.finish();
        mActivity.startActivity(intent);
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

    public static void showBrowse(Activity activity, String url) {
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    public static void showWhatsApp(Activity activity, String number) {
        PackageManager packageManager = activity.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + URLEncoder.encode(activity.getString(R.string.whatsapp_hi), "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                activity.startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Share*/

    public static void share(Activity activity, String content) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        activity.startActivity(shareIntent);
    }

    public static void shareWhatsApp(Activity activity) {
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
            Toasty.info(activity.getApplicationContext(), activity.getString(R.string.whatsapp_info)).show();
        }
    }
}
