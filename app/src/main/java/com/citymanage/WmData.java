package com.citymanage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * WmData class
 *
 * @author
 */
public class WmData {
    @SerializedName("resultCode")
    @Expose
    public String resultCode;

    @SerializedName("wmInfo")
    @Expose
    public List<WmInfo> wmInfo;

    public List<WmInfo> getWmInfo() {
        return wmInfo;
    }

    public void setWmInfo(List<WmInfo> wmInfo) {
        this.wmInfo = wmInfo;
    }

    @SerializedName("array")
    @Expose
    public String array;
}