package com.citymanage;

/**
 * Created by minjeon on 2017-06-26.
 */

public class WmItem {

    String sensorId;
    String address;        // 설치된 지역정보
    String addressInfo;
    String waterQuality;   // 수질 센서 (waterQuality)
    String waterLevel;     // 수압 센서 (waterLevel)


    public WmItem(String address) {
        this.address = address;
    }

    public WmItem(String sensorId, String address, String addressInfo, String waterQuality, String waterLevel) {
        this.sensorId = sensorId;
        this.address = address;
        this.addressInfo = addressInfo;
        this.waterQuality = waterQuality;
        this.waterLevel = waterLevel;
    }

    public String getSensorId() {
        return sensorId;
    }
    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressInfo() {
        return addressInfo;
    }
    public void setAddressInfo(String address) {
        this.addressInfo = addressInfo;
    }

    public String getWaterQuality() {
        return waterQuality;
    }
    public void setWaterQuality(String waterQuality) {
        this.waterQuality = waterQuality;
    }

    public String getWaterLevel() {
        return waterLevel;
    }
    public void setWaterLevel(String waterLevel) {
        this.waterLevel = waterLevel;
    }
}