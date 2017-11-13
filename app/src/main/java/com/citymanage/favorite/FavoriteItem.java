package com.citymanage.favorite;

/**
 * Created by we25 on 2017-06-26.
 */

public class FavoriteItem {
    String addressInfo;
    String sensorId;

    public FavoriteItem(String pAddressInfo, String pSensorId) {
        this.addressInfo = pAddressInfo;
        this.sensorId = pSensorId;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public String getSensorId() {
        return sensorId;
    }
}
