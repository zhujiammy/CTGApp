package com.c.ctgapp.mvvm.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.c.ctgapp.Tools.AppExecutors;
import com.c.ctgapp.mvvm.db.PersonalInfoDao;
import com.c.ctgapp.mvvm.model.PersonalInfo;
import com.c.ctgapp.mvvm.model.Response;
import com.c.ctgapp.retrofit.HttpHelper;
import com.c.ctgapp.retrofit.Serviece;

import java.util.Objects;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class PersonalinfoRepo {

    private Serviece serviece;
    private final PersonalInfoDao dao;
    private AppExecutors executors;
    private int i = 0;

    @Inject
    public PersonalinfoRepo(PersonalInfoDao dao){
        this.dao = dao;
    }

    public void refreshPeronalInfo(final int userId){
            new AppExecutors().getDiskIO().execute(() -> {
                // boolean PersonalinfoExistes = dao
                serviece = HttpHelper.getInstance().create(Serviece.class);
                Observable<Response<PersonalInfo>> userInfo = serviece.userInfo(userId);
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
                                dao.insertPersonal(response.getData());
                                if(i != 0){
                                    Log.e("TAG", "onNext: "+"刷新" );
                                    getupdatepersonalinfo(response.getData().realname,response.getData().file,response.getData().nickname,response.getData().work,response.getData().edulevel);
                                    i =0;
                                }else {
                                    Log.e("TAG", "onNext: "+"没刷新" );
                                }


                            }
                        });
            });



    }


    public LiveData<PersonalInfo> getperson(int userid,String realname,AppExecutors executors){
        //根据id获取用户个人信息
        this.executors = executors;
            refreshPeronalInfo(userid);
            return dao.getpersonalinfo(realname);
        }

        public void getupdatepersonalinfo(String realname,String file,String nickname,String work,String edulevel){
           dao.getupdatepersonalinfo(realname,file,nickname,work,edulevel);
        }

        public void getRefresh(int i){
            this.i = i;
        }

    }

