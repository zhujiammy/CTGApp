package com.c.ctgapp.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c.ctgapp.mvvm.model.*
import com.c.ctgapp.retrofit.HttpHelper
import com.c.ctgapp.retrofit.Serviece
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import java.util.*

class MyBusinessViewModel: ViewModel() {
    private val responedata: MutableLiveData<Response<MyBusiness>> = MutableLiveData()
    private val externalcontactdata: MutableLiveData<Response<List<Externalcontact>>> = MutableLiveData()
    private val deliveryAddressdata: MutableLiveData<Response<List<ReceivingAddress>>> = MutableLiveData()
    private val Addresssavedata: MutableLiveData<Response<*>> = MutableLiveData()
    private val honorsavedata: MutableLiveData<Response<*>> = MutableLiveData()
    private val honorupdata: MutableLiveData<Response<*>> = MutableLiveData()
    private val dataupload: MutableLiveData<Response<uploadImg>> = MutableLiveData()
    private val honorlistdata: MutableLiveData<Response<Honor>> = MutableLiveData()
    private val honorlistdataMore: MutableLiveData<Response<Honor>> = MutableLiveData()


    fun gethonorlistdata(): MutableLiveData<Response<Honor>>{
        return  honorlistdata
    }
    fun getgethonorMorelistdata():MutableLiveData<Response<Honor>>{
        return  honorlistdataMore
    }
    fun getuploaddata(): MutableLiveData<Response<uploadImg>> {
        return dataupload
    }
    fun gethonorupdata():MutableLiveData<Response<*>>{
        return  honorupdata
    }
    fun gethonorsave(): MutableLiveData<Response<*>>{
        return  honorsavedata
    }
    fun getresponedata():MutableLiveData<Response<MyBusiness>>{
        return responedata
    }
    fun getexternalcontactdata():MutableLiveData<Response<List<Externalcontact>>>{
        return externalcontactdata
    }
    fun getdeliveryAddressdata():MutableLiveData<Response<List<ReceivingAddress>>>{
        return  deliveryAddressdata
    }
    fun getAddresssavedata():MutableLiveData<Response<*>>{
        return  Addresssavedata
    }

    //获取企业信息详情
    fun Getmybusinessdata(companyId: Int){
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val getmybusinessdata = serviece.mycompany(companyId)
        getmybusinessdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<Response<MyBusiness>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<MyBusiness>) {
                        responedata.value = response
                    }
                })
    }


    //获取企业对外联系人
    fun linkman(companyId: Int){
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val linkman = serviece.linkman(companyId)
        linkman.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<Response<List<Externalcontact>>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<List<Externalcontact>>) {
                        externalcontactdata.value = response
                    }
                })
    }

    //获取收货地址
    fun deliveryAddress(companyId: Int){
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val deliveryAddress = serviece.deliveryAddress(companyId)
        deliveryAddress.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<Response<List<ReceivingAddress>>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<List<ReceivingAddress>>) {
                        deliveryAddressdata.value = response
                    }
                })
    }

    //更新收货地址

    fun Addresssave(id:String,province:String,city:String,district:String,detailAddress:String,deliveryName:String,deliveryPhone:String,companyId:String){
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val Addresssave = serviece.Addresssave(id,province,city,district,detailAddress,deliveryName,deliveryPhone,companyId)
        Addresssave.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<Response<*>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<*>) {
                        Addresssavedata.value = response
                    }
                })
    }

    //保存资质
    fun Honorsavedata(companyId:String,fileName:String,name:String,remark:String){
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val honorsave = serviece.honorsave(companyId,fileName,name,remark)
        honorsave.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<Response<*>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<*>) {
                        honorsavedata.value = response
                    }
                })
    }

    //更新资质
    fun Honorupdate(id:String,fileName:String,name:String,remark:String){
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val honorupdate = serviece.honorupdate(id,fileName,name,remark)
        honorupdate.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<Response<*>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<*>) {
                        honorupdata.value = response
                    }
                })
    }

    //获取资质列表
    fun Honorlistdata(companyId:String,pageNum:Int,pageSize:Int,type:Int){
        Log.e("pagenum",""+pageNum)
        val serviece = HttpHelper.getInstance().create(Serviece::class.java)
        val honorlist = serviece.honorlist(companyId,pageNum,pageSize)
        honorlist.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.Observer<Response<Honor>> {
                    override fun onError(e: Throwable) {
                        Log.d("错误信息", Objects.requireNonNull(e.message))
                        e.printStackTrace()
                    }
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(response: Response<Honor>) {
                        if(type == 1){
                            honorlistdata.value = response
                        }else{
                            honorlistdataMore.value = response
                        }

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