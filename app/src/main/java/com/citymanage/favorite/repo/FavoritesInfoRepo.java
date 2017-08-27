package com.citymanage.favorite.repo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by park on 2017-08-27.
 */

public class FavoritesInfoRepo {

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

    private List<Favorites> favoritesList = new ArrayList<Favorites>();

    public List<Favorites> getFavoritesList() {
        return favoritesList;
    }

    public class Favorites{
        @SerializedName("manageId")
        private String manageId;

        @SerializedName("locationName")
        private String locationName;

        public String getManageId() {
            return manageId;
        }

        public String getLocationName() {
            return locationName;
        }
    }
}
