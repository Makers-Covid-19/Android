/*
 *  * Created by Serhat Bekir AK on 26.03.2020 00:12
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 26.03.2020 00:12
 */

package com.sba.covid.acil.api.model.provinces;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.sba.covid.acil.api.core.APIConstants;
import com.sba.covid.acil.api.core.MasterAPI;
import com.sba.covid.acil.api.core.ResponseError;
import com.sba.covid.acil.api.core.ResponseInterface;
import com.sba.covid.acil.helpers.core.MyFragmentManager;

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
                    ProvinceModel[] provinceModel = new Gson().fromJson(response, ProvinceModel[].class);
                    ArrayList<ProvinceModel> model = new ArrayList<>(Arrays.asList(provinceModel));
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
            responseInterface.error("FAIL");
            checkResponse(ANError, responseInterface);
        }
    };
}
