package com.c.ctgapp.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.c.ctgapp.mvvm.model.Response
import com.c.ctgapp.retrofit.HttpHelper
import com.c.ctgapp.retrofit.Serviece
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class CreateEnterpriseViewModel:ViewModel() {
    private val responedata: MutableLiveData<Response<*>> = MutableLiveData()

    fun getdata():MutableLiveData<Response<*>>{
        return  responedata
    }

    fun CompanySave(orgname:String,industry:String,province:String,city:String,district:String,address:String,createUserid:String,profile:String,companyScale:String,
                    employeeScale:String,email:String,netaddress:String,phone:String){
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val companysave = serviece.companysave(orgname,industry,province,city,district,address,createUserid,profile,companyScale,employeeScale,email,netaddress,phone)
        companysave.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<Response<*>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<*>) {
                        responedata.value = response
                    }
                })

    }
}