package com.citymanage;

        import com.google.gson.annotations.SerializedName;

/**
 * WmItem 클래스
 *
 * @author devetude
 */
public class WmItem {

    @SerializedName("sensorId")
    String sensorId;

    @SerializedName("city")
    String city;

    @SerializedName("state")
    String state;

    @SerializedName("street")
    String street;

    @SerializedName("waterQuality")
    String waterQuality;

    @SerializedName("waterLevel")
    String waterLevel;

    public WmItem(String sensorId, String city, String state, String street, String waterQuality, String waterLevel) {
        this.sensorId = sensorId;
        this.city = city;
        this.state = state;
        this.street = street;
        this.waterQuality = waterQuality;
        this.waterLevel = waterLevel;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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