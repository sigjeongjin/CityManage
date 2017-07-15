package com.citymanage.favorite;

/**
 * Created by we25 on 2017-06-26.
 */

public class FavoriteItem {
    String addressInfo;
    String sensorId;
    String favoriteDescription;

    public FavoriteItem(String pAddressInfo, String pSensorId, String pFavoriteDscription) {
        this.addressInfo = pAddressInfo;
        this.sensorId = pSensorId;
        this.favoriteDescription = pFavoriteDscription;
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

    public String getFavoriteDescription() {
        return favoriteDescription;
    }

    public void setFavoriteDescripTion(String favoriteDescription) {
        this.favoriteDescription = favoriteDescription;
    }
}
