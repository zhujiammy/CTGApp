package com.c.ctgapp.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c.ctgapp.mvvm.model.BusinessFriend
import com.c.ctgapp.mvvm.model.ParamTelephone
import com.c.ctgapp.mvvm.model.Response
import com.c.ctgapp.retrofit.HttpHelper
import com.c.ctgapp.retrofit.Serviece
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class BusinessFriendViewModel:ViewModel() {
    private val allresponsedata: MutableLiveData<Response<List<BusinessFriend>>> = MutableLiveData()
    private val industryresponsedata: MutableLiveData<Response<List<BusinessFriend>>> = MutableLiveData()
    private val deletefreinddata: MutableLiveData<Response<*>> = MutableLiveData()
    fun getallresponsedata():MutableLiveData<Response<List<BusinessFriend>>>{
        return  allresponsedata
    }
    fun getindustryresponsedata():MutableLiveData<Response<List<BusinessFriend>>>{
        return  industryresponsedata
    }
    fun getdeletefreinddata():MutableLiveData<Response<*>>{
        return  deletefreinddata
    }

    fun urmUserFriendlist(token:String){
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val urmUserFriendlist = serviece.urmUserFriendlist(token)
        urmUserFriendlist.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<Response<List<BusinessFriend>>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<List<BusinessFriend>>) {
                        allresponsedata.value = response
                    }
                })
    }

    fun urmUserFriendindustry(token: String,industry:String){
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val urmUserFriendindustry = serviece.urmUserFriendindustry(token,industry)
        urmUserFriendindustry.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<Response<List<BusinessFriend>>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<List<BusinessFriend>>) {
                        industryresponsedata.value = response
                    }
                })
    }

    //删除商友
    fun deletefreind(token: String,friendUserId:Int){
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val deletefreind = serviece.deletefreind(token,friendUserId)
        deletefreind.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<Response<*>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<*>) {
                        deletefreinddata.value = response
                    }
                })
    }
}