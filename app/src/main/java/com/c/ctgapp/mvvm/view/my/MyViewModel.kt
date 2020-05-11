package com.c.ctgapp.mvvm.view.my

import androidx.lifecycle.LiveData
import com.c.ctgapp.mvvm.model.PersonalInfo
import com.c.ctgapp.mvvm.repository.PersonalinfoRepo
import com.c.ctgapp.mvvm.viewmodel.BaseViewModel
import javax.inject.Inject

class MyViewModel @Inject constructor(private val personalinfoRepo: PersonalinfoRepo) : BaseViewModel() {
    private var personinfo: LiveData<PersonalInfo>? = null
    //获取头像等信息
    fun getuersinfo(userid: Int, realname: String) {
        if (personinfo != null) {
            return
        }
        personinfo = personalinfoRepo.getperson(userid, realname)
    }

    fun getuserinfodata(): LiveData<PersonalInfo>? {
        return personinfo
    }

}