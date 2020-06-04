package com.c.ctgapp.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c.ctgapp.mvvm.model.Response
import com.c.ctgapp.mvvm.model.uploadImg
import com.c.ctgapp.retrofit.HttpHelper
import com.c.ctgapp.retrofit.Serviece
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import java.util.*

class PersonalAuthenticationViewmodel :  ViewModel() {
    private val dataMutableLiveData: MutableLiveData<Response<*>> = MutableLiveData()
    private val dataupload: MutableLiveData<Response<uploadImg>> = MutableLiveData()


    fun getdata(): MutableLiveData<Response<*>> {
        return dataMutableLiveData
    }

    fun getuploaddata(): MutableLiveData<Response<uploadImg>> {
        return dataupload
    }


    fun Realauthentication(userId: String?, realname: String?, idcardnum: String?, aimage: String?, bimage: String?) {
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val Realauthentication = serviece.identificationsubmit(userId, realname, idcardnum, aimage, bimage)
        Log.e("sss",realname)
        Log.e("sss",idcardnum)
        Log.e("sss",aimage)
        Log.e("sss",bimage)

        Realauthentication.subscribeOn(Schedulers.io())
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