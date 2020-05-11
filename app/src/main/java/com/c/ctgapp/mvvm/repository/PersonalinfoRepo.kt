package com.c.ctgapp.mvvm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.c.ctgapp.Tools.AppExecutors
import com.c.ctgapp.mvvm.db.PersonalInfoDao
import com.c.ctgapp.mvvm.model.PersonalInfo
import com.c.ctgapp.mvvm.model.Response
import com.c.ctgapp.retrofit.HttpHelper
import com.c.ctgapp.retrofit.Serviece
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonalinfoRepo @Inject constructor(private val dao: PersonalInfoDao, private val executors: AppExecutors) {
    private var serviece: Serviece? = null
    private var i = 0
    fun refreshPeronalInfo(userId: Int,realname: String?) {
            serviece = HttpHelper.getInstance().create(Serviece::class.java)
            val userInfo = serviece?.userInfo(userId)
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
                            executors.diskIO.execute {
                                //数据库操作不能在主线程
                                dao.insertPersonal(response.data)
                                if (i != 0) {
                                    Log.e("TAG", "onNext: " + "刷新")
                                    getupdatepersonalinfo(response.data.realname, response.data.file, response.data.nickname, response.data.work, response.data.edulevel)
                                    i = 0
                                } else {
                                    Log.e("TAG", "onNext: " + "没刷新")
                                }
                            }

                        }
                    })

    }

    fun getperson(userid: Int, realname: String): LiveData<PersonalInfo> { //根据id获取用户个人信息
        refreshPeronalInfo(userid,realname)

        return dao.getpersonalinfo(realname)
    }

    fun getupdatepersonalinfo(realname: String, file: String, nickname: String, work: String, edulevel: String) {
        dao.getupdatepersonalinfo(realname, file, nickname, work, edulevel)
    }

    fun getRefresh(i: Int) {
        this.i = i
    }

}