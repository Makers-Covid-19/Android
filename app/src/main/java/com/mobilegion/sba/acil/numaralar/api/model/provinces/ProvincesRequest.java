/*
 *  * Created by Serhat Bekir AK on 26.03.2020 00:12
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 26.03.2020 00:12
 */

package com.mobilegion.sba.acil.numaralar.api.model.provinces;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.mobilegion.sba.acil.numaralar.api.core.APIConstants;
import com.mobilegion.sba.acil.numaralar.api.core.MasterAPI;
import com.mobilegion.sba.acil.numaralar.api.core.ResponseError;
import com.mobilegion.sba.acil.numaralar.api.core.ResponseInterface;
import com.mobilegion.sba.acil.numaralar.helpers.core.MyFragmentManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ProvincesRequest extends MasterAPI {
    ResponseInterface responseInterface;
    MyFragmentManager myFragmentManager;
    private String url = APIConstants.PROVINCES;

    public ProvincesRequest(ResponseInterface responseInterface, MyFragmentManager myFragmentManager) {
        this.responseInterface = responseInterface;
        this.myFragmentManager = myFragmentManager;
    }

    public void request() {
        myFragmentManager.showProgress();
        HashMap<String, String> params = new HashMap<>();
        scRestManager.get(url, params, url, newsRequest);
    }

    StringRequestListener newsRequest = new StringRequestListener() {
        @Override
        public void onResponse(String response) {
            myFragmentManager.hideProgress();
            ResponseError error = new Gson().fromJson(response, ResponseError.class);
            if(error.isSuccess()) {
                try {
                    String json = new JSONObject(response).getString("data");
                    ProvinceModel[] provinceModel = new Gson().fromJson(json, ProvinceModel[].class);
                    ArrayList<ProvinceModel> model = new ArrayList<>(Arrays.asList(provinceModel));
                    tinyDB.putListCity(model);
                    responseInterface.success(model);
                } catch (Exception e) {
                    responseInterface.error("FAIL");
                }
            }
            else {
                responseInterface.error("FAIL");
            }
        }

        @Override
        public void onError(ANError ANError) {
            myFragmentManager.hideProgress();
            responseInterface.error(ANError.getErrorDetail());
            checkResponse(ANError, responseInterface);
        }
    };
}
