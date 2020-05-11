package com.c.ctgapp.mvvm.viewmodel

import android.util.Base64
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c.ctgapp.mvvm.model.Response
import com.c.ctgapp.mvvm.model.User
import com.c.ctgapp.retrofit.HttpHelper
import com.c.ctgapp.retrofit.Serviece
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class UserViewModel : ViewModel() {
    private val userMutableLiveData: MutableLiveData<Response<User>> = MutableLiveData()
    private val dataMutableLiveData: MutableLiveData<Response<*>> = MutableLiveData()

    fun login(): MutableLiveData<Response<User>> {
        return userMutableLiveData
    }

    fun getdata(): MutableLiveData<Response<*>> {
        return dataMutableLiveData
    }

    //登录
    fun Login(telephone: String?, password: String, code: String?, type: Int) {
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val login: Observable<Response<User>>
        login = if (type == 1) {
            serviece.login(telephone, code)
        } else {
            Log.e("password", "password: " + Base64.encodeToString(password.toByteArray(), Base64.DEFAULT))
            serviece.loginpass(telephone, Base64.encodeToString(password.toByteArray(), Base64.DEFAULT))
        }
        login.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Response<User>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }

                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(userBeanResponse: Response<User>) {
                        userMutableLiveData.setValue(userBeanResponse)
                    }
                })
    }

    //获取验证码
    fun GetSMS(telephone: String?, type: String?) {
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val sms = serviece.getsms(telephone, type)
        sms.subscribeOn(Schedulers.io())
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
                    }
                })
    }

}