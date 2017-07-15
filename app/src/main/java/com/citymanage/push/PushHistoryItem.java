package com.citymanage.push;

/**
 * Created by we25 on 2017-06-26.
 */

public class PushHistoryItem {
    String addressInfo;
    String sensorId;
    String pushDescription;

    public PushHistoryItem(String pAddressInfo, String pSensorId, String pPushDescription) {
        this.addressInfo = pAddressInfo;
        this.sensorId = pSensorId;
        this.pushDescription = pPushDescription;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getPushDescription() {
        return pushDescription;
    }

    public void setPushDescripTion(String pushDescription) {
        this.pushDescription = pushDescription;
    }
}
