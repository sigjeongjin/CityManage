package com.citymanage.push.repo;

import com.citymanage.member.repo.PushInfoRepo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by park on 2017-08-06.
 */

public interface PushService {
    @Headers({"Accept:application/json"})
    @GET("pushHistoryList.app")
    Call<PushHistoryInfoRepo> getPushHistoryList(@Query("memberId") String memberId, @Query("manageType") String manageType);
}
