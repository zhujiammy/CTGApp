package com.c.ctgapp.mvvm.view

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.c.ctgapp.R
import com.c.ctgapp.Tools.DialogUtils
import com.c.ctgapp.databinding.AccountRegistrationBinding
import com.c.ctgapp.mvvm.model.Response
import com.c.ctgapp.mvvm.viewmodel.AccountRegistrationViewmodel
import com.pilot.common.utils.NetworkUtils
import java.util.*
import kotlinx.android.synthetic.main.title.*
import kotlinx.android.synthetic.main.account_registration.*

class AccountRegistrationActivity : AppCompatActivity() {
    private var model: AccountRegistrationViewmodel? = null
    private var progressDialog: ProgressDialog? = null
    private var dialogUtils: DialogUtils? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_registration)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        dialogUtils = DialogUtils()
        model = ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory(application)
        ).get(AccountRegistrationViewmodel::class.java)
        model!!.getdata().observe(this@AccountRegistrationActivity, Observer { response: Response<*> ->
            if (response.status == 0) {
                Getverificationcode_btn.start()
                Toast.makeText(applicationContext, response.msg, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, response.msg, Toast.LENGTH_LONG).show()
            }
        })
        model!!.getdatareslut().observe(this@AccountRegistrationActivity, Observer { response: Response<*> ->
            if (response.status == 0) {
                progressDialog!!.dismiss()
                Toast.makeText(applicationContext, response.msg, Toast.LENGTH_LONG).show()
                finish()
            } else {
                progressDialog!!.dismiss()
                dialogUtils!!.showTwo(this@AccountRegistrationActivity, "提示", response.msg)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Getverificationcode_btn.isFinish) {
            Getverificationcode_btn.cancel()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    //获取验证码
    fun getCode(view: View?) {
        if (TextUtils.isEmpty(Phonenumber_ed.text.toString())) {
            Toast.makeText(applicationContext, "手机号码不能为空", Toast.LENGTH_SHORT).show()
        } else {
            if (Getverificationcode_btn.isFinish) {
                if (NetworkUtils.isConnected(applicationContext)) {
                    model!!.GetSMS(Phonenumber_ed.text.toString(), "4")
                } else {
                    Toast.makeText(applicationContext, "请检查您的网络是否连接", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun Register(view: View?) {
        if (TextUtils.isEmpty(realName_ed.text.toString())) {
            Toast.makeText(applicationContext, "真实姓名不能为空！", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(Phonenumber_ed.text.toString())) {
            Toast.makeText(applicationContext, "手机号码不能为空！", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(VerificationCode_ed.text.toString())) {
            Toast.makeText(applicationContext, "验证码不能为空！", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(password_ed.text.toString())) {
            Toast.makeText(applicationContext, "密码不能为空！", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(confirmpassword.text.toString())) {
            Toast.makeText(applicationContext, "确认密码不能为空！", Toast.LENGTH_SHORT).show()
        } else if (Phonenumber_ed.text.toString() != confirmpassword.text.toString()) {
            Toast.makeText(applicationContext, "两次密码输入不一致！", Toast.LENGTH_SHORT).show()
        } else {
            progressDialog = ProgressDialog(this@AccountRegistrationActivity,
                    R.style.AppTheme_Dark_Dialog)
            progressDialog!!.isIndeterminate = true
            progressDialog!!.setCanceledOnTouchOutside(false)
            progressDialog!!.setCancelable(true)
            progressDialog!!.setMessage("正在提交...")
            progressDialog!!.show()
            if (NetworkUtils.isConnected(applicationContext)) {
                model!!.register(realName_ed.text.toString(), Phonenumber_ed.text.toString(), password_ed.text.toString(), null, VerificationCode_ed.text.toString())
            } else {
                Toast.makeText(applicationContext, "请检查您的网络是否连接", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}