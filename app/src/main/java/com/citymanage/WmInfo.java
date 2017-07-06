package com.citymanage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by we25 on 2017-07-06.
 */

public class WmInfo {
    @SerializedName("sensorId")
    @Expose
    public String sensorId;

    @SerializedName("city")
    @Expose
    public String city;

    @SerializedName("state")
    @Expose
    public String state;

    @SerializedName("street")
    @Expose
    public String street;

    @SerializedName("waterQuality")
    @Expose
    public String waterQuality;

    @SerializedName("waterLevel")
    @Expose
    public String waterLevel;
}
