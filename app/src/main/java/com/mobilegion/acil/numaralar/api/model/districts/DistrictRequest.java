/*
 *  * Created by Serhat Bekir AK on 29.03.2020 03:06
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 29.03.2020 02:58
 */

package com.mobilegion.acil.numaralar.api.model.districts;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.mobilegion.acil.numaralar.api.core.APIConstants;
import com.mobilegion.acil.numaralar.api.core.MasterAPI;
import com.mobilegion.acil.numaralar.api.core.ResponseError;
import com.mobilegion.acil.numaralar.api.core.ResponseInterface;
import com.mobilegion.acil.numaralar.helpers.core.MyFragmentManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DistrictRequest extends MasterAPI {
    ResponseInterface responseInterface;
    MyFragmentManager myFragmentManager;
    private String url = APIConstants.DISTRICTS;
    private int id;

    public DistrictRequest(ResponseInterface responseInterface, MyFragmentManager myFragmentManager) {
        this.responseInterface = responseInterface;
        this.myFragmentManager = myFragmentManager;
    }

    public void request(int id) {
        this.id = id;
        myFragmentManager.showProgress();
        HashMap<String, String> params = new HashMap<>();
        scRestManager.get(url + "/" + id, params, url + "/" + id, newsRequest);
    }

    StringRequestListener newsRequest = new StringRequestListener() {
        @Override
        public void onResponse(String response) {
            myFragmentManager.hideProgress();
            ResponseError error = new Gson().fromJson(response, ResponseError.class);
            if (error.isSuccess()) {
                try {
                    String json = new JSONObject(response).getString("data");
                    DistrictModel[] DistrictModel = new Gson().fromJson(json, DistrictModel[].class);
                    ArrayList<DistrictModel> model = new ArrayList<>(Arrays.asList(DistrictModel));
                    tinyDB.putListDistrict(model, id);
                    responseInterface.success(model);
                } catch (Exception e) {
                    responseInterface.error("FAIL");
                }
            } else {
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
