package com.citymanage.favorite.repo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by park on 2017-08-06.
 */

public interface FavoritesService {
    @Headers({"Accept:application/json"})
    @GET("favoritesList.app")
    Call<FavoritesInfoRepo> getFavoritesInfo(@Query("memberId") String memberId, @Query("manageType") String manageType);

    @Headers({"Accept:application/json"})
    @GET("/favoritesRegister.app")
    Call<FavoritesInfoRepo> setFavoritesRegister(@Query("memberId") String memberId, @Query("manageId") String manageId);

    @Headers({"Accept:application/json"})
    @GET("/favoritesRelease.app")
    Call<FavoritesInfoRepo> setFavoritesRelease(@Query("memberId") String memberId, @Query("manageId") String manageId);
}
