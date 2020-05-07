package com.c.ctgapp.mvvm.viewmodel;

import android.util.Base64;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.c.ctgapp.mvvm.model.Response;
import com.c.ctgapp.retrofit.HttpHelper;
import com.c.ctgapp.retrofit.Serviece;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AccountRegistrationViewmodel extends ViewModel {
    private MutableLiveData<Response> dataMutableLiveData;
    private MutableLiveData<Response> dataMutableLiveDatareslut;

    public AccountRegistrationViewmodel(){
        dataMutableLiveData = new MutableLiveData<>();
        dataMutableLiveDatareslut = new MutableLiveData<>();
    }
    public MutableLiveData<Response> getdata(){
        return dataMutableLiveData;
    }
    public MutableLiveData<Response> getdatareslut(){
        return dataMutableLiveDatareslut;
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

    //注册
    @SuppressWarnings("unchecked")
    public void register(String realName,String telephone,String password,String industry,String code ){
        Serviece serviece = HttpHelper.getInstance().create(Serviece.class);
        Observable<Response> register = serviece.register(realName,telephone, Base64.encodeToString(password.getBytes(), Base64.DEFAULT),industry,code);
        register.subscribeOn(Schedulers.io())
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
                        dataMutableLiveDatareslut.setValue(response);
                    }
                });
    }
}
