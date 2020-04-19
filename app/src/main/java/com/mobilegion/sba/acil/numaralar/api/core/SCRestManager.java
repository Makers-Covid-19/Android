/*
 * Copyright (c) Serhan CANOVA - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Created and written by Serhan CANOVA <serhancanova@gmail.com>, 6.3.2020
 */

package com.mobilegion.sba.acil.numaralar.api.core;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

public class SCRestManager {

    private static SCRestManager instance;

    public static synchronized SCRestManager getInstance() {
        if (instance == null)
            instance = new SCRestManager();
        return instance;
    }

    public void get(String url, HashMap<String, String> params, String TAG, StringRequestListener srl) {

        AndroidNetworking.get(url)
                .addQueryParameter(params)
                .addHeaders(requestHeader(TAG))
                .setTag(TAG)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsString(srl);
    }

    public void post(String url, HashMap<String, Object> params, String TAG, StringRequestListener srl) {

        JSONObject json = JsonUtils.mapToJson(params);
        HashMap<String, String> headers = requestHeader(TAG);

        AndroidNetworking.post(url)
                .addJSONObjectBody(json)
                .addHeaders(headers)
                .setTag(TAG)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsString(srl);
    }

    public void put(String url, HashMap<String, Object> params, String TAG, StringRequestListener srl) {

        JSONObject json = JsonUtils.mapToJson(params);
        HashMap<String, String> headers = requestHeader(TAG);

        AndroidNetworking.put(url)
                .addJSONObjectBody(json)
                .addHeaders(headers)
                .setTag(TAG)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsString(srl);
    }

    public void post(String url, File file, StringRequestListener srl) {

        HashMap<String, String> headers = requestHeader("");

        AndroidNetworking.upload(url)
                .addMultipartFile("file", file)
                .setTag("POST")
                .addHeaders(headers)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsString(srl);
    }

    public void registerMulti(String url, File file, HashMap<String, Object> params, StringRequestListener srl) {

        JSONObject json = JsonUtils.mapToJson(params);
        HashMap<String, String> headers = requestHeader("");

        AndroidNetworking.upload(url)
                .addMultipartFile("avatar", file)
                .addMultipartParameter("json", json.toString())
                .setTag("registerMulti")
                .addHeaders(headers)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsString(srl);
    }

    public void photo(String url, File file, StringRequestListener srl) {

        HashMap<String, String> headers = requestHeader("");

        AndroidNetworking.upload(url)
                .addMultipartFile("photo", file)
                .setTag("registerMulti")
                .addHeaders(headers)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsString(srl);
    }

    private HashMap<String, String> requestHeader(String tag) {
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "application/json");
        return header;
    }

}