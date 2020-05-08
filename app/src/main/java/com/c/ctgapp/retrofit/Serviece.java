package com.c.ctgapp.retrofit;

import androidx.annotation.Nullable;

import com.c.ctgapp.mvvm.model.PersonalInfo;
import com.c.ctgapp.mvvm.model.Response;
import com.c.ctgapp.mvvm.model.User;
import com.c.ctgapp.mvvm.model.uploadImg;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Serviece {
    //短信登录
    @FormUrlEncoded
    @POST("/signIn/login")
    Observable<Response<User>> login(
           @Field("telephone") String telephone, //手机号码
           @Field("code") @Nullable String code  //验证码
    );

    //密码登录
    @FormUrlEncoded
    @POST("/signIn/login")
    Observable<Response<User>> loginpass(
            @Field("telephone") String telephone, //手机号码
            @Field("password") @Nullable String password  //密码
    );

    //发送短信
    @POST("/system/sms/{telephone}/{type}")
    Observable<Response>getsms(
            @Path("telephone") String telephone, //手机号码
            @Path("type") String type  //短信类型 1-注册
    );
    //忘记密码
    @FormUrlEncoded
    @POST("/userInfo/updatePassByCodeAndNewPass")
    Observable<Response>updatePassByCodeAndNewPass(
            @Field("telephone") String telephone, //手机号码
            @Field("code") String code, //验证码
            @Field("newPass") String newPass //新密码
    );
    //修改密码
    @FormUrlEncoded
    @POST("/userInfo/updatePassword")
    Observable<Response>updatePassword(
            @Field("currentUserId") String currentUserId, //当前用户id
            @Field("oldPassword") String oldPassword, //旧密码
            @Field("newPassword") String newPassword  //新密码
    );

    //注册
    @FormUrlEncoded
    @POST("/signUp/register")
    Observable<Response>register(
            @Field("realName") String realName, //真实姓名
            @Field("telephone") String telephone,//手机号码
            @Field("password") String password, //密码
            @Field("industry") @Nullable String industry,
            @Field("code") String code  //验证码
    );

    //保存个人信息

    @FormUrlEncoded
    @POST("/user/save")
    Observable<Response>usersave(
            @Field("uid") String uid,//userid
            @Field("ctgId") String ctgId, //车同轨id
            @Field("edulevel") String edulevel, //专业特长
            @Field("nickname") String nickname, //昵称
            @Field("file") String file,//头像
            @Field("work") String work //职位
    );

    //根据用户ID查询用户详细信息
    @GET("/userInfo/info/{uid}")
    Observable<Response<PersonalInfo>>userInfo(
            @Path("uid") String uid //userid
    );

    //上传文件
    @Multipart
    @POST("/userInfo/uploadFile")
    Observable<Response<uploadImg>>uploadFile(
            @Part MultipartBody.Part file //头像
    );
}
