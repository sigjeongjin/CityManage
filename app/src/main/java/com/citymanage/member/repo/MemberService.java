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
    @GET("appLogin.app")
    Call<MemberRepo> getMemberRepo(@Query("id") String id, @Query("password") String password);
}
