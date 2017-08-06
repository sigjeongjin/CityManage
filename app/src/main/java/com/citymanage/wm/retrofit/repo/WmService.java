package com.citymanage.wm.retrofit.repo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by park on 2017-08-06.
 */

public interface WmService {
    @Headers({"Accept:application/json"})
    @GET("wmList/{user}")
    Call<WmRepo> getWmRepo(@Path("user") String user);
}
