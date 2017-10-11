package com.citymanage.tm.repo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by com on 2017-08-29.
 */

public class TmInfoRepo {
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

    @SerializedName("stink")
    private String stink;

    @SerializedName("generous")
    private String generous;

    @SerializedName("lockStatus")
    private String lockStatus;

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

    public String getFlameDetection() {
        return flameDetection;
    }

    public String getStink() {
        return stink;
    }

    public String getGenerous() {
        return generous;
    }

    public String getLockStatus() {
        return lockStatus;
    }

    public String getInstallationDateTime() {
        return installationDateTime;
    }

    public String getBookmark() { return bookmark; }
}
