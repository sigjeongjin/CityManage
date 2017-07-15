package com.citymanage.tm;

/**
 * Created by we25 on 2017-06-26.
 **/

public class TmListItem {
    String addressInfo;
    String sensorId;

    public TmListItem(String pAddressInfo, String pSensorId) {
        this.addressInfo = pAddressInfo;
        this.sensorId = pSensorId;
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

}
