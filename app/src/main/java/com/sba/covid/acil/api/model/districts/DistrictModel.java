/*
 *  * Created by Serhat Bekir AK on 29.03.2020 03:06
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 26.03.2020 00:26
 */

package com.sba.covid.acil.api.model.districts;

public class DistrictModel {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
