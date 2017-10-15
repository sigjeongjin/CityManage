package com.citymanage.push.repo;

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

    @Headers({"Accept:application/json"})
    @POST("pushTokenRegister.app")
    Call<PushInfoRepo> getPushTokenRegister(@Query("memberId") String memberId, @Query("pushToken") String pushToken);
}
