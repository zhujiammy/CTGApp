@file:Suppress("DEPRECATION")

package com.c.ctgapp.mvvm.view.my.SwitchingEnterprises

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.c.ctgapp.Adapter.SwitchingEnterprisesAdapter
import com.c.ctgapp.R
import com.c.ctgapp.Tools.DialogUtils
import com.c.ctgapp.Tools.Utils
import com.c.ctgapp.mvvm.model.Company
import com.c.ctgapp.mvvm.model.Response
import com.c.ctgapp.mvvm.viewmodel.SwitchingEnterprisesViewModel
import com.pilot.common.utils.NetworkUtils
import kotlinx.android.synthetic.main.title.*
import kotlinx.android.synthetic.main.activity_switchingenterprises.*

//企业切换
@SuppressLint("Registered")
class SwitchingEnterprisesActivity : AppCompatActivity(), View.OnClickListener {

    private var progressDialog: ProgressDialog? = null

    private var dialogUtils: DialogUtils? = null

    private var viewmodel:SwitchingEnterprisesViewModel? = null

    private var adapter: SwitchingEnterprisesAdapter? = null

    private var mLinearLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switchingenterprises)
        toolbar_title!!.text = "切换企业"
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        createenterprise_btn.setOnClickListener(this)
        progressDialog = ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog)
        progressDialog!!.isIndeterminate = true
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.setCancelable(true)
        progressDialog!!.setMessage("正在加载...")
        progressDialog!!.show()
        viewmodel = ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory(application)
        ).get(SwitchingEnterprisesViewModel::class.java)

        if (NetworkUtils.isConnected(applicationContext)) {
            viewmodel!!.userCompany(Utils.getShared2(this@SwitchingEnterprisesActivity, "userId").toInt())
        }else{
            Toast.makeText(applicationContext, "请检查您的网络是否连接", Toast.LENGTH_SHORT).show()
            progressDialog!!.dismiss()
        }
        viewmodel!!.getresponedata().observe(this, androidx.lifecycle.Observer {response:Response<List<Company>>->
            if(response.status == 0){
                adapter = SwitchingEnterprisesAdapter(response.data,this)
                mLinearLayoutManager = LinearLayoutManager(this)
                recyclerView!!.layoutManager = mLinearLayoutManager
                recyclerView!!.adapter = adapter
                progressDialog!!.dismiss()
                adapter?.setOnItemClickListener(object : SwitchingEnterprisesAdapter.OnItemClickListener{
                    override fun onItemClick(view: View, position: Int) {
                        Log.e("cid",""+response.data[position].id)
                        Log.e("userid",""+response.data[position].createUserId)
                        viewmodel!!.companydefault(response.data[position].id,response.data[position].createUserId)

                    }
                })
            }else{
                progressDialog!!.dismiss()
                dialogUtils!!.showTwo(this@SwitchingEnterprisesActivity, "提示", response.msg)
            }
        })

        viewmodel!!.getdata().observe(this,Observer{response: Response<*>->
            if(response.status == 0){
                Toast.makeText(applicationContext, "切换成功", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                dialogUtils!!.showTwo(this@SwitchingEnterprisesActivity, "提示", response.msg)
            }
        })
        Event()


    }

    private fun Event() {
        createenterprise_btn!!.setOnClickListener(this)
        Applicationaccession_btn!!.setOnClickListener(this)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        if (v === createenterprise_btn) { //创建企业
            startActivity(Intent(this, CreateEnterpriseActivity::class.java))
        }
        if (v === Applicationaccession_btn) { //创建企业
            startActivity(Intent(this, ApplicationAccessionActivity::class.java))
        }
    }


}


