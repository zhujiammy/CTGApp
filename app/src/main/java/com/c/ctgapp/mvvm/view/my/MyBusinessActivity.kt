package com.c.ctgapp.mvvm.view.my

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.c.ctgapp.Adapter.ContactsAdatper
import com.c.ctgapp.Adapter.HonoraryqualificationAdapter
import com.c.ctgapp.R
import com.c.ctgapp.Tools.DialogUtils
import com.c.ctgapp.Tools.MyBottomSheetDialog
import com.c.ctgapp.Tools.RecyclerViewEmptySupport
import com.c.ctgapp.Tools.Utils
import com.c.ctgapp.mvvm.model.*
import com.c.ctgapp.mvvm.viewmodel.MyBusinessViewModel
import com.google.android.material.appbar.AppBarLayout
import com.lljjcoder.citypickerview.widget.CityPicker
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.pilot.common.utils.NetworkUtils
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_mybusiness.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class MyBusinessActivity : AppCompatActivity(), View.OnClickListener {
    var dialog: MyBottomSheetDialog? = null
    private var adapter: ContactsAdatper? = null
    private var honoraryqualificationAdapter: HonoraryqualificationAdapter? = null
    private var viewmodel: MyBusinessViewModel? = null
    private var progressDialog: ProgressDialog? = null
    private var dialogUtils: DialogUtils? = null
    private var externalcontactlist:List<Externalcontact>? = null
    private var responsedata:Honor? = null
    private var selectvstr:String? = null
    private var address:String? = null
    private var Province:String? = null
    private var City:String? = null
    private var District:String? = null
    private var name:String? = null
    private var telephone:String? = null
    private var Id:Int? = null
    private var Select_et:TextView?=null
    private var Addqualification_iv:ImageView?=null
    private var fileName: String? = null
    private var ivpath: String? = null
    private var dialog1:AlertDialog? = null
    private var pagenum = 1
    private var pagesize = 10
    private var refreshLayout: SmartRefreshLayout? = null
    private var type: Int? = 1
    private var Qualificationname:String? = null
    private var remarks:String? = null
    private var Qualificationid:String? = null
    private var isMore:Boolean? = null
    private var num = 0
    private var profile:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mybusiness)
        dialogUtils = DialogUtils()
        viewmodel = ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory(application)
        ).get(MyBusinessViewModel::class.java)
        progressDialog = ProgressDialog(this@MyBusinessActivity,
                R.style.AppTheme_Dark_Dialog)
        progressDialog!!.isIndeterminate = true
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.setCancelable(true)
        progressDialog!!.setMessage("加载中...")
        progressDialog!!.show()
        if (NetworkUtils.isConnected(applicationContext)) {
            viewmodel!!.Getmybusinessdata(Utils.getShared2(this@MyBusinessActivity, "companyId").toInt())
            viewmodel!!.linkman(Utils.getShared2(this@MyBusinessActivity, "companyId").toInt())
            viewmodel!!.deliveryAddress(Utils.getShared2(this@MyBusinessActivity, "companyId").toInt())
            isMore = false
            viewmodel!!.Honorlistdata(Utils.getShared2(this@MyBusinessActivity, "companyId"),1,pagesize,1)
        } else {
            progressDialog!!.dismiss()
            Toast.makeText(applicationContext, "请检查您的网络是否连接", Toast.LENGTH_SHORT).show()
        }
        viewmodel!!.getresponedata().observe(this,Observer{response: Response<MyBusiness>->
            if(response.status == 0){
                progressDialog!!.dismiss()
                orgName.text = response.data.orgName
                industry.text = response.data.industry
                phone.text = response.data.phone
                email.text = response.data.email
                netAddress.text = response.data.netAddress
                employeeScale.text = response.data.employeeScale
                detailAddress.text = response.data.detailAddress
                profile = response.data.profile
            }else{
                progressDialog!!.dismiss()
                dialogUtils!!.showTwo(this@MyBusinessActivity, "提示", response.msg)
            }
        })
        viewmodel!!.getexternalcontactdata().observe(this,Observer{response: Response<List<Externalcontact>>->
            if(response.status == 0){
                externalcontactlist = response.data
                Log.e("size",""+externalcontactlist?.size)

            }else{
                progressDialog!!.dismiss()
                dialogUtils!!.showTwo(this@MyBusinessActivity, "提示", response.msg)
            }
        })

        viewmodel!!.getAddresssavedata().observe(this,Observer{response: Response<*>->
            if(response.status == 0){
                progressDialog!!.dismiss()
                viewmodel!!.deliveryAddress(Utils.getShared2(this@MyBusinessActivity, "companyId").toInt())
                Toast.makeText(applicationContext, "保存成功！", Toast.LENGTH_LONG).show()
                dialog!!.dismiss()
            }else{
                progressDialog!!.dismiss()
                dialogUtils!!.showTwo(this@MyBusinessActivity, "提示", response.msg)
            }
        })

        viewmodel!!.gethonorsave().observe(this,Observer{response:Response<*>->
            if(response.status == 0){
                progressDialog!!.dismiss()
                isMore = true
                refreshLayout!!.autoRefresh()
                Toast.makeText(applicationContext, "保存成功！", Toast.LENGTH_LONG).show()
                dialog1!!.dismiss()
            }else{
                progressDialog!!.dismiss()
                dialogUtils!!.showTwo(this@MyBusinessActivity, "提示", response.msg)
            }
        })
        viewmodel!!.gethonorupdata().observe(this,Observer{response:Response<*>->
            if(response.status == 0){
                progressDialog!!.dismiss()
                refreshLayout!!.autoRefresh()
                Toast.makeText(applicationContext, "更新成功！", Toast.LENGTH_LONG).show()
                dialog1!!.dismiss()
            }else{
                progressDialog!!.dismiss()
                dialogUtils!!.showTwo(this@MyBusinessActivity, "提示", response.msg)
            }
        })

        viewmodel!!.getdeliveryAddressdata().observe(this,Observer{response: Response<List<ReceivingAddress>>->
            if(response.status == 0){
                for(str in response.data){
                    if(str.isDefault.equals("1")){
                        name = str.name
                        telephone = str.telephone
                        address = str.detailAddress
                        Province = str.province
                        City = str.city
                        District = str.district
                        Id = str.id
                    }
                }
            }
        })

        viewmodel!!.getuploaddata().observe(this@MyBusinessActivity, Observer { uploadImgResponse: Response<uploadImg> ->
            if (uploadImgResponse.status == 0) {
                progressDialog!!.dismiss()
                fileName = if (uploadImgResponse.data.imgList.size > 1) {
                        uploadImgResponse.data.imgList[uploadImgResponse.data.imgList.size - 1]
                    }else {
                        uploadImgResponse.data.imgList[0]
                    }
                Log.e("TAG", "filePath: $fileName")
                Toast.makeText(applicationContext, "图片上传成功", Toast.LENGTH_LONG).show()
            } else {
                progressDialog!!.dismiss()
                dialogUtils!!.showTwo(this@MyBusinessActivity, "提示", uploadImgResponse.msg)
            }
        })

        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        Event()
    }

    private fun Event() {

        edit_iv!!.setOnClickListener(this)
        Companyprofile_lin!!.setOnClickListener(this)
        Contacts_lin!!.setOnClickListener(this)
        Accountinformation_lin!!.setOnClickListener(this)
        Invoiceinformation_lin!!.setOnClickListener(this)
        Receivinginformation_lin!!.setOnClickListener(this)
        Honoraryqualification_lin!!.setOnClickListener(this)

        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            if(verticalOffset == -465){
                toolbar_title.text = orgName.text.toString()
            }
            if(verticalOffset == 0){
                toolbar_title.text = ""
            }

        })
    }

    @SuppressLint("InflateParams", "SetTextI18n", "ClickableViewAccessibility")
    override fun onClick(v: View) {
        if (v === edit_iv) {
            dialog = MyBottomSheetDialog(this@MyBusinessActivity)
            val box_view = LayoutInflater.from(this@MyBusinessActivity).inflate(R.layout.editinginformation_layout, null)
            dialog!!.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) //←重点在这里，来，都记下笔记
            dialog!!.setContentView(box_view)
            dialog!!.setCancelable(true)
            dialog!!.setCanceledOnTouchOutside(true)
            dialog!!.show()
            val close_iv = box_view.findViewById<View>(R.id.close_iv) as ImageView
            val listener = View.OnClickListener { dialog!!.dismiss() }
            close_iv.setOnClickListener(listener)
        }
        if (v === Companyprofile_lin) {
            dialog = MyBottomSheetDialog(this@MyBusinessActivity)
            val box_view = LayoutInflater.from(this@MyBusinessActivity).inflate(R.layout.companyprofile_layout, null)
            dialog!!.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) //←重点在这里，来，都记下笔记
            dialog!!.setContentView(box_view)
            dialog!!.setCancelable(true)
            dialog!!.setCanceledOnTouchOutside(true)
            dialog!!.show()
            val close_iv = box_view.findViewById<View>(R.id.close_iv) as ImageView
            val profile_et = box_view.findViewById<EditText>(R.id.profile_et)
            profile_et.setText(profile)
            val listener = View.OnClickListener { dialog!!.dismiss() }
            close_iv.setOnClickListener(listener)
        }
        if(v == Honoraryqualification_lin){
            num +=1
            isMore = false
            dialog = MyBottomSheetDialog(this@MyBusinessActivity)
            val box_view = LayoutInflater.from(this@MyBusinessActivity).inflate(R.layout.honoraryqualification, null)
            dialog!!.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) //←重点在这里，来，都记下笔记
            dialog!!.setContentView(box_view)
            dialog!!.setCancelable(false)
            dialog!!.setCanceledOnTouchOutside(false)
            dialog!!.show()
            val close_iv = box_view.findViewById<ImageView>(R.id.close_iv)
            val recycler: RecyclerViewEmptySupport = box_view.findViewById(R.id.recyclerView)
            val add_lin: TextView = box_view.findViewById(R.id.add_lin)
            refreshLayout = box_view.findViewById(R.id.refreshLayout)
            val mLinearLayoutManager: LinearLayoutManager
            Log.e("num",""+num)

            viewmodel!!.gethonorlistdata().observe(this,Observer{response:Response<Honor>->
                if(response.status == 0){
                    if(num == 1){
                    if(isMore == false){
                        if(response.data.list.size != 0){
                                responsedata = response.data
                                refreshLayout!!.finishRefresh(true) //结束刷新
                            honoraryqualificationAdapter = responsedata!!.list?.let { HonoraryqualificationAdapter(it,this) }
                            recycler.layoutManager = GridLayoutManager(applicationContext, 2, GridLayoutManager.VERTICAL, false)
                            recycler.adapter = honoraryqualificationAdapter
                            honoraryqualificationAdapter?.setOnItemClickListener(object : HonoraryqualificationAdapter.OnItemClickListener{
                                override fun onItemClick(view: View, position: Int) {
                                    fileName = responsedata!!.list[position].fileName.replace("https://ctgdev.oss-cn-shanghai.aliyuncs.com/urm/", "")
                                    ivpath = responsedata!!.list[position].fileName
                                    Qualificationname = responsedata!!.list[position].name
                                    remarks = responsedata!!.list[position].remark
                                    Qualificationid = responsedata!!.list[position].id.toString()
                                    type = 2
                                    add_group()
                                }
                            })


                        }
                    }
                    }else{
                        //Log.e("responsedata",""+response.data.list.size)
                        honoraryqualificationAdapter = responsedata!!.list?.let { HonoraryqualificationAdapter(it,this) }
                        recycler.layoutManager = GridLayoutManager(applicationContext, 2, GridLayoutManager.VERTICAL, false)
                        recycler.adapter = honoraryqualificationAdapter
                        honoraryqualificationAdapter?.setOnItemClickListener(object : HonoraryqualificationAdapter.OnItemClickListener{
                            override fun onItemClick(view: View, position: Int) {
                                fileName = responsedata!!.list[position].fileName.replace("https://ctgdev.oss-cn-shanghai.aliyuncs.com/urm/", "")
                                ivpath = responsedata!!.list[position].fileName
                                Qualificationname = responsedata!!.list[position].name
                                remarks = responsedata!!.list[position].remark
                                Qualificationid = responsedata!!.list[position].id.toString()
                                type = 2
                                add_group()

                            }
                        })


                    }
                        // mLinearLayoutManager = LinearLayoutManager(this@MyBusinessActivity)


                }else{
                    progressDialog!!.dismiss()
                    dialogUtils!!.showTwo(this@MyBusinessActivity, "提示", response.msg)
                }
            })
            if(num == 1){
            viewmodel!!.getgethonorMorelistdata().observe(this,Observer{respone: Response<Honor>->
                    if(isMore == true){
                        if(respone.data.list.size != 0){
                            responsedata!!.list.addAll(respone.data.list)
                            Log.e("responsedata11",""+respone.data.list.size)
                            honoraryqualificationAdapter!!.notifyDataSetChanged()
                            refreshLayout!!.finishLoadMore(true)
                        }else{
                            refreshLayout!!.finishLoadMoreWithNoMoreData()
                            Toast.makeText(applicationContext, "没有跟多数据了", Toast.LENGTH_SHORT).show()
                        }
                }
            })
            }else{
                if(isMore == true){
                    if(responsedata!!.list.size != 0){
                        honoraryqualificationAdapter!!.notifyDataSetChanged()
                        refreshLayout!!.finishLoadMore(true)
                    }else{
                        refreshLayout!!.finishLoadMoreWithNoMoreData()
                        Toast.makeText(applicationContext, "没有跟多数据了", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            val listener = View.OnClickListener {
                dialog!!.dismiss()
            }
            val addlistener = View.OnClickListener {
                type = 1
                add_group()
            }
            add_lin.setOnClickListener(addlistener)
            close_iv.setOnClickListener(listener)
            refreshLayout!!.setEnableRefresh(true)
            refreshLayout!!.setEnableLoadMore(true)
            refreshLayout!!.setOnRefreshListener {
                isMore = false
                viewmodel!!.Honorlistdata(Utils.getShared2(this@MyBusinessActivity, "companyId"),1,pagesize,1)
                pagenum = 1
                num = 1
                responsedata!!.list.clear()
            }
            refreshLayout!!.setOnLoadMoreListener {
                isMore = true
                pagenum += 1
                viewmodel!!.Honorlistdata(Utils.getShared2(this@MyBusinessActivity, "companyId"),pagenum,pagesize,2)
            }
        }

        if (v === Contacts_lin) {
            dialog = MyBottomSheetDialog(this@MyBusinessActivity)
            val box_view = LayoutInflater.from(this@MyBusinessActivity).inflate(R.layout.contacts_layout, null)
            dialog!!.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) //←重点在这里，来，都记下笔记
            dialog!!.setContentView(box_view)
            dialog!!.setCancelable(true)
            dialog!!.setCanceledOnTouchOutside(true)
            dialog!!.show()

            val close_iv = box_view.findViewById<ImageView>(R.id.close_iv)
            val recycler: RecyclerViewEmptySupport = box_view.findViewById(R.id.recyclerView)
            val mLinearLayoutManager: LinearLayoutManager
            adapter = externalcontactlist?.let { ContactsAdatper(it,this) }
            mLinearLayoutManager = LinearLayoutManager(this@MyBusinessActivity)
            recycler.layoutManager = GridLayoutManager(applicationContext, 2, GridLayoutManager.VERTICAL, false)
            recycler.adapter = adapter
            val listener = View.OnClickListener {
                dialog!!.dismiss()
            }
            close_iv.setOnClickListener(listener)
        }
        if (v === Accountinformation_lin) {
            dialog = MyBottomSheetDialog(this@MyBusinessActivity)
            val box_view = LayoutInflater.from(this@MyBusinessActivity).inflate(R.layout.accountinformation_layout, null)
            dialog!!.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) //←重点在这里，来，都记下笔记
            dialog!!.setContentView(box_view)
            dialog!!.setCancelable(true)
            dialog!!.setCanceledOnTouchOutside(true)
            dialog!!.show()
            val close_iv = box_view.findViewById<ImageView>(R.id.close_iv)
            val listener = View.OnClickListener { dialog!!.dismiss() }
            close_iv.setOnClickListener(listener)
        }
        if (v === Invoiceinformation_lin) {
            dialog = MyBottomSheetDialog(this@MyBusinessActivity)
            val box_view = LayoutInflater.from(this@MyBusinessActivity).inflate(R.layout.invoiceinformation_layout, null)
            dialog!!.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) //←重点在这里，来，都记下笔记
            dialog!!.setContentView(box_view)
            dialog!!.setCancelable(true)
            dialog!!.setCanceledOnTouchOutside(true)
            dialog!!.show()
            val close_iv = box_view.findViewById<ImageView>(R.id.close_iv)
            val listener = View.OnClickListener { dialog!!.dismiss() }
            close_iv.setOnClickListener(listener)
        }
        if (v === Receivinginformation_lin) {
            dialog = MyBottomSheetDialog(this@MyBusinessActivity)
            val box_view = LayoutInflater.from(this@MyBusinessActivity).inflate(R.layout.receivinginformation_layout, null)
            dialog!!.window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) //←重点在这里，来，都记下笔记
            dialog!!.setContentView(box_view)
            dialog!!.setCancelable(true)
            dialog!!.setCanceledOnTouchOutside(true)
            dialog!!.show()
            val close_iv = box_view.findViewById<ImageView>(R.id.close_iv)
            val Name:TextView = box_view.findViewById(R.id.name)
            val Telephone:TextView = box_view.findViewById(R.id.telephone)
            Select_et = box_view.findViewById(R.id.select_et)
            val DetailAddress:TextView = box_view.findViewById(R.id.detailAddress)
            val save_btn:Button = box_view.findViewById(R.id.save_btn)
            Name.text = name.toString()
            Telephone.text = telephone.toString()
            DetailAddress.text = address.toString()
            Select_et!!.text = Province.toString()+City.toString()+District.toString()

            val listener = View.OnClickListener {
                dialog!!.dismiss()
            }
            val selectlistener = View.OnClickListener {
                selectAddress()
                dialog!!.dismiss()
            }
            val savelistener = View.OnClickListener {
                if(TextUtils.isEmpty(Name.text.toString())){
                    Toast.makeText(applicationContext, "联系人不能为空", Toast.LENGTH_SHORT).show()
                }else if(TextUtils.isEmpty(Telephone.text.toString())){
                    Toast.makeText(applicationContext, "联系人不能为空", Toast.LENGTH_SHORT).show()
                }else  if(TextUtils.isEmpty(DetailAddress.text.toString())){
                    Toast.makeText(applicationContext, "详细地址不能为空", Toast.LENGTH_SHORT).show()
                }else{
                    progressDialog!!.show()
                    viewmodel!!.Addresssave(Id.toString(),Province.toString(),City.toString(),District.toString(),DetailAddress.text.toString(),Name.text.toString(),Telephone.text.toString(),
                            Utils.getShared2(this@MyBusinessActivity, "companyId"))
                }

            }

            close_iv.setOnClickListener(listener)
            Select_et!!.setOnClickListener(selectlistener)
            save_btn!!.setOnClickListener(savelistener)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_plant_detail, menu)
        return true
    }

    private fun add_group() {
        val dialog = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this.baseContext).inflate(R.layout.add_honoraryqualification, null, false)
        dialog.setView(view)
        dialog.setCancelable(false)
        val qualifications_et = view.findViewById<EditText>(R.id.qualifications_et)
        val remarks_et = view.findViewById<EditText>(R.id.remarks_et)
        Addqualification_iv = view.findViewById<ImageView>(R.id.Addqualification_iv)
        val confirm_bt = view.findViewById<Button>(R.id.confirm_bt)
        val cancel_bt = view.findViewById<Button>(R.id.cancel_bt)
        if(type == 2){
            qualifications_et.setText(Qualificationname)
            remarks_et.setText(remarks)
            Addqualification_iv?.let { Glide.with(this).load(ivpath).into(it) }
        }
         dialog1 = dialog.show()
        confirm_bt.setOnClickListener {

            if(TextUtils.isEmpty(qualifications_et.text.toString())){
                Toast.makeText(applicationContext, "资质名称不能为空", Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(remarks_et.text.toString())){
                Toast.makeText(applicationContext, "备注不能为空", Toast.LENGTH_SHORT).show()
            }else if(fileName ==  null){
                Toast.makeText(applicationContext, "请上传资质图片", Toast.LENGTH_SHORT).show()
            }else{
                progressDialog!!.setMessage("保存中...")
                progressDialog!!.show()
                if(type == 1){
                    viewmodel!!.Honorsavedata(Utils.getShared2(this@MyBusinessActivity, "companyId"),fileName.toString(),qualifications_et.text.toString(),remarks_et.text.toString())
                }else{
                    viewmodel!!.Honorupdate(Qualificationid!!,fileName.toString(),qualifications_et.text.toString(),remarks_et.text.toString())
                }

            }
        }
        cancel_bt.setOnClickListener { dialog1!!.dismiss() }
        Addqualification_iv!!.setOnClickListener {
            selection()
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
                .province(Province.toString())
                .city(City.toString())
                .district(District.toString())
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
                selectvstr = (province.trim { it <= ' ' } + "-" + city.trim { it <= ' ' } + "-" + district.trim { it <= ' ' })
                Province = province
                City = city
                District = district
                Select_et!!.text = selectvstr
                dialog!!.show()
            }

            override fun onCancel() { dialog!!.show() }
        })
    }

    //拍照
    fun selection() { //参数很多，根据需要添加
        PictureSelector.create(this@MyBusinessActivity)
                .openGallery(PictureMimeType.ofImage()) // 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1) // 最大图片选择数量
                .minSelectNum(1) // 最小选择数量
                .imageSpanCount(4) // 每行显示个数
                .selectionMode(PictureConfig.SINGLE) // 多选 or 单选PictureConfig.MULTIPLE : PictureConfig.SINGLE
                .previewImage(true) // 是否可预览图片
                .isWeChatStyle(true) //开启R.style.picture_WeChat_style样式
                //.setPictureCropStyle(style)//动态自定义裁剪主题
                .isCamera(true) // 是否显示拍照按钮
                .isZoomAnim(true) // 图片列表点击 缩放效果 默认true
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(true) // 是否裁剪
                .compress(true) // 是否压缩
                .circleDimmedLayer(false) // 是否开启圆形裁剪
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .withAspectRatio(1, 1) // 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                //.selectionMedia(selectList)// 是否传入已选图片
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                //.compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
                //.compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                .rotateEnabled(true) // 裁剪是否可旋转图片
                //.scaleEnabled()// 裁剪是否可放大缩小图片
                //.recordVideoSecond()//录制视频秒数 默认60s
                //.isUseCustomCamera(true)// 开启自定义相机
                // .setButtonFeatures(CustomCameraView.BUTTON_STATE_ONLY_CAPTURE)// 自定义相机按钮状态,CustomCameraView.BUTTON_STATE_BOTH
                .forResult(PictureConfig.CHOOSE_REQUEST) //结果回调onActivityResult code
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val images: List<LocalMedia>
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) { // 图片选择结果回调
                images = PictureSelector.obtainMultipleResult(data)
                Addqualification_iv?.let { Glide.with(this).load(images[0].compressPath).into(it) }

                if (images[0].compressPath != null) {
                    uploadImg(images[0].compressPath)
                } else {
                    Toast.makeText(applicationContext, "选择图片错误", Toast.LENGTH_SHORT).show()
                }
                //selectList = PictureSelector.obtainMultipleResult(data);
                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
            }
        }
    }
    //上传文件
    private fun uploadImg(imgurl: String) {
        Log.e("TAG", "imgurl: $imgurl")
        progressDialog!!.setMessage("正在上传...")
        progressDialog!!.show()
        if (NetworkUtils.isConnected(applicationContext)) {
            val file = File(imgurl)
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            var part: MultipartBody.Part? = null
            part =   MultipartBody.Part.createFormData("excelfile", file.name, requestFile)
            viewmodel!!.uploadFile(part)
        } else {
            progressDialog!!.dismiss()
            dialogUtils!!.showTwo(this@MyBusinessActivity, "提示", "网络不给力")
        }
    }
}