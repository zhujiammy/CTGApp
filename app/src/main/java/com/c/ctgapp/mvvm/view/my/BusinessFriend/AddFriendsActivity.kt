package com.c.ctgapp.mvvm.view.my.BusinessFriend

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import butterknife.BindView
import butterknife.ButterKnife
import com.c.ctgapp.R
import com.c.ctgapp.Tools.DialogUtils
import com.c.ctgapp.Tools.Utils
import com.c.ctgapp.mvvm.model.ParamTelephone
import com.c.ctgapp.mvvm.model.Response
import kotlinx.android.synthetic.main.title.*
import kotlinx.android.synthetic.main.activity_addfriends.*
import com.c.ctgapp.mvvm.view.my.BusinessFriend.MaillistActivitys
import com.c.ctgapp.mvvm.viewmodel.AddFriendsViewModel
import com.pilot.common.utils.NetworkUtils
import java.util.*

class AddFriendsActivity : AppCompatActivity(), View.OnClickListener {


    private var viewmodel: AddFriendsViewModel? = null
    private var progressDialog: ProgressDialog? = null
    private var dialogUtils: DialogUtils? = null
    private var groupId:String? = null
    private var userId:Int? = null
    private var realName:String? = null
    private var type:Int? = null
    private var code:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addfriends)
        ButterKnife.bind(this)
        code = intent.getStringExtra("code")
        if(code.equals("1")){
            toolbar_title!!.text = "添加商友"
        }else{
            toolbar_title!!.text = "编辑商友"
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        progressDialog = ProgressDialog(this@AddFriendsActivity,
                R.style.AppTheme_Dark_Dialog)
        progressDialog!!.isIndeterminate = true
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.setCancelable(true)
        viewmodel = ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory(application)
        ).get(AddFriendsViewModel::class.java)
        dialogUtils = DialogUtils()
        viewmodel!!.getResponseData().observe(this,Observer{ response: Response<*> ->
            if(response.status == 0){
                progressDialog!!.dismiss()
                val intent = Intent()
                intent.putExtra("success","1")
                setResult(Activity.RESULT_OK, intent)
                Toast.makeText(applicationContext, "添加成功！", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                progressDialog!!.dismiss()
                Toast.makeText(applicationContext, response.msg, Toast.LENGTH_SHORT).show()
            }
        })

        viewmodel!!.getupdatefreinddata().observe(this,Observer{ response: Response<*> ->
            if(response.status == 0){
                progressDialog!!.dismiss()
                val intent = Intent()
                intent.putExtra("success","1")
                setResult(Activity.RESULT_OK, intent)
                Toast.makeText(applicationContext, "修改成功！", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                progressDialog!!.dismiss()
                Toast.makeText(applicationContext, response.msg, Toast.LENGTH_SHORT).show()
            }
        })


        viewmodel!!.getparamdata().observe(this,Observer{response:Response<ParamTelephone>->
            if(response.status == 0){
                if(response.data !=null){
                    userId = response.data.userId
                    realName = response.data.realName
                    name_et.setText(realName)
                    type = 1
                }else{
                    type = 2
                    name_et.setText("")
                }

            }else{
                Toast.makeText(applicationContext, response.msg, Toast.LENGTH_SHORT).show()
            }
        })
        Event()
    }

    private fun Event() {
        Maillist_lin!!.setOnClickListener(this)
        relativeLayout4!!.setOnClickListener(this)
        createenterprise_btn!!.setOnClickListener(this)
        telephone_et!!.addTextChangedListener(addTextChangedListener)
    }

    var addTextChangedListener: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if(s.length == 11){
                viewmodel!!.param(s.toString())
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        if (v === Maillist_lin) {
            startActivity(Intent(this, MaillistActivitys::class.java))
        }
        if(v == relativeLayout4){
            startActivityForResult(Intent(this, GroupingActivity::class.java),1)
        }
        if(v == createenterprise_btn){
            if(TextUtils.isEmpty(telephone_et.text.toString())){
                Toast.makeText(applicationContext, "手机号码不能为空！", Toast.LENGTH_SHORT).show()
            }else  if(TextUtils.isEmpty(name_et.text.toString())){
                Toast.makeText(applicationContext, "姓名不能为空！", Toast.LENGTH_SHORT).show()
            }else  if(groupname_et.text.toString().equals("选择分组")){
                Toast.makeText(applicationContext, "请选择分组！", Toast.LENGTH_SHORT).show()
            }else {
                progressDialog!!.setMessage("保存中...")
                progressDialog!!.show()
                if(!NetworkUtils.isConnected(applicationContext)){
                    progressDialog!!.dismiss()
                    Toast.makeText(applicationContext, "请检查您的网络是否连接", Toast.LENGTH_SHORT).show()
                }else{
                    if(code.equals("1")){
                        viewmodel!!.add(type!!,Utils.getShared2(this,"token"),userId.toString(),groupId!!.toInt(),telephone_et.text.toString(),name_et.text.toString())
                    }else{
                        //viewmodel!!.updatefreind(Utils.getShared2(this,"token"),)
                    }

            }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val name = data?.getStringExtra("name")
                groupname_et.text = name
                groupId = data?.getStringExtra("groupId")
                Log.e("id",groupId)
            }
        }
    }


}