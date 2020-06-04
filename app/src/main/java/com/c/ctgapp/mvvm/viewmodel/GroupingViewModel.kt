package com.c.ctgapp.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c.ctgapp.mvvm.model.Externalcontact
import com.c.ctgapp.mvvm.model.Response
import com.c.ctgapp.mvvm.model.UrmFriendGroup
import com.c.ctgapp.retrofit.HttpHelper
import com.c.ctgapp.retrofit.Serviece
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.URLEncoder
import java.util.*

class GroupingViewModel :ViewModel(){
    private val responsedata: MutableLiveData<Response<*>> = MutableLiveData()
    private val urmFriendGroupdata: MutableLiveData<Response<List<UrmFriendGroup>>> = MutableLiveData()
    private val urmFriendGroupdeletedata: MutableLiveData<Response<*>> = MutableLiveData()
    private val urmFriendGroupdatedata: MutableLiveData<Response<*>> = MutableLiveData()

    fun getResponseData():MutableLiveData<Response<*>>{
        return  responsedata
    }
    fun getUrmFriendGroupdata():MutableLiveData<Response<List<UrmFriendGroup>>>{
        return urmFriendGroupdata
    }
    fun geturmFriendGroupdeletedata():MutableLiveData<Response<*>>{
        return urmFriendGroupdeletedata
    }
    fun geturmFriendGroupdatedata():MutableLiveData<Response<*>>{
        return urmFriendGroupdatedata
    }

    fun urmFriendGrouplist(token:String){
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val linkman = serviece.urmFriendGrouplist(token)
        linkman.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<Response<List<UrmFriendGroup>>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<List<UrmFriendGroup>>) {
                        urmFriendGroupdata.value = response
                    }
                })
    }

    fun createname(name:String,token: String){
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val createname = serviece.createname(token,name)
        Log.e("name",name)
        Log.e("token",token)
        createname.subscribeOn(Schedulers.io())
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

    fun deletegroup(groupId:Int,token: String){
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val deletegroup = serviece.delete(token,groupId)
        deletegroup.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<Response<*>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<*>) {
                        urmFriendGroupdeletedata.value = response
                    }
                })
    }

    fun updategroup(groupId:Int,token: String,name: String){
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val updategroup = serviece.update(token,groupId,name)
        updategroup.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<Response<*>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<*>) {
                        urmFriendGroupdatedata.value = response
                    }
                })
    }
}
