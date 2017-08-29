package com.citymanage.favorite.repo;


import com.citymanage.favorite.repo.FavoriteRepo;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by park on 2017-08-06.
 */

public interface FavoriteService {



    @Headers({"Accept:application/json"})
    @GET("favoritesRegister.app")
    Call<FavoriteRepo> getFavoritesRegister(@Query("memberId") String id, @Query("bookmark") String bookmark, @Query("manageId") String manageId);

    @Headers({"Accept:application/json"})
    @GET("operationStatusRegister.app")
    Call<FavoriteRepo> getOperationStatusRegister(@Query("sensorId") String id, @Query("operationStatus") String operationStatus);






}
