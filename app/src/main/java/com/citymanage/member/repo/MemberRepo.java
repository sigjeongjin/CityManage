package com.citymanage.member.repo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by park on 2017-08-06.
 */

public class MemberRepo {
    @SerializedName("resultCode")
    private String resultCode;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    public String getResultCode() {
        return resultCode;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
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
