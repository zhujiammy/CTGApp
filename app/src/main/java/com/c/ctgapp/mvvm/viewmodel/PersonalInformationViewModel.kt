package com.c.ctgapp.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c.ctgapp.mvvm.model.Certificationinfo
import com.c.ctgapp.mvvm.model.PersonalInfo
import com.c.ctgapp.mvvm.model.Response
import com.c.ctgapp.mvvm.model.uploadImg
import com.c.ctgapp.mvvm.repository.PersonalinfoRepo
import com.c.ctgapp.retrofit.HttpHelper
import com.c.ctgapp.retrofit.Serviece
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import java.util.*
import javax.inject.Inject

class PersonalInformationViewModel @Inject constructor(private val personalinfoRepo: PersonalinfoRepo) : BaseViewModel() {
    private val dataMutableLiveData: MutableLiveData<Response<*>> = MutableLiveData()
    private val dataupload: MutableLiveData<Response<uploadImg>> = MutableLiveData()
    private val identificationdata: MutableLiveData<Response<Certificationinfo>> = MutableLiveData()
    private val personinfodata: MutableLiveData<Response<PersonalInfo>> = MutableLiveData()
    fun getdata(): MutableLiveData<Response<*>> {
        return dataMutableLiveData
    }

    fun getuploaddata(): MutableLiveData<Response<uploadImg>> {
        return dataupload
    }
    fun getidentificationdata(): MutableLiveData<Response<Certificationinfo>>{
        return identificationdata
    }

    fun getpersoninfo():MutableLiveData<Response<PersonalInfo>>{
        return personinfodata
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

    //获取实名认证信息
    fun identification(userid: String){
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val identification = serviece.identification(userid)
        identification?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<Response<Certificationinfo>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<Certificationinfo>) {
                        identificationdata.value = response
                    }
                })
    }

    fun PeronalInfo(userId: Int) {
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val userInfo = serviece.userInfo(userId)
        userInfo?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<Response<PersonalInfo>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<PersonalInfo>) {
                        personinfodata.value = response
                    }
                })
    }

//保存个人信息
    fun usersave(uid: String?, ctgId: String?, edulevel: String?, nickname: String?, file: String?, work: String?) {
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val usersave = serviece.usersave(uid, ctgId, edulevel, nickname, file, work)
        usersave.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Response<*>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }

                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<*>) {
                        dataMutableLiveData.value = response
                        personalinfoRepo.getRefresh(1)
                    }
                })
    }

    //上传文件
    fun uploadFile(part: MultipartBody.Part?) {
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val uploadFile = serviece.uploadFile(part)
        uploadFile.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Response<uploadImg>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }

                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(uploadImgResponse: Response<uploadImg>) {
                        dataupload.value = uploadImgResponse
                    }
                })
    }

}