package com.citymanage.favorite.repo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by park on 2017-08-06.
 */

public class FavoriteRepo {
    @SerializedName("resultCode")
    private String resultCode;

    @SerializedName("resultMessage")
    private String resultMessage;


    @SerializedName("memberId")
    private String memberId;

    @SerializedName("manageId")
    private String manageId;

    @SerializedName("bookmark")
    private String bookmark;




    public String getResultMessage() {
        return resultMessage;
    }

    public String getResultCode() {
        return resultCode;
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
