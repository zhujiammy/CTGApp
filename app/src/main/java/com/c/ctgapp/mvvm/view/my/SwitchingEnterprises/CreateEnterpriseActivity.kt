package com.c.ctgapp.mvvm.view.my.SwitchingEnterprises

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import butterknife.BindView
import butterknife.ButterKnife
import com.c.ctgapp.R
import com.c.ctgapp.Tools.DialogUtils
import com.c.ctgapp.Tools.Utils
import com.c.ctgapp.mvvm.model.Response
import com.c.ctgapp.mvvm.viewmodel.CreateEnterpriseViewModel
import com.lljjcoder.citypickerview.widget.CityPicker
import com.pilot.common.utils.NetworkUtils
import java.util.*
import kotlinx.android.synthetic.main.title.*
import kotlinx.android.synthetic.main.activity_createenterprise.*

@Suppress("DEPRECATION")
class CreateEnterpriseActivity : AppCompatActivity(),View.OnClickListener {

    private var viewmodel: CreateEnterpriseViewModel? = null
    private var progressDialog: ProgressDialog? = null
    private var dialogUtils: DialogUtils? = null
    private var userid: String? = null
    private var address:String? = null
    private var Province:String? = null
    private var City:String? = null
    private var District:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createenterprise)
        toolbar_title!!.text = "创建企业"
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        createenterprise_btn.setOnClickListener(this)
        select_tv.setOnClickListener(this)
        userid = Utils.getShared2(this, "userId")
        viewmodel = ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory(application)
        ).get(CreateEnterpriseViewModel::class.java)
        viewmodel!!.getdata().observe(this@CreateEnterpriseActivity, androidx.lifecycle.Observer { response: Response<*>->
            if (response.status == 0) {
                progressDialog!!.dismiss()
                Toast.makeText(applicationContext, response.msg, Toast.LENGTH_LONG).show()
                finish()
            } else {
                progressDialog!!.dismiss()
                dialogUtils!!.showTwo(this@CreateEnterpriseActivity, "提示", response.msg)
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        if(v == createenterprise_btn){
            if (TextUtils.isEmpty(industry_et.text.toString())) {
                Toast.makeText(applicationContext, "行业属性不能为空", Toast.LENGTH_SHORT).show()
            }else if (TextUtils.isEmpty(orgname_et.text.toString())) {
                Toast.makeText(applicationContext, "企业名称不能为空", Toast.LENGTH_SHORT).show()
            }else if (select_tv.text.toString().equals("请选择地址")) {
                Toast.makeText(applicationContext, "请选择省市区", Toast.LENGTH_SHORT).show()
            }else if (TextUtils.isEmpty(address_et.text.toString())) {
                Toast.makeText(applicationContext, "详细地址不能为空", Toast.LENGTH_SHORT).show()
            }else{
                progressDialog = ProgressDialog(this@CreateEnterpriseActivity,
                        R.style.AppTheme_Dark_Dialog)
                progressDialog!!.isIndeterminate = true
                progressDialog!!.setCanceledOnTouchOutside(false)
                progressDialog!!.setCancelable(true)
                progressDialog!!.setMessage("正在提交...")
                progressDialog!!.show()
                if (NetworkUtils.isConnected(applicationContext)) {
                    viewmodel!!.CompanySave(orgname_et.text.toString(),industry_et.text.toString(),Province.toString(),City.toString(),District.toString(),address_et.text.toString(), userid.toString(),
                            profile_et.text.toString(),companyScale_et.text.toString(),employeeScale_et.text.toString(),email_et.text.toString(),netaddress_et.text.toString(),phone_et.text.toString())
                } else {
                    Toast.makeText(applicationContext, "请检查您的网络是否连接", Toast.LENGTH_SHORT).show()
                    progressDialog!!.dismiss()
                }
            }
        }
        if(v == select_tv){
            //选择地址
            selectAddress()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun selectAddress(){
        val cityPicker = CityPicker.Builder(this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .titleTextColor("#696969")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("北京市")
                .city("北京市")
                .district("朝阳区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build()
        cityPicker.show()
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(object : CityPicker.OnCityItemClickListener {
            override fun onSelected(vararg citySelected: String) { //省份
                val province = citySelected[0]
                //城市
                val city = citySelected[1]
                //区县（如果设定了两级联动，那么该项返回空）
                val district = citySelected[2]
                //邮编
                val code = citySelected[3]
                //为TextView赋值
                select_tv.setText(province.trim { it <= ' ' } + "-" + city.trim { it <= ' ' } + "-" + district.trim { it <= ' ' })
                address = province.trim { it <= ' ' } + "" + city.trim { it <= ' ' } + "" + district.trim { it <= ' ' }
                Province = province
                City = city
                District = district
            }

            override fun onCancel() {}
        })
    }


}