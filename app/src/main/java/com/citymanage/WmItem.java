package com.citymanage;

/**
 * Created by minjeon on 2017-06-26.
 */

public class WmItem {

    String address;        // 설치된 지역정보
    String waterQuality;   // 수질 센서 (waterQuality)
    String waterLevel;     // 수압 센서 (waterLevel)


    public WmItem(String address) {
        this.address = address;
    }

    public WmItem(String address, String waterQuality, String waterLevel) {
        this.address = address;
        this.waterQuality = waterQuality;
        this.waterLevel = waterLevel;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
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
