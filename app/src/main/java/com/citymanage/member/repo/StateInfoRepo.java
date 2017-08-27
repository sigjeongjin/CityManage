package com.citymanage.member.repo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by com on 2017-08-21.
 */

public class StateInfoRepo {
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


    public List<State> state = new ArrayList<>();

    public List<State> getState() {
        return state;
    }

    public class State {
        @SerializedName("stateCode")
        private String stateCode;

        @SerializedName("stateName")
        private String stateName;

        public String getStateCode() {
            return stateCode;
        }

        public String getStateName() {
            return stateName;
        }
    }
}
