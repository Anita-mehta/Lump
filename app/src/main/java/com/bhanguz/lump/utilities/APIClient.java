package com.bhanguz.lump.utilities;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;

    //    final static String BASE_URL = "https://restaurantordersapp.com/app/api/v1/";
    final static String BASE_URL = "http://103.43.153.37:8060/apis/";
    // final static String BASE_URL = "http://192.168.0.2/Lump/apis/";


    private static int REQUEST_TIMEOUT = 60;
    private static OkHttpClient okHttpClient;

    public static Retrofit getClient(Context context) {

        Gson gson = new GsonBuilder().setLenient().create();

        if (okHttpClient == null)
            initOkHttp(context);

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    private static void initOkHttp(final Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json");

                // Adding Authorization Key
                requestBuilder.addHeader("X-custom-header", "7890abcdefghijkl");

                Request request = requestBuilder.build();
                return chain.proceed(request);

            }
        });

        okHttpClient = httpClient.build();
    }


}
