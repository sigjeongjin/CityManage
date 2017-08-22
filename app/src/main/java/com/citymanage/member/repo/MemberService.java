package com.citymanage.member.repo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by park on 2017-08-06.
 */

public interface MemberService {
    @Headers({"Accept:application/json"})
    @GET("memberLogin.app")
    Call<MemberRepo> getMemberRepo(@Query("memberId") String id, @Query("memberPwd") String password);

    @Headers({"Accept:application/json"})
    @GET("cityInfo.app")
    Call<MemberRepo> getCityInfo();

    @Headers({"Accept:application/json"})
    @GET("stateInfo.app")
    Call<MemberRepo> getStateInfo(@Query("cityCode") String cityCode);


    @Headers({"Accept:application/json"})
    @GET("cityStateInfoRegister.app")
    Call<MemberRepo> getCityStateInfoRegister(@Query("cityGecode") String cityGeocode, @Query("stateGeocode") String stateGeocode);

    @Headers({"Accept:application/json"})
    @GET("memberPwdConfirm.app")
    Call<MemberRepo> getMemberPwdConfirm(@Query("memberId") String id, @Query("memberPwd") String password);

    @Headers({"Accept:application/json"})
    @GET("memberPwdChange.app")
    Call<MemberRepo> getMemberPwdChange(@Query("memberId") String id, @Query("memberPwd") String password);








}
