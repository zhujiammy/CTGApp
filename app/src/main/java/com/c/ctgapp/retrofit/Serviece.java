package com.c.ctgapp.retrofit;

import android.content.ContentResolver;

import androidx.annotation.Nullable;

import com.c.ctgapp.mvvm.model.BusinessFriend;
import com.c.ctgapp.mvvm.model.Certificationinfo;
import com.c.ctgapp.mvvm.model.Company;
import com.c.ctgapp.mvvm.model.Externalcontact;
import com.c.ctgapp.mvvm.model.Honor;
import com.c.ctgapp.mvvm.model.MyBusiness;
import com.c.ctgapp.mvvm.model.ParamTelephone;
import com.c.ctgapp.mvvm.model.PersonalInfo;
import com.c.ctgapp.mvvm.model.ReceivingAddress;
import com.c.ctgapp.mvvm.model.Response;
import com.c.ctgapp.mvvm.model.UrmFriendGroup;
import com.c.ctgapp.mvvm.model.User;
import com.c.ctgapp.mvvm.model.uploadImg;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Provides;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
            @Path("uid") int uid //userid
    );

    //上传文件
    @Multipart
    @POST("/userInfo/uploadFile")
    Observable<Response<uploadImg>>uploadFile(
            @Part MultipartBody.Part file //头像
    );

    //创建企业
    @FormUrlEncoded
    @POST("/company/base/save")
    Observable<Response>companysave(
            @Field("orgname") String orgname, //企业名称
            @Field("industry") String industry,//行业属性
            @Field("province") String province,//省
            @Field("city") String city,//市
            @Field("district") String district,//区
            @Field("address") String address, //详细地址
            @Field("createUserid") String createUserid, //创建者id
            @Field("profile") @Nullable String profile,//企业简介
            @Field("companyScale") @Nullable String companyScale,//企业规模
            @Field("employeeScale") @Nullable String employeeScale,//人员规模
            @Field("email") @Nullable String email,//邮箱
            @Field("netaddress") @Nullable String netaddress,//公司网址
            @Field("phone") @Nullable String phone//公司电话
    );

    //获取用户所有企业
    @POST("/userCompany/company/list/{userId}")
    Observable<Response<List<Company>>>userCompany(
            @Path("userId") int uid //userid
    );

    //获取企业详情
    @POST("/company/base/{companyId}")
    Observable<Response<MyBusiness>>mycompany(
            @Path("companyId") int uid //companyId
    );

    //获取对外联系人
    @POST("/company/linkman/{companyId}")
    Observable<Response<List<Externalcontact>>>linkman(
            @Path("companyId") int uid //companyId
    );

    //获取物流信息
    @POST("/company/deliveryAddress/{companyId}")
    Observable<Response<List<ReceivingAddress>>>deliveryAddress(
            @Path("companyId") int uid //companyId
    );

    //更新保存收货信息
    @FormUrlEncoded
    @POST("/company/deliveryAddress/save")
    Observable<Response>Addresssave(
            @Field("id") @Nullable String id,//更新的时候传
            @Field("province") String province,//省
            @Field("city") String city,//市
            @Field("district") String district,//区
            @Field("detailAddress") String detailAddress,//详细地址
            @Field("deliveryName") String deliveryName,//收货人
            @Field("deliveryPhone") String deliveryPhone,//收货电话
            @Field("companyId") String companyId//企业id
    );
    //保存荣誉资质
    @FormUrlEncoded
    @POST("/company/honor/save")
    Observable<Response>honorsave(
            @Field("companyId") String companyId,//企业id
            @Field("fileName") String fileName,//资质图片
            @Field("name") String name,//资质名称
            @Field("remark") String remark//资质备注
    );

    //更新荣誉资质
    @FormUrlEncoded
    @POST("/company/honor/update")
    Observable<Response>honorupdate(
            @Field("id") String id,//id
            @Field("fileName") String fileName,//资质图片
            @Field("name") String name,//资质名称
            @Field("remark") String remark//资质备注
    );

    //获取荣誉资质列表
    @FormUrlEncoded
    @POST("/company/honor/list")
    Observable<Response<Honor>>honorlist(
            @Field("companyId") String companyId,//企业id
            @Field("pageNum") int pageNum,//第几页
            @Field("pageSize") int pageSize//一页多少条
    );
    //设置默认企业
    @FormUrlEncoded
    @POST("/userCompany/company/default")
    Observable<Response>companydefault(
            @Field("companyId") int companyId,//企业id
            @Field("currentUserId") int currentUserId //用户id
    );

    //获取实名认证信息
    @POST("/person/identification/{userId}")
    Observable<Response<Certificationinfo>>identification(
            @Path("userId") String userId //userid
    );

    //实名认证
    @FormUrlEncoded
    @POST("/userInfo/identification/submit")
    Observable<Response>identificationsubmit(
            @Field("userId") String userId, //userId
            @Field("realName") String realname, //真是姓名
            @Field("idCardNum") String idcardnum, //身份证号
            @Field("aImage") String aimage, //身份证图片
            @Field("bImage") String bimage //身份证图片
    );

    //添加分组
    @FormUrlEncoded
    @POST("/urmFriendGroup/create")
    Observable<Response>createname(
            @Header("token") String token,
            @Field("name") String name
    );

    //删除分组
    @POST("/urmFriendGroup/delete/{groupId}")
    Observable<Response>delete(
            @Header("token") String token,
            @Path("groupId") int groupId
    );

    //更新分组
    @FormUrlEncoded
    @POST("/urmFriendGroup/update/{groupId}")
    Observable<Response>update(
            @Header("token") String token,
            @Path("groupId") int groupId,
            @Field("name") String name
    );

    //获取分组列表

    @GET("/urmFriendGroup/list")
    Observable<Response<List<UrmFriendGroup>>>urmFriendGrouplist(
            @Header("token") String token
    );

    //添加商友
    @FormUrlEncoded
    @POST("/urmUserFriend/add")
    Observable<Response>add1(
            @Header("token") String token,
            @Field("friendUserId") String friendUserId,
            @Field("groupId")  int groupId
    );

    @FormUrlEncoded
    @POST("/urmUserFriend/add")
    Observable<Response>add2(
            @Header("token") String token,
            @Field("groupId")  int groupId,
            @Field("telephone") String telephone,
            @Field("name") String name
    );

    //编辑商友
    @FormUrlEncoded
    @POST("/urmUserFriend/update/freind/{friendUserId}")
    Observable<Response>updatefreind(
            @Header("token") String token,
            @Path("friendUserId")  int friendUserId,
            @Field("groupId") int groupId,
            @Field("nickName") String nickName
    );

    //根据手机号查询用户基础信息
    @POST("/person/base/param/{telephoneOrCtgId}")
    Observable<Response<ParamTelephone>>param(
            @Path("telephoneOrCtgId") String telephoneOrCtgId
    );

    //获取所有商友
    @GET("/urmUserFriend/list")
    Observable<Response<List<BusinessFriend>>>urmUserFriendlist(
            @Header("token") String token
    );

    //查询修理厂等固定分组
    @GET("/urmUserFriend/list/{industry}")
    Observable<Response<List<BusinessFriend>>>urmUserFriendindustry(
            @Header("token") String token,
            @Path("industry") String industry
    );
    //删除商友
    @POST("/urmUserFriend/delete/freind/{friendUserId}")
    Observable<Response>deletefreind(
            @Header("token") String token,
            @Path("friendUserId") int friendUserId
    );
}
