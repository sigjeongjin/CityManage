package com.citymanage;

/**
 * Created by minjeongkim on 2017-07-04.
 */

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * WmAPIAdapter 클래스
 *
 * @autor devetude
 */
public class WmAPIAdapter {
    /**
     * Retrofit 객체를 초기화하는 메소드
     *
     * @param context
     * @param WmService
     * @return
     */
    protected static Object retrofit(Context context, Class<?> WmService) {

        /**
         * Retrofit 객체 생성 과정
         *
         * 1. Retrofit 객체 생성
         * 2. base(api 서버) url 설정
         * 3. json 형식의 reponse 데이터의 파싱을 위해 Gson 추가
         * 4. Retrofit 빌드
         *
         * 주의) addConverterFactory를 추가하지 않을 경우 어플리케이션이 종료됨
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrlList.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        /**
         * 서비스객체의 이름으로 Retrofit 객체 생성 및 반환
         *
         * ex) retrofit.create(SignService.class);
         */
        return retrofit.create(WmService);
    }
}