/*
 *  * Created by Serhat Bekir AK on 27.03.2020 23:51
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27.03.2020 23:50
 */

package com.sba.covid.acil.api.model.main;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.sba.covid.acil.api.core.APIConstants;
import com.sba.covid.acil.api.core.MasterAPI;
import com.sba.covid.acil.api.core.ResponseError;
import com.sba.covid.acil.api.core.ResponseInterface;
import com.sba.covid.acil.api.model.provinces.ProvinceModel;
import com.sba.covid.acil.helpers.Constants;
import com.sba.covid.acil.helpers.core.MyFragmentManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainPhonesRequest extends MasterAPI {
    ResponseInterface responseInterface;
    MyFragmentManager myFragmentManager;
    private String url = APIConstants.PHONES;
    private String id = "";

    public MainPhonesRequest(ResponseInterface responseInterface, MyFragmentManager myFragmentManager) {
        this.responseInterface = responseInterface;
        this.myFragmentManager = myFragmentManager;
    }

    public void request(int id) {
        if (checkRequest(tinyDB.getLong(Constants.PHONE_REQUEST_LAST_TIME + id, 0)) && myFragmentManager.isOnline() && id != 0) {
            this.id = id + "";
            myFragmentManager.showProgress();
            HashMap<String, String> params = new HashMap<>();
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

    StringRequestListener newsRequest = new StringRequestListener() {
        @Override
        public void onResponse(String response) {
            //response = "{\"httpStatus\":\"OK\",\"timestamp\":\"2020-03-28T00:35:33.3426992\",\"message\":\"succes\",\"data\":{\"majorPhones\":[{\"id\":32,\"name\":\"SMO\",\"category\":{\"id\":1,\"name\":\"Manav\"},\"neighborhood\":null,\"province\":{\"id\":10,\"name\":\"BALIKESİR\",\"hibernateLazyInitializer\":{}},\"district\":{\"id\":1161,\"name\":\"AYVALIK\"},\"phone\":\"05425287131\"},{\"id\":25,\"name\":\"Büyükşehir Belediye\",\"category\":{\"id\":0,\"name\":\"Tüm ihtiyaçlar\"},\"neighborhood\":null,\"province\":{\"id\":10,\"name\":\"BALIKESİR\",\"hibernateLazyInitializer\":{}},\"district\":null,\"phone\":\"4441066\"}],\"publicPhones\":[{\"id\":33,\"name\":\"SMO\",\"category\":{\"id\":1,\"name\":\"Manav\"},\"neighborhood\":null,\"province\":{\"id\":10,\"name\":\"BALIKESİR\",\"hibernateLazyInitializer\":{}},\"district\":{\"id\":1161,\"name\":\"AYVALIK\"},\"phone\":\"05442926812\"}],\"globalPhones\":[{\"id\":28,\"name\":\"Jandarma\",\"category\":{\"id\":4,\"name\":\"Koordinasyon\"},\"neighborhood\":null,\"province\":{\"id\":0,\"name\":\"Ülke Geneli\"},\"district\":null,\"phone\":\"156\"},{\"id\":29,\"name\":\"Corona Danışma Hattı\",\"category\":{\"id\":4,\"name\":\"Koordinasyon\"},\"neighborhood\":null,\"province\":{\"id\":0,\"name\":\"Ülke Geneli\"},\"district\":null,\"phone\":\"184\"},{\"id\":26,\"name\":\"Ambulans\",\"category\":{\"id\":4,\"name\":\"Koordinasyon\"},\"neighborhood\":null,\"province\":{\"id\":0,\"name\":\"Ülke Geneli\"},\"district\":null,\"phone\":\"112\"},{\"id\":27,\"name\":\"Polis\",\"category\":{\"id\":4,\"name\":\"Koordinasyon\"},\"neighborhood\":null,\"province\":{\"id\":0,\"name\":\"Ülke Geneli\"},\"district\":null,\"phone\":\"155\"}]}}";
            myFragmentManager.hideProgress();
            ResponseError error = new Gson().fromJson(response, ResponseError.class);
            if (error.isSuccess()) {
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
