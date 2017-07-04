package com.citymanage;

import android.content.Context;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by we25 on 2017-07-04.
 */

public class WmAPI {

    /**
     * Retrofit 객체를 가져오는 메소드
     *
     * @param context
     * @return
     */
    public static WmAPI getRetrofit(Context context) {
        // 현재 서비스객체의 이름으로 Retrofit 객체를 초기화 하고 반환
        return (WmAPI) retrofit(context, WmAPI.class);
    }

    // SignAPI 인터페이스
    public interface SignAPI {
        /**
         * 회원가입 메소드
         *
         * @param sensorId
         * @param city
         * @param state
         * @param street
         * @param waterQuality
         * @param waterLevel

         * @return
         */
        @FormUrlEncoded
        @POST(APIUrl.SIGN_UP_URL)
        Call<WmData> up(
                @Field("sensorId") String sensorId,
                @Field("city") String city,
                @Field("state") String state,
                @Field("street") String street,
                @Field("waterQuality") String waterQuality,
                @Field("waterLevel") String waterLevel
        );

        /**
         * 로그인 메소드
         *
         * @param city
         * @param state
         * @param street
         * @return
         */
        @FormUrlEncoded
        @POST(APIUrl.SIGN_IN_URL)
        Call<WmData> in(
                @Field("city") String city,
                @Field("state") String state,
                @Field("street") String street
        );
    }
}
