/*
 *  * Created by Serhat Bekir AK on 27.03.2020 23:29
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27.03.2020 23:29
 */

package com.mobilegion.acil.numaralar.api.model.main;

import java.util.ArrayList;

public class MainPhones {
    private ArrayList<Phone> majorPhones;
    private ArrayList<Phone> publicPhones;
    private ArrayList<Phone> globalPhones;

    public ArrayList<Phone> getMajorPhones() {
        return majorPhones;
    }

    public void setMajorPhones(ArrayList<Phone> value) {
        this.majorPhones = value;
    }

    public ArrayList<Phone> getPublicPhones() {
        return publicPhones;
    }

    public void setPublicPhones(ArrayList<Phone> value) {
        this.publicPhones = value;
    }

    public ArrayList<Phone> getGlobalPhones() {
        return globalPhones;
    }

    public void setGlobalPhones(ArrayList<Phone> value) {
        this.globalPhones = value;
    }

    public class Phone {
        private long id;
        private String name;
        private Category category;
        private Category neighborhood;
        private Category province;
        private Category district;
        private String phone;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public Category getNeighborhood() {
            return neighborhood;
        }

        public void setNeighborhood(Category neighborhood) {
            this.neighborhood = neighborhood;
        }

        public Category getProvince() {
            return province;
        }

        public void setProvince(Category province) {
            this.province = province;
        }

        public Category getDistrict() {
            return district;
        }

        public void setDistrict(Category district) {
            this.district = district;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public class Category {
        private long id;
        private String name;

        public long getID() {
            return id;
        }

        public void setID(long value) {
            this.id = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String value) {
            this.name = value;
        }
    }

}