package com.c.ctgapp.mvvm.view

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.c.ctgapp.MainActivity
import com.c.ctgapp.R
import com.c.ctgapp.Tools.DialogUtils
import com.c.ctgapp.Tools.Utils
import com.c.ctgapp.databinding.ActivityLoginBinding
import com.c.ctgapp.mvvm.model.Response
import com.c.ctgapp.mvvm.model.User
import com.c.ctgapp.mvvm.viewmodel.UserViewModel
import com.pilot.common.utils.NetworkUtils
import kotlinx.android.synthetic.main.activity_login.*

@Suppress("DEPRECATION")
@SuppressLint("Registered")
class LoginActivity : AppCompatActivity() {
    private var activityLoginBinding: ActivityLoginBinding? = null
    private var userViewModel: UserViewModel? = null
    private var progressDialog: ProgressDialog? = null
    private var dialogUtils: DialogUtils? = null
    var type = 1
    private var tokentime: Long = 0
    private var nowtime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        dialogUtils = DialogUtils()
        userViewModel = ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory(application)
        ).get(UserViewModel::class.java)
        userViewModel!!.getdata().observe(this@LoginActivity, Observer { response: Response<*> ->
            if (response.status == 0) {
                Getverificationcode_btn.start()
                Toast.makeText(applicationContext, response.msg, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, response.msg, Toast.LENGTH_LONG).show()
            }
        })
        nowtime = Utils.getTimeStame().toLong()
        if (Utils.getShared2(applicationContext, "tokentime") != "") {
            tokentime = Utils.getShared2(applicationContext, "tokentime").toLong()
            if (nowtime - tokentime > 7200000) {
                Utils.setShare2(applicationContext, "type", "2")
                Toast.makeText(applicationContext, "登录信息已过期，请重新登录", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        userViewModel!!.login().observe(this@LoginActivity, Observer { userBeanResponse: Response<User> ->
            if (userBeanResponse.status == 0) {
                progressDialog!!.dismiss()
                //service.queryUserData(userBeanResponse);
                Utils.setShare2(applicationContext, "tokentime", Utils.getTimeStame())
                Utils.setShare2(applicationContext, "realName", userBeanResponse.data.realName)
                Utils.setShare2(applicationContext, "userId", userBeanResponse.data.userId.toString())
                Utils.setShare2(applicationContext, "ctgId", userBeanResponse.data.ctgId.toString())
                Utils.setShare2(applicationContext, "companyName", userBeanResponse.data.companyName.toString())
                Utils.setShare2(applicationContext, "token", userBeanResponse.data.token.toString())
                Utils.setShare2(applicationContext, "companyId", userBeanResponse.data.companyId.toString())
                val intent = Intent(applicationContext, CreateEnterpriseActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                progressDialog!!.dismiss()
                dialogUtils!!.showTwo(this@LoginActivity, "提示", userBeanResponse.msg)
            }
        })
        initUI()
    }

    private fun initUI() {
        Phonenumber_ed.onFocusChangeListener = View.OnFocusChangeListener { _: View?, hasFocus: Boolean ->
            if (hasFocus) { //获得焦制点
                linearLayout7.setBackgroundColor(resources.getColor(R.color.line4))
            } else { //失去知焦道点
                linearLayout7.setBackgroundColor(resources.getColor(R.color.line))
            }
        }
        VerificationCode_ed.onFocusChangeListener = View.OnFocusChangeListener { _: View?, hasFocus: Boolean ->
            if (hasFocus) { //获得焦制点
                linearLayout8.setBackgroundColor(resources.getColor(R.color.line4))
            } else { //失去知焦道点
                linearLayout8.setBackgroundColor(resources.getColor(R.color.line))
            }
        }
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
                    userViewModel!!.GetSMS(Phonenumber_ed.text.toString(), "1")
                } else {
                    Toast.makeText(applicationContext, "请检查您的网络是否连接", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //注册
    fun Registration(view: View?) {
        startActivity(Intent(applicationContext, AccountRegistrationActivity::class.java))
    }

    //忘记密码
    fun Forgetpassword(view: View?) {
        startActivity(Intent(applicationContext, ForgetPasswordActivity::class.java))
    }

    //登录
    fun Login(view: View?) {
        if (TextUtils.isEmpty(Phonenumber_ed.text.toString())) {
            Toast.makeText(applicationContext, "手机号码不能为空", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(VerificationCode_ed.text.toString())) {
            if (type == 1) {
                Toast.makeText(applicationContext, "验证码不能为空", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "密码不能为空", Toast.LENGTH_SHORT).show()
            }
        } else {
            progressDialog = ProgressDialog(this@LoginActivity,
                    R.style.AppTheme_Dark_Dialog)
            progressDialog!!.isIndeterminate = true
            progressDialog!!.setCanceledOnTouchOutside(false)
            progressDialog!!.setCancelable(true)
            progressDialog!!.setMessage("登陆中...")
            progressDialog!!.show()
            if (NetworkUtils.isConnected(applicationContext)) {
                userViewModel!!.Login(Phonenumber_ed.text.toString(), VerificationCode_ed.text.toString(), VerificationCode_ed.text.toString(), type)
            } else {
                Toast.makeText(applicationContext, "请检查您的网络是否连接", Toast.LENGTH_SHORT).show()
                progressDialog!!.dismiss()
            }
        }
    }

    fun Verificationcodelogin(view: View?) {
        if (type == 1) {
            Getverificationcode_btn.visibility = View.GONE
            Verificationcodelogin_tv.text = "验证码登录"
            VerificationCode_ed.hint = "请输入密码"
            Forgetpasswordtv.visibility = View.VISIBLE
            VerificationCode_ed.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(30))
            VerificationCode_ed.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            type = 2
        } else {
            if (type == 2) {
                Getverificationcode_btn.visibility = View.VISIBLE
                Verificationcodelogin_tv.text = "密码登录"
                VerificationCode_ed.hint = "请输入验证码"
                Forgetpasswordtv.visibility = View.GONE
                VerificationCode_ed.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(4))
                VerificationCode_ed.inputType = InputType.TYPE_CLASS_PHONE
                type = 1
            }
        }
    }
}