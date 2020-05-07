package com.c.ctgapp.mvvm.viewmodel;

import android.util.Base64;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.c.ctgapp.mvvm.model.Response;
import com.c.ctgapp.mvvm.model.uploadImg;
import com.c.ctgapp.retrofit.HttpHelper;
import com.c.ctgapp.retrofit.Serviece;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class PersonalInformationViewModel extends ViewModel {
    private MutableLiveData<Response> dataMutableLiveData;
    private MutableLiveData<Response<uploadImg>> dataupload;

    public PersonalInformationViewModel(){
        dataMutableLiveData = new MutableLiveData<>();
        dataupload = new MutableLiveData<>();
    }

    public MutableLiveData<Response> getdata(){
        return dataMutableLiveData;
    }
    public MutableLiveData<Response<uploadImg>> getuploaddata(){
        return dataupload;
    }
    //保存个人信息
    @SuppressWarnings("unchecked")
    public void usersave(String ctgId, String edulevel, String nickname, String file,String work ){
        Serviece serviece = HttpHelper.getInstance().create(Serviece.class);
        Observable<Response> usersave = serviece.usersave(ctgId,edulevel,nickname,file,work);
        usersave.subscribeOn(Schedulers.io())
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
                        dataupload.setValue(response);
                    }
                });
    }


    //上传文件
    @SuppressWarnings("unchecked")
    public void uploadFile( MultipartBody.Part part ){
        Serviece serviece = HttpHelper.getInstance().create(Serviece.class);
        Observable<Response<uploadImg>> uploadFile = serviece.uploadFile(part);
        uploadFile.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<uploadImg>>() {
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
                    public void onNext(Response<uploadImg> uploadImgResponse) {
                        dataupload.setValue(uploadImgResponse);
                    }
                });
    }

}
