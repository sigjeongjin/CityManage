package com.citymanage.sm;

/**
 * Created by we25 on 2017-06-26.
 **/

public class SmListItem {
    String addressInfo;
    String sensorId;

    public SmListItem(String pAddressInfo, String pSensorId) {
        this.addressInfo = pAddressInfo;
        this.sensorId = pSensorId;
    }
    //네트워크의 주소정보를 가져옴
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
