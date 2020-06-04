package com.c.ctgapp.mvvm.view.my

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.c.ctgapp.R
import com.c.ctgapp.Tools.DialogUtils
import com.c.ctgapp.Tools.Utils
import com.c.ctgapp.mvvm.viewmodel.PersonalAuthenticationViewmodel
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.pilot.common.utils.NetworkUtils
import java.util.*
import kotlinx.android.synthetic.main.title.*
import kotlinx.android.synthetic.main.activity_personalauthentication.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import androidx.lifecycle.Observer
import com.c.ctgapp.mvvm.model.*

class PersonalAuthenticationActivity : AppCompatActivity(),Observer<Certificationinfo>{
    private var progressDialog: ProgressDialog? = null
    var viewmodel: PersonalAuthenticationViewmodel? = null
    private var dialogUtils: DialogUtils? = null
    private var aimagestr: String? = null
    private var bimagestr: String? = null
    private var type: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personalauthentication)
        toolbar_title!!.text = "个人认证"
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        progressDialog = ProgressDialog(this@PersonalAuthenticationActivity,
                R.style.AppTheme_Dark_Dialog)
        progressDialog!!.isIndeterminate = true
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.setCancelable(true)
        CertificationinfoLiveData.getInstance().observe(this, this)
        dialogUtils = DialogUtils()
        viewmodel = ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory(application)
        ).get(PersonalAuthenticationViewmodel::class.java)
        viewmodel!!.getdata().observe(this@PersonalAuthenticationActivity,Observer { response: Response<*>->
            if (response.status == 0) {
                progressDialog!!.dismiss()
                Toast.makeText(applicationContext, response.msg, Toast.LENGTH_LONG).show()
                finish()
            } else {
                progressDialog!!.dismiss()
                dialogUtils!!.showTwo(this@PersonalAuthenticationActivity, "提示", response.msg)
            }
        })

        viewmodel!!.getuploaddata().observe(this@PersonalAuthenticationActivity, Observer { uploadImgResponse: Response<uploadImg> ->
            if (uploadImgResponse.status == 0) {
                progressDialog!!.dismiss()
                if(type == 2){
                    aimagestr = if (uploadImgResponse.data.imgList.size > 1) {
                        uploadImgResponse.data.imgList[uploadImgResponse.data.imgList.size - 1]
                    } else {
                        uploadImgResponse.data.imgList[0]
                    }
                }
                if(type == 3){
                    bimagestr = if (uploadImgResponse.data.imgList.size > 1) {
                        uploadImgResponse.data.imgList[uploadImgResponse.data.imgList.size - 1]
                    } else {
                        uploadImgResponse.data.imgList[0]
                    }
                }

                Log.e("TAG", "filePath: $aimagestr")
                Toast.makeText(applicationContext, "图片上传成功", Toast.LENGTH_LONG).show()
            } else {
                progressDialog!!.dismiss()
                dialogUtils!!.showTwo(this@PersonalAuthenticationActivity, "提示", uploadImgResponse.msg)
            }
        })
       aimage.setOnClickListener {
            type =2
            selection()
        }
        bimage.setOnClickListener {
            type =3
            selection()
        }

    }


    //提交个人认证
    fun Submitcertification(view: View?) {
             if (TextUtils.isEmpty(realname_ed.text.toString())) {
            Toast.makeText(applicationContext, "真是姓名不能为空！", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(idcardnum_ed.text.toString())) {
            Toast.makeText(applicationContext, "身份证号不能为空！", Toast.LENGTH_SHORT).show()
        }else if (aimagestr == null) {
            Toast.makeText(applicationContext, "请上传身份证正面", Toast.LENGTH_SHORT).show()
        }else if (bimagestr == null) {
                 Toast.makeText(applicationContext, "请上传身份证反面", Toast.LENGTH_SHORT).show() }
             else {
            progressDialog!!.setMessage("正在提交...")
            progressDialog!!.show()
            if (NetworkUtils.isConnected(applicationContext)) {
                viewmodel!!.Realauthentication(Utils.getShared2(applicationContext, "userId"),realname_ed.text.toString(),idcardnum_ed.text.toString(),aimagestr,bimagestr)
            } else {
                dialogUtils!!.showTwo(this@PersonalAuthenticationActivity, "提示", "网络不给力")
                progressDialog!!.dismiss()
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
            dialogUtils!!.showTwo(this@PersonalAuthenticationActivity, "提示", "网络不给力")
        }
    }

    //拍照
    fun selection() { //参数很多，根据需要添加
        PictureSelector.create(this@PersonalAuthenticationActivity)
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
                if(type == 2){
                    Glide.with(this).load(images[0].compressPath).into(aimage)
                }
                if(type == 3){
                    Glide.with(this).load(images[0].compressPath).into(bimage)
                }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onChanged(t: Certificationinfo?) {
        if(t != null) run {
             realname_ed.setText(t.realName)
            idcardnum_ed.setText(t.idCardNum)
            aimagestr = t.frontImgUrl.replace("https://ctgdev.oss-cn-shanghai.aliyuncs.com/urm/", "")
            bimagestr = t.backImgUrl.replace("https://ctgdev.oss-cn-shanghai.aliyuncs.com/urm/", "")
            Glide.with(this).load(t.frontImgUrl).into(aimage)
            Glide.with(this).load(t.backImgUrl).into(bimage)
        }
    }
}


