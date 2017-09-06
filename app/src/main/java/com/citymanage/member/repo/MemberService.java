package com.citymanage.member.repo;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Call<CityRepo> getCityInfo();

    @Headers({"Accept:application/json"})
    @GET("stateInfo.app")
    Call<StateRepo> getStateInfo(@Query("cityCode") String cityCode);


    @Headers({"Accept:application/json"})
    @GET("cityStateInfoRegister.app")
    Call<MemberRepo> getCityStateInfoRegister(@Query("cityCode") String citycode, @Query("stateCode") String statecode, @Query("memberId") String memberId, @Query("memberPwd") String memberPwd);

    @Headers({"Accept:application/json"})
    @GET("memberPwdConfirm.app")
    Call<MemberRepo> getMemberPwdConfirm(@Query("memberId") String id, @Query("memberPwd") String password);

    @Headers({"Accept:application/json"})
    @GET("memberPwdChange.app")
    Call<MemberRepo> getMemberPwdChange(@Query("memberChangePwd") String memberChangePwd,@Query("memberId") String id, @Query("memberPwd") String password);

    @Headers({"Accept:application/json"})
    @GET("memberProfileImageChange.app")
    Call<MemberRepo> getMemberProfileImageChange(@Query("memberId") String id, @Query("memberPhoto") String memberPhoto);

    @Headers({"Accept:application/json"})
    @GET("favoritesRegister.app")
    Call<MemberRepo> getFavoritesRegister(@Query("memberId") String id, @Query("bookmark") String bookmark, @Query("manageId") String manageId);

    @Headers({"Accept:application/json"})
    @Multipart
    @POST("register.app")
    Call<MemberRepo> setRegister(@Part("file") RequestBody song, @Part("file_path") String fileName, @Part("memberId") String id, @Part("user_id") String userId, @Part("token") String token);

}





