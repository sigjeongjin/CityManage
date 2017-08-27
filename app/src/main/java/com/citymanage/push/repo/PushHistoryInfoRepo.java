package com.citymanage.push.repo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by park on 2017-08-27.
 */

public class PushHistoryInfoRepo {

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

    private List<Push> pushHistoryList = new ArrayList<Push>();

    public List<Push> getPushHistoryList() {
        return pushHistoryList;
    }

    public class Push{
        @SerializedName("manageId")
        private String manageId;

        @SerializedName("pushSendTime")
        private String pushSendTime;

        @SerializedName("locationName")
        private String locationName;

        public String getManageId() {
            return manageId;
        }

        public String getLocationName() {
            return locationName;
        }

        public String getPushSendTime() {
            return pushSendTime;
        }
    }
}
