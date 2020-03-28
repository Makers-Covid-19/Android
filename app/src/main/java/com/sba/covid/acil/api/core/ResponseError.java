/*
 *  * Created by Serhat Bekir AK on 27.03.2020 23:44
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 25.03.2020 14:03
 */

package com.sba.covid.acil.api.core;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResponseError implements Serializable {

    @SerializedName("httpStatus") private String httpStatus = "";
    @SerializedName("message") private String message = "";
    @SerializedName("timestamp") private String timestamp = "";

    public String getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public long getTimesTamp() {
        return (new Date()).getTime();
    }

    public boolean isSuccess() {
        if (!getHttpStatus().equals("OK")) {
            return false;
        }
        else {
            return true;
        }
    }

}