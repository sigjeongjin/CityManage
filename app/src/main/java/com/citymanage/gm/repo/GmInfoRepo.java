package com.citymanage.gm.repo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by com on 2017-08-29.
 */

public class GmInfoRepo {

    @SerializedName("resultCode")
    private String resultCode;

    @SerializedName("resultMessage")
    private String resultMessage;

    @SerializedName("manageId")
    private String manageId;

    @SerializedName("locationName")
    private String locationName;

    @SerializedName("gasDensity")
    private String gasDensity;

    @SerializedName("shockDetection")
    private String shockDetection;

    @SerializedName("installationDateTime")
    private String installationDateTime;

    @SerializedName("bookmark")
    private String bookmark;

    public String getResultCode() {
        return resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public String getManageId() {
        return manageId;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getGasDensity() {
        return gasDensity;
    }

    public String getShockDetection() {
        return shockDetection;
    }

    public String getInstallationDateTime() {
        return installationDateTime;
    }

    public String getBookmark() { return bookmark; }
}
