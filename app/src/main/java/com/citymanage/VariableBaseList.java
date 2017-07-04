package com.citymanage;

public class VariableBaseList {
    private String sensorId;  // 센서 ID
    private String city;      // 시/도
    private String state;     // 시/군/구
    private String town;      // 동/읍/면

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

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
