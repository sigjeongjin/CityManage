package com.citymanage.wm.retrofit.repo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by park on 2017-08-06.
 */

public class WmRepo {
    @SerializedName("response")
    private String response;

    @SerializedName("name")
    private String name;

    @SerializedName("age")
    private String age;
}
