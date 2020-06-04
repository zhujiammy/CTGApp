package com.c.ctgapp.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c.ctgapp.mvvm.model.ParamTelephone
import com.c.ctgapp.mvvm.model.Response
import com.c.ctgapp.retrofit.HttpHelper
import com.c.ctgapp.retrofit.Serviece
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class AddFriendsViewModel:ViewModel() {
    private val responsedata: MutableLiveData<Response<*>> = MutableLiveData()
    private val paramdata:MutableLiveData<Response<ParamTelephone>> = MutableLiveData()
    private val updatefreinddata:MutableLiveData<Response<*>> = MutableLiveData()

    fun getResponseData():MutableLiveData<Response<*>>{
        return  responsedata
    }
    fun getparamdata():MutableLiveData<Response<ParamTelephone>> {
        return paramdata
    }
    fun getupdatefreinddata():MutableLiveData<Response<*>>{
        return  responsedata
    }

    fun param(telephone: String){
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val param = serviece.param(telephone)
        param.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<Response<ParamTelephone>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<ParamTelephone>) {
                        paramdata.value = response
                    }
                })
    }

    fun add(type:Int,token:String,friendUserId: String,groupId:Int,telephone:String ,name:String){
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        if(type == 1){
            val add = serviece.add1(token,friendUserId,groupId)
            add.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : io.reactivex.Observer<Response<*>> {
                        override fun onError(e: Throwable) {
                            Log.d("错误信息", Objects.requireNonNull(e.message))
                            e.printStackTrace()
                        }
                        override fun onComplete() {}
                        override fun onSubscribe(d: Disposable) {}
                        override fun onNext(response: Response<*>) {
                            responsedata.value = response
                        }
                    })
        }else{
            val add = serviece.add2(token,groupId,telephone,name)
            add.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : io.reactivex.Observer<Response<*>> {
                        override fun onError(e: Throwable) {
                            Log.d("错误信息", Objects.requireNonNull(e.message))
                            e.printStackTrace()
                        }
                        override fun onComplete() {}
                        override fun onSubscribe(d: Disposable) {}
                        override fun onNext(response: Response<*>) {
                            responsedata.value = response
                        }
                    })
        }

    }


    fun updatefreind(token: String,friendUserId: Int,groupId:Int,nickName:String){
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val updatefreind = serviece.updatefreind(token,friendUserId,groupId,nickName)
        updatefreind.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<Response<*>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<*>) {
                        updatefreinddata.value = response
                    }
                })
    }
}