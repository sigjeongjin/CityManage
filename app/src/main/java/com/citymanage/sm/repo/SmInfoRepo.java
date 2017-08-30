package com.citymanage.sm.repo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by com on 2017-08-29.
 */

public class SmInfoRepo {

    @SerializedName("resultCode")
    private String resultCode;

    @SerializedName("resultMessage")
    private String resultMessage;

    @SerializedName("manageId")
    private String manageId;

    @SerializedName("locationName")
    private String locationName;

    @SerializedName("flameDetection")
    private String flameDetection;

    @SerializedName("smokeDetection")
    private String smokeDetection;

    @SerializedName("installationDateTime")
    private String installationDateTime;

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

    public String getFlameDetection() {
        return flameDetection;
    }

    public String getSmokeDetection() {
        return smokeDetection;
    }

    public String getInstallationDateTime() {
        return installationDateTime;
    }
}
