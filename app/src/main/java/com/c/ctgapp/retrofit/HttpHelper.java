package com.c.ctgapp.retrofit;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.c.ctgapp.retrofit.Constants.DEFAULT_TIMEOUT;

public class HttpHelper {
    private static Retrofit mRetrofit;
    private HttpHelper() {
        Log.i("Constants", Constants.SERVER_URL);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())//往retrofit中装一个Gson插件
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用RxJava作为回调适配器（Observable）
                .build();
    }

    public static Retrofit getInstance() {
        if (mRetrofit == null) {
            synchronized (RetrofitManager.class) {
                if (mRetrofit == null) {
                    mRetrofit = new HttpHelper().getRetrofit();
                }
            }
        }
        return mRetrofit;
    }


    private Retrofit getRetrofit() {
        return mRetrofit;
    }

}
