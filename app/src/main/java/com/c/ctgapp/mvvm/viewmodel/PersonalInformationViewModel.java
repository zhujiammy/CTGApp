package com.c.ctgapp.mvvm.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.c.ctgapp.Tools.AppExecutors;
import com.c.ctgapp.Tools.Utils;
import com.c.ctgapp.mvvm.model.PersonalInfo;
import com.c.ctgapp.mvvm.model.Response;
import com.c.ctgapp.mvvm.model.uploadImg;
import com.c.ctgapp.mvvm.repository.PersonalinfoRepo;
import com.c.ctgapp.retrofit.HttpHelper;
import com.c.ctgapp.retrofit.Serviece;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class PersonalInformationViewModel extends BaseViewModel {

    private MutableLiveData<Response> dataMutableLiveData;
    private MutableLiveData<Response<uploadImg>> dataupload;
    private LiveData<PersonalInfo> personalInfo;
    private PersonalinfoRepo personalinfoRepo;
    private AppExecutors executors;

    @Inject
    public PersonalInformationViewModel(PersonalinfoRepo personalinfoRepo) {
        dataMutableLiveData = new MutableLiveData<>();
        dataupload = new MutableLiveData<>();
        this.personalinfoRepo = personalinfoRepo;
        executors = new AppExecutors();
    }

    public MutableLiveData<Response> getdata(){
        return dataMutableLiveData;
    }
    public MutableLiveData<Response<uploadImg>> getuploaddata(){
        return dataupload;
    }

    public void init(int userid,String realname){
       if(this.personalInfo != null){
           return;
       }
           personalInfo = personalinfoRepo.getperson(userid,realname,executors);



    }

    public LiveData<PersonalInfo> getPersonalInfo(){
        return this.personalInfo;
    }

/*    //插入数据库
    private Response<PersonalInfo>inser(Response<PersonalInfo> response){
        executors.getDiskIO().execute(() -> {
            if(ctgApp.getAppDatabase().personalInfoDao().insertPersonal(response.getData())>0){
                //select(response.getData().realname,response);
                Log.e("TAG", "onCreate: "+"数据成功插入personalinfo表" );
            }else {
                Log.e("TAG", "onCreate: "+"数据插入失败" );
            }
        });
        return  response;
    }*/
/*
    //根据姓名查询个人信息
    public void select(String realname, Response<PersonalInfo> response){

        executors.getDiskIO().execute(() -> {
            if(ctgApp.getAppDatabase().personalInfoDao().getpersonalinfo(realname)!=null && ctgApp.getAppDatabase().personalInfoDao().getpersonalinfo(realname).realname.equals(realname) ){

                if(response.getData().file.equals(ctgApp.getAppDatabase().personalInfoDao().getpersonalinfo(realname).file)&&
                        response.getData().nickname.equals(ctgApp.getAppDatabase().personalInfoDao().getpersonalinfo(realname).nickname)&&
                        response.getData().work.equals(ctgApp.getAppDatabase().personalInfoDao().getpersonalinfo(realname).work)&&
                        response.getData().edulevel.equals(ctgApp.getAppDatabase().personalInfoDao().getpersonalinfo(realname).edulevel)){
                    Log.e("TAG", "select: "+"数据无更新" );
                    personalInfoMutableLiveData.postValue(ctgApp.getAppDatabase().personalInfoDao().getpersonalinfo(realname));


                }else {
                    if(ctgApp.getAppDatabase().personalInfoDao().getupdatepersonalinfo(realname,response.getData().file,response.getData().nickname,
                            response.getData().work,response.getData().edulevel)>0){
                        Log.e("TAG", "select: "+"数据有更新" );
                        personalInfoMutableLiveData.postValue(ctgApp.getAppDatabase().personalInfoDao().getpersonalinfo(realname));
                    }
                }

            }else {
                inser(response);
            }
        });
    }*/

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
                        personalinfoRepo.getRefresh(1);

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
