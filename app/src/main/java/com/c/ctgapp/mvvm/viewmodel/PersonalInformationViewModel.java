package com.c.ctgapp.mvvm.viewmodel;

import android.app.Application;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.c.ctgapp.CTGApp;
import com.c.ctgapp.Tools.AppExecutors;
import com.c.ctgapp.dao.PersonalInfoDao;
import com.c.ctgapp.mvvm.model.PersonalInfo;
import com.c.ctgapp.mvvm.model.Response;
import com.c.ctgapp.mvvm.model.uploadImg;
import com.c.ctgapp.retrofit.HttpHelper;
import com.c.ctgapp.retrofit.Serviece;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class PersonalInformationViewModel extends AndroidViewModel {
    private PersonalInfoDao dao;
    private CTGApp ctgApp;
    private AppExecutors executors;
    private MutableLiveData<Response> dataMutableLiveData;
    private MutableLiveData<Response<uploadImg>> dataupload;
    private MutableLiveData<Response<PersonalInfo>> responsepersonalinfo;
    private MutableLiveData<PersonalInfo> personalInfoMutableLiveData;

    public PersonalInformationViewModel(@NonNull Application application){
        super(application);
        ctgApp = (CTGApp)application;
        executors = new AppExecutors();
        dao = ctgApp.getAppDatabase().personalInfoDao();
        dataMutableLiveData = new MutableLiveData<>();
        dataupload = new MutableLiveData<>();
        responsepersonalinfo = new MutableLiveData<>();
        personalInfoMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Response> getdata(){
        return dataMutableLiveData;
    }
    public MutableLiveData<Response<uploadImg>> getuploaddata(){
        return dataupload;
    }
    public MutableLiveData<Response<PersonalInfo>> getResponsepersonalinfo(){
        return  responsepersonalinfo;
    }
    public MutableLiveData<PersonalInfo> getPersonalInfoMutableLiveData(){
        return personalInfoMutableLiveData;
    }

    //插入数据库
    private Response<PersonalInfo>inser(Response<PersonalInfo> response){
        executors.getDiskIO().execute(() -> {
            if(ctgApp.getAppDatabase().personalInfoDao().insertPersonal(response.getData())>0){
                select(response.getData().realname,response);
                Log.e("TAG", "onCreate: "+"数据成功插入personalinfo表" );
            }else {
                Log.e("TAG", "onCreate: "+"数据插入失败" );
            }
        });
        return  response;
    }

    //根据姓名查询个人信息
    public void select(String realname,Response<PersonalInfo> response){
        executors.getDiskIO().execute(() -> {
            if(ctgApp.getAppDatabase().personalInfoDao().getpersonalinfo(realname)!=null && ctgApp.getAppDatabase().personalInfoDao().getpersonalinfo(realname).realname.equals(realname) ){
                personalInfoMutableLiveData.setValue(ctgApp.getAppDatabase().personalInfoDao().getpersonalinfo(realname));
            }else {
               inser(response);
            }
        });
    }

    //保存个人信息
    @SuppressWarnings("unchecked")
    public void usersave(String uid,String ctgId, String edulevel, String nickname, String file,String work ){
        Serviece serviece = HttpHelper.getInstance().create(Serviece.class);
        Observable<Response> usersave = serviece.usersave(uid,ctgId,edulevel,nickname,file,work);
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
                        dataMutableLiveData.setValue(response);
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

    //加载个人信息
    //保存个人信息
    @SuppressWarnings("unchecked")
    public void userInfo(String uid){
        Serviece serviece = HttpHelper.getInstance().create(Serviece.class);
        Observable<Response<PersonalInfo>> userInfo = serviece.userInfo(uid);
        userInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<PersonalInfo>>() {
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
                    public void onNext(Response<PersonalInfo> response) {
                        select(response.getData().realname,response);
                        responsepersonalinfo.setValue(response);
                    }
                });
    }

}
