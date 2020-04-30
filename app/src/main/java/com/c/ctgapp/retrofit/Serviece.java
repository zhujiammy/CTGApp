package com.c.ctgapp.retrofit;

import androidx.annotation.Nullable;

import com.c.ctgapp.mvvm.model.Response;
import com.c.ctgapp.mvvm.model.User;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Serviece {
    //短信登录
    @FormUrlEncoded
    @POST("/signIn/login")
    Observable<Response<User.UserBean>> login(
           @Field("telephone") String telephone,
           @Field("code") @Nullable String code
    );

    //密码登录
    @FormUrlEncoded
    @POST("/signIn/login")
    Observable<Response<User.UserBean>> loginpass(
            @Field("telephone") String telephone,
            @Field("password") @Nullable String password
    );

    @POST("/system/sms/{telephone}/{type}")
    Observable<Response>getsms(
            @Path("telephone") String telephone,
            @Path("type") String type
    );
}
