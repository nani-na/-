package com.dckj.runnablea.net;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GETData {


    /**
     * 获取所有省市区
     */
    @GET("api/index/get_city")
    Observable<ResponseBody> get_city();

    /**
     * 发送验证码
     *
     * @param phone
     */
    @POST("api/user/get_sms")
    @FormUrlEncoded
    Observable<ResponseBody> get_sms(@Field("token") String token,
                                     @Field("phone") String phone);
}
