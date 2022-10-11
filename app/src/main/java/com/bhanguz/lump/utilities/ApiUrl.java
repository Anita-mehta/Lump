package com.bhanguz.lump.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUrl {
    public static final String BASE_url= "http://103.43.153.37:8060/apis/";
    //  public static final String BASE_url= "http://192.168.0.2/Lump/apis/";
    public static final String BASE_url_image= "http://103.43.153.37:8060/apis/img/";
    public static Retrofit retrofit;
    public static Retrofit getRetrofit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(com.bhanguz.lump.utilities.ApiUrl.BASE_url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static ApiInterface  getAllClient(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        // change your base URL
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_url)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        // Creating object for our interface
        ApiInterface api = retrofit.create(ApiInterface.class);
        return api; //return the APIInterface object
    }

}
