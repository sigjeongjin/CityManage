package com.citymanage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * WmData class
 *
 * @author
 */
public class WmData {
    @SerializedName("resultCode")
    public String resultCode;

    @SerializedName("wmInfo")
    public List<WmInfo> wmInfo;

    public class WmInfo {
        @SerializedName("sensorId")
        public String sensorId;

        @SerializedName("city")
        public String city;

        @SerializedName("state")
        public String state;

        @SerializedName("street")
        public String street;

        @SerializedName("waterQuality")
        public String waterQuality;

        @SerializedName("waterLevel")
        public String waterLevel;
    }

    @SerializedName("array")
    public String array;
}