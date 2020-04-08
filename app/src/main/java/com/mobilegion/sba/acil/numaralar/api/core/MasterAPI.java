/*
 * Copyright (c) Serhan CANOVA - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Created and written by Serhan CANOVA <serhancanova@gmail.com>, 6.3.2020
 */

package com.mobilegion.sba.acil.numaralar.api.core;

import com.androidnetworking.error.ANError;
import com.mobilegion.sba.acil.numaralar.helpers.db.tinydb.TinyDB;

import java.util.Date;

public class MasterAPI {
    public SCRestManager scRestManager = SCRestManager.getInstance();
    public TinyDB tinyDB = TinyDB.getInstance();

    public void checkResponse(ANError ANError, ResponseInterface responseInterface) {
        String message = null;
        if (ANError.getErrorCode() != 0 && ANError.getMessage() != null) {
            message = ANError.getMessage();
        }
        responseInterface.error(message);
    }

    public boolean checkRequest(long lastRequestDate) {
        if (((new Date()).getTime() - lastRequestDate) > 1)//900000
            return true;
        return false;
    }
}
