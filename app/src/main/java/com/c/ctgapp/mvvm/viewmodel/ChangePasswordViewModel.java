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

public class ChangePasswordViewModel extends ViewModel {
    private MutableLiveData<Response> dataMutableLiveData;

    public ChangePasswordViewModel(){
        dataMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Response> getdata(){
        return dataMutableLiveData;
    }


    //修改密码
    @SuppressWarnings("unchecked")
    public void updatePassword(String currentUserId,String oldPassword,String newPassword){
        Serviece serviece = HttpHelper.getInstance().create(Serviece.class);
        Observable<Response> updatePassword = serviece.updatePassword(currentUserId, Base64.encodeToString(oldPassword.getBytes(), Base64.DEFAULT),Base64.encodeToString(newPassword.getBytes(), Base64.DEFAULT));
        updatePassword.subscribeOn(Schedulers.io())
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
