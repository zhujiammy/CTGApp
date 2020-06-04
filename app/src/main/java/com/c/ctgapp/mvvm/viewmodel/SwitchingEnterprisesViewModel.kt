package com.c.ctgapp.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c.ctgapp.mvvm.model.Company
import com.c.ctgapp.mvvm.model.Response
import com.c.ctgapp.retrofit.HttpHelper
import com.c.ctgapp.retrofit.Serviece
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class SwitchingEnterprisesViewModel:ViewModel() {
    private val responedata: MutableLiveData<Response<List<Company>>> = MutableLiveData()
    private val data: MutableLiveData<Response<*>> = MutableLiveData()

    fun getresponedata():MutableLiveData<Response<List<Company>>>{
        return responedata
    }
    fun getdata():MutableLiveData<Response<*>>{
        return  data
    }


    //获取用户所有企业
    fun userCompany(userid: Int){
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val userCompany = serviece.userCompany(userid)
        userCompany.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<Response<List<Company>>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<List<Company>>) {
                        responedata.value = response
                    }
                })
    }

    //设置默认企业
    fun companydefault(companyid:Int,userid: Int){
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val companydefault = serviece.companydefault(companyid,userid)
        companydefault.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<Response<*>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<*>) {
                        data.value = response
                    }
                })
    }
}