package com.c.ctgapp.mvvm.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.c.ctgapp.mvvm.model.Response;
import com.c.ctgapp.mvvm.model.User;
import com.c.ctgapp.retrofit.HttpHelper;
import com.c.ctgapp.retrofit.Serviece;
import com.google.gson.Gson;

import java.util.Map;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class UserViewModel extends ViewModel {
    private MutableLiveData<Response<User.UserBean>> userMutableLiveData;
    private MutableLiveData<Response> dataMutableLiveData;
    public UserViewModel(){
        userMutableLiveData = new MutableLiveData<>();
        dataMutableLiveData = new MutableLiveData<>();
    }
    public MutableLiveData<Response<User.UserBean>> login() {
        return userMutableLiveData;
    }
    public MutableLiveData<Response> getdata(){
        return dataMutableLiveData;
    }
    //登录
   @SuppressWarnings("unchecked")
    public void Login(String telephone,String password,String code,int type){
       Serviece serviece = HttpHelper.getInstance().create(Serviece.class);
       Observable<Response<User.UserBean>> login = null;
       if(type == 1){
           login = serviece.login(telephone,code);
       }else {
           login = serviece.loginpass(telephone,password);
       }
       login.subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<Response<User.UserBean>>() {
                   @Override
                   public void onError(Throwable e) {
                       Log.d("错误信息", Objects.requireNonNull(e.getMessage()));
                       e.printStackTrace();
                   }
                   @Override
                   public void onComplete() {

                   }
                   @Override
                   public void onSubscribe(Disposable d) {

                   }
                   @Override
                   public void onNext(Response<User.UserBean> userBeanResponse) {
                       userMutableLiveData.setValue(userBeanResponse);
                   }
               });
    }
    //获取验证码
    @SuppressWarnings("unchecked")
    public void GetSMS(String telephone,String type){
        Serviece serviece = HttpHelper.getInstance().create(Serviece.class);
        Observable<Response> sms = serviece.getsms(telephone,type);
        sms.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onError(Throwable e) {
                        Log.d("错误信息", Objects.requireNonNull(e.getMessage()));
                        e.printStackTrace();
                    }
                    @Override
                    public void onComplete() {
                    }
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(Response response) {
                       dataMutableLiveData.setValue(response);
                    }
                });
    }
}
