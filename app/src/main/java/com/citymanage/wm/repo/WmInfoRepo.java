package com.citymanage.wm.repo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by com on 2017-08-29.
 */

public class WmInfoRepo {
    @SerializedName("resultCode")
    private String resultCode;

    @SerializedName("resultMessage")
    private String resultMessage;

    @SerializedName("manageId")
    private String manageId;

    @SerializedName("locationName")
    private String locationName;

    @SerializedName("waterQuality")
    private String waterQuality;

    @SerializedName("waterLevel")
    private String waterLevel;

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

    public String getWaterQuality() {
        return waterQuality;
    }

    public String getWaterLevel() {
        return waterLevel;
    }

    public String getInstallationDateTime() {
        return installationDateTime;
    }
}
