package com.citymanage;

/**
 * Created by we25 on 2017-06-27.
 */

public class ApiUrlList {
    private final static String HOST = "http://192.168.0.230:3000";
    private final static String PUSHHISTORY = HOST + "/pushHistory";
    private final static String LOGIN = HOST + "/login";

    static String getPushHistoryUrl() {
        return  PUSHHISTORY;
    }
}


