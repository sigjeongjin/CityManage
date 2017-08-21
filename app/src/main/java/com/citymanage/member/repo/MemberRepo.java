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

    @SerializedName("cityCode")
    private String cityCode;

    @SerializedName("cityName")
    private String cityName;

    @SerializedName("stateCode")
    private String stateCode;

    @SerializedName("stateName")
    private String stateName;

    public String getResultMessage() { return resultMessage; }

    public String getResultCode() {
        return resultCode;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getStateName() {
        return stateName;
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
