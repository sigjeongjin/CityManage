package com.citymanage.member.repo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by com on 2017-08-29.
 */

public class CityRepo {

    List<City> city = new ArrayList<>();

    public List<City> getCity() {
        return city;
    }

    public class City {
        @SerializedName("cityCode")
        private String cityCode;

        @SerializedName("cityName")
        private String cityName;

        public String getCityCode() {
            return cityCode;
        }

        public String getCityName() {
            return cityName;
        }
    }
}
