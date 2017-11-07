package com.common.repo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by park on 2017-08-27.
 */

public class SensorInfoRepo {

    @SerializedName("resultCode")
    private String resultCode;

    @SerializedName("resultMessage")
    private String resultMessage;

    public String getResultCode() {
        return resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    private List<SensorResultInfo> sensorList = new ArrayList<SensorResultInfo>();

    public List<SensorResultInfo> getSensorList() {
        return sensorList;
    }

    public class SensorResultInfo{
        @SerializedName("manageId")
        private String manageId;

        @SerializedName("locationName")
        private String locationName;

        @SerializedName("longitude")
        private String longitude;

        @SerializedName("latitude")
        private String latitude;

        public String getManageId() {
            return manageId;
        }

        public String getLocationName() {
            return locationName;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getLatitude() {
            return latitude;
        }
    }
}
