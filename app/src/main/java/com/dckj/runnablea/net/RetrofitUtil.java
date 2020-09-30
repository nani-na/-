package com.dckj.runnablea.net;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private volatile static RetrofitUtil sInstance;
    private Retrofit mRetrofit;
    private GETData mGetDataService;
    private RetrofitUtil(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //拦截器打印
                Logger.d(message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000, TimeUnit.SECONDS)
                .writeTimeout(5000, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BaseApi.APP_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mGetDataService = mRetrofit.create(GETData.class);

    }
    public static RetrofitUtil getInstance(){
        if (sInstance == null){
            synchronized(RetrofitUtil.class){
                if (sInstance == null){
                    sInstance = new RetrofitUtil();
                }
            }
        }
        return sInstance;
    }
    public GETData getDataService(){
        return mGetDataService;
    }

}
