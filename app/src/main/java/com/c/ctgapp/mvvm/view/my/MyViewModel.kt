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

class MyViewModel @Inject constructor(private val personalinfoRepo: PersonalinfoRepo) : BaseViewModel() {
    private val data: MutableLiveData<Response<PersonalInfo>> = MutableLiveData()
    fun getdata():MutableLiveData<Response<PersonalInfo>>{
        return data
    }


}