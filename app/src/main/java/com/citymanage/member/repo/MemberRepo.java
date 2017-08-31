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


    @SerializedName("manageId")
    private String manageId;

    @SerializedName("bookmark")
    private String bookmark;


    @SerializedName("memberPhoto")
    private String memberPhoto;

    public String getResultMessage() {
        return resultMessage;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getmemberChangePwd() {
        return memberChangePwd;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getbookmark() {
        return bookmark;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getManageId() {
        return manageId;
    }

    public String getMemberPhoto() {
        return memberPhoto;
    }

    public String getMemberPwd() {
        return memberPwd;
    }

    //    public List<Tm> tmList = new ArrayList<>();
//    public List<Tm> getTm() {return tmList;}
//
//    public class Tm {
//        @SerializedName("name")
//        private String name;
//
//        @SerializedName("id")
//        private String id;
//
//        public String getName() {
//            return name;
//        }
//
//        public String getId() {
//            return id;
//        }
//    }

}
