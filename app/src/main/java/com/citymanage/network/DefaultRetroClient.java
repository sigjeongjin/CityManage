package com.citymanage.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by park on 2017-08-06.
 */

public class DefaultRetroClient<T> {

    public String host = "http://192.168.0.2:3000"; //현진 집

    private T service;
    private String HOST = "http://192.168.0.2:3000/tmList"; //현진 집

    public T getClient(Class<? extends T> type) {
        if(service == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {


                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("application","json")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            }).build();

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(host)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = client.create(type);
        }

        return service;
    }
}
