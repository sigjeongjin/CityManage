package com.citymanage.member.repo;

import com.google.gson.annotations.SerializedName;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class MemberMuiltRepo {
    @SerializedName("resultCode")
    private String resultCode;

    @SerializedName("resultMessage")
    private String resultMessage;

    @SerializedName("memberName")
    private RequestBody memberName;

    @SerializedName("memberId")
    private RequestBody memberId;

    @SerializedName("memberPwd")
    private RequestBody memberPwd;

    @SerializedName("memberChangePwd")
    private RequestBody memberChangePwd;

    @SerializedName("manageId")
    private RequestBody manageId;

    @SerializedName("memberPhoto")
    private MultipartBody.Part memberPhoto;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public RequestBody getMemberName() {
        return memberName;
    }

    public void setMemberName(RequestBody memberName) {
        this.memberName = memberName;
    }

    public RequestBody getMemberId() {
        return memberId;
    }

    public void setMemberId(RequestBody memberId) {
        this.memberId = memberId;
    }

    public RequestBody getMemberPwd() {
        return memberPwd;
    }

    public void setMemberPwd(RequestBody memberPwd) {
        this.memberPwd = memberPwd;
    }

    public RequestBody getMemberChangePwd() {
        return memberChangePwd;
    }

    public void setMemberChangePwd(RequestBody memberChangePwd) {
        this.memberChangePwd = memberChangePwd;
    }

    public RequestBody getManageId() {
        return manageId;
    }

    public void setManageId(RequestBody manageId) {
        this.manageId = manageId;
    }

    public MultipartBody.Part getMemberPhoto() {
        return memberPhoto;
    }

    public void setMemberPhoto(MultipartBody.Part memberPhoto) {
        this.memberPhoto = memberPhoto;
    }
}
