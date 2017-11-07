package com.common.repo;

import com.citymanage.gm.repo.GmInfoRepo;
import com.citymanage.sm.repo.SmInfoRepo;
import com.citymanage.tm.repo.TmInfoRepo;
import com.citymanage.wm.repo.WmInfoRepo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by park on 2017-08-06.
 */

public interface SensorService {
    @Headers({"Accept:application/json"})
    @GET("/sensorList.app")
    Call<SensorInfoRepo> getSensorList(@Query("memberId") String memberId, @Query("manageType") String manageType);

    @Headers({"Accept:application/json"})
    @GET("/stateSearchSensorList.app")
    Call<SensorInfoRepo> getStateSearchSensorList(@Query("memberId") String memberId, @Query("manageType") String manageType, @Query("searchText") String searchText);

    @Headers({"Accept:application/json"})
    @GET("/wmInfo.app")
    Call<WmInfoRepo> getWmInfo(@Query("manageId") String manageId, @Query("memberId") String memberId);

    @Headers({"Accept:application/json"})
    @GET("/tmInfo.app")
    Call<TmInfoRepo> getTmInfo(@Query("manageId") String manageId, @Query("memberId") String memberId);

    @Headers({"Accept:application/json"})
    @GET("/smInfo.app")
    Call<SmInfoRepo> getSmInfo(@Query("manageId") String manageId, @Query("memberId") String memberId);

    @Headers({"Accept:application/json"})
    @GET("/gmInfo.app")
    Call<GmInfoRepo> getGmInfo(@Query("manageId") String manageId, @Query("memberId") String memberId);

    @Headers({"Accept:application/json"})
    @GET("/sensorMapInfoList.app")
    Call<SensorInfoRepo> getSensorMapInfoList(@Query("memberId") String memberId, @Query("manageType") String manageType );
}
