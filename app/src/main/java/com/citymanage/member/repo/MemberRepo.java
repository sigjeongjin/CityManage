package com.citymanage.member.repo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by park on 2017-08-06.
 */

public class MemberRepo {

    @SerializedName("resultCode")
    private String resultCode;

    @SerializedName("resultMessage")
    private String resultMessage;

    @SerializedName("memberName")
    private String memberName;

    @SerializedName("memberId")
    private String memberId;

    @SerializedName("memberPwd")
    private String memberPwd;

    @SerializedName("memberChangePwd")
    private String memberChangePwd;

    @SerializedName("memberEmail")
    private String memberEmail;

    @SerializedName("memberPhone")
    private String memberPhone;

    @SerializedName("memberPhoto")
    private String memberPhoto;

    @SerializedName("memberPhotoOriginal")
    private String memberPhotoOriginal;

    @SerializedName("bookmark")
    private String bookmark;

    public String getResultCode() {
        return resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getMemberPwd() {
        return memberPwd;
    }

    public String getMemberChangePwd() {
        return memberChangePwd;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public String getMemberPhoto() {
        return memberPhoto;
    }

    public String getMemberPhotoOriginal() {
        return memberPhotoOriginal;
    }

    public String getBookmark() {
        return bookmark;
    }
}
