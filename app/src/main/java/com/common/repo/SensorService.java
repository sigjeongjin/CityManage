package com.common.repo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by park on 2017-08-06.
 */

public interface SensorService {
    @Headers({"Accept:application/json"})
    @GET("sensorList.app")
    Call<SensorInfoRepo> getSensorList(@Query("memberId") String memberId, @Query("manageType") String manageType);

    @Headers({"Accept:application/json"})
    @GET("stateSearchSensorList.app")
    Call<SensorInfoRepo> getStateSearchSensorList(@Query("memberId") String memberId, @Query("manageType") String manageType, @Query("searchText") String searchText);
}
