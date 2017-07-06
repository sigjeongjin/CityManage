package com.citymanage;

import android.content.Context;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class WmService extends WmAPIAdapter {

    /**
     * Retrofit 객체를 가져오는 메소드
     * @param context
     * @return
     */
    public static WmAPI getRetrofit(Context context) {
        // 현재 서비스객체의 이름으로 Retrofit 객체를 초기화 하고 반환
        return (WmAPI) retrofit(context, WmAPI.class);
    }

    // SignAPI 인터페이스
    public interface WmAPI {
        /**
         * WmManger Method
         *
         * @param sensorId
         * @param city
         * @param state
         * @param street
         * @param waterQuality
         * @param waterLevel

         * @return
         */
//        @GET(ApiUrlList.WM_LIST_URL)
//        Call<WmData>
        @FormUrlEncoded
        @POST(ApiUrlList.WM_LIST_URL)
        Call<List<WmInfo>> wm(
                @Field("sensorId") String sensorId,
                @Field("city") String city,
                @Field("state") String state,
                @Field("street") String street,
                @Field("waterQuality") String waterQuality,
                @Field("waterLevel") String waterLevel
        );

        /**
         * address Method
         *
         * @param city
         * @param state
         * @param street
         * @return
         */
        @FormUrlEncoded
        @POST(ApiUrlList.ADRRESS_URL)
        Call<WmData> address(
                @Field("city") String city,
                @Field("state") String state,
                @Field("street") String street
        );
    }
}
