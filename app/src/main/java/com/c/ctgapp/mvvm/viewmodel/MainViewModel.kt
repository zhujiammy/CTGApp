package com.c.ctgapp.mvvm.view.my

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c.ctgapp.mvvm.model.PersonalInfo
import com.c.ctgapp.mvvm.model.Response
import com.c.ctgapp.mvvm.repository.PersonalinfoRepo
import com.c.ctgapp.mvvm.viewmodel.BaseViewModel
import com.c.ctgapp.retrofit.HttpHelper
import com.c.ctgapp.retrofit.Serviece
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class MainViewModel @Inject constructor(private val personalinfoRepo: PersonalinfoRepo) : BaseViewModel() {
    private val data: MutableLiveData<Response<PersonalInfo>> = MutableLiveData()
    fun getdata():MutableLiveData<Response<PersonalInfo>>{
        return data
    }

    fun PeronalInfo(userId: Int) {
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val userInfo = serviece.userInfo(userId)
        userInfo?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<Response<PersonalInfo>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息",e.message)
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<PersonalInfo>) {
                        data.value = response
                    }
                })

    }



}