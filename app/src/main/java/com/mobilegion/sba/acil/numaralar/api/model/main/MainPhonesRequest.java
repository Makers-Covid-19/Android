/*
 *  * Created by Serhat Bekir AK on 27.03.2020 23:51
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27.03.2020 23:50
 */

package com.mobilegion.sba.acil.numaralar.api.model.main;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.mobilegion.sba.acil.numaralar.api.core.APIConstants;
import com.mobilegion.sba.acil.numaralar.api.core.MasterAPI;
import com.mobilegion.sba.acil.numaralar.api.core.ResponseError;
import com.mobilegion.sba.acil.numaralar.api.core.ResponseInterface;
import com.mobilegion.sba.acil.numaralar.helpers.Constants;
import com.mobilegion.sba.acil.numaralar.helpers.core.MyFragmentManager;

import org.json.JSONObject;

import java.util.HashMap;

public class MainPhonesRequest extends MasterAPI {
    ResponseInterface responseInterface;
    MyFragmentManager myFragmentManager;
    private String url = APIConstants.PHONES;
    private String id = "";
    private HashMap<String, String> params;
    private Boolean unrequired;

    public MainPhonesRequest(ResponseInterface responseInterface, MyFragmentManager myFragmentManager) {
        this.responseInterface = responseInterface;
        this.myFragmentManager = myFragmentManager;
        params = new HashMap<>();
    }

    public void request(int id) {
        unrequired = false;
        if (checkRequest(tinyDB.getLong(Constants.PHONE_REQUEST_LAST_TIME + id, 0)) && myFragmentManager.isOnline() && id != 0) {
            this.id = id + "";
            myFragmentManager.showProgress();
            params = new HashMap<>();
            if (id > 100) { //todo unreasonable solution
                params.put("district_id", id + "");
            } else {
                params.put("province_id", id + "");
            }
            scRestManager.get(url, params, url, newsRequest);
        } else {
            myFragmentManager.hideProgress();
            responseInterface.success(null);
        }
    }

    public void request(long phoneId) {
        unrequired = true;
        myFragmentManager.showProgress();
        scRestManager.put(APIConstants.UNREQUIRED_PHONE + "/" + phoneId, new HashMap<>(), APIConstants.UNREQUIRED_PHONE, newsRequest);
    }

    StringRequestListener newsRequest = new StringRequestListener() {
        @Override
        public void onResponse(String response) {
            myFragmentManager.hideProgress();
            ResponseError error = new Gson().fromJson(response, ResponseError.class);
            if (error.isSuccess()) {
                if (unrequired) {
                    responseInterface.success("SUCCESS");
                    return;
                }
                try {
                    String json = new JSONObject(response).getString("data");
                    MainPhones model = new Gson().fromJson(json, MainPhones.class);
                    tinyDB.putListHomePhone(model, id);
                    tinyDB.putLong(Constants.PHONE_REQUEST_LAST_TIME, error.getTimesTamp());
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
            responseInterface.error("FAIL");
            checkResponse(ANError, responseInterface);
        }
    };

}
