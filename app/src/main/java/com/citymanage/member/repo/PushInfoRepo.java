package com.citymanage.member.repo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by minjeongkim on 2017-10-15.
 */

public class PushInfoRepo {

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

    @SerializedName("pushToken")
    private String pushToken;

    @SerializedName("memberId")
    private String memberId;

    @SerializedName("memberPhone")
    private String memberPhone;

    public String getPushToken() {
        return pushToken;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

}
