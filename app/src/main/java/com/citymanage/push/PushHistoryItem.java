package com.citymanage.push;

/**
 * Created by we25 on 2017-06-26.
 */

public class PushHistoryItem {
    String addressInfo;
    String sensorId;
    String pushSendTime;

    public PushHistoryItem(String pAddressInfo, String pSensorId, String pushSendTime) {
        this.addressInfo = pAddressInfo;
        this.sensorId = pSensorId;
        this.pushSendTime = pushSendTime;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

//    public void setAddressInfo(String addressInfo) {
//        this.addressInfo = addressInfo;
//    }

    public String getSensorId() {
        return sensorId;
    }

//    public void setSensorId(String sensorId) {
//        this.sensorId = sensorId;
//    }

    public String getPushSendTime() {
        return pushSendTime;
    }

//    public void setPushSendTime(String pushSendTime) {
//        this.pushSendTime = pushSendTime;
//    }
}
