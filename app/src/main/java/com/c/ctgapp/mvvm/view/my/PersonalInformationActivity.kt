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
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.c.ctgapp.R
import com.c.ctgapp.Tools.DialogUtils
import com.c.ctgapp.Tools.Utils
import com.c.ctgapp.databinding.ActivityPersonalinformationBinding
import com.c.ctgapp.mvvm.model.*
import com.c.ctgapp.mvvm.view.BaseActivity
import com.c.ctgapp.mvvm.viewmodel.DaggerViewModelFactory
import com.c.ctgapp.mvvm.viewmodel.PersonalInformationViewModel
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.pilot.common.utils.NetworkUtils
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.title.*
import kotlinx.android.synthetic.main.activity_personalinformation.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*
import javax.inject.Inject

@Suppress("DEPRECATION")
class PersonalInformationActivity : BaseActivity(),Observer<PersonalInfo>{
    private var filePath: String? = null
    private var progressDialog: ProgressDialog? = null
    private var dialogUtils: DialogUtils? = null
    private var binding: ActivityPersonalinformationBinding? = null
    @JvmField
    @Inject
    var viewModelFactory: DaggerViewModelFactory? = null
    var model: PersonalInformationViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personalinformation)
        toolbar_title.text = "个人信息"
        setSupportActionBar(toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        progressDialog = ProgressDialog(this@PersonalInformationActivity,
                R.style.AppTheme_Dark_Dialog)

        progressDialog!!.isIndeterminate = true
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.setCancelable(true)
        dialogUtils = DialogUtils()
        model = ViewModelProviders.of(this, viewModelFactory).get(PersonalInformationViewModel::class.java)
        model!!.identification(Utils.getShared2(applicationContext, "userId"))
        //StatusLiveData.getInstance().observe(this, this)
        UserInfoLiveData.getInstance().observe(this, this)
        model!!.getdata().observe(this@PersonalInformationActivity, Observer { response: Response<*> ->
            if (response.status == 0) {
                progressDialog!!.dismiss()
                model!!.PeronalInfo(Utils.getShared2(applicationContext, "userId").toInt())
                Toast.makeText(applicationContext, response.msg, Toast.LENGTH_LONG).show()
            } else {
                progressDialog!!.dismiss()
                dialogUtils!!.showTwo(this@PersonalInformationActivity, "提示", response.msg)
            }
        })
        model!!.getuploaddata().observe(this, Observer { uploadImgResponse: Response<uploadImg> ->
            if (uploadImgResponse.status == 0) {
                progressDialog!!.dismiss()
                filePath = if (uploadImgResponse.data.imgList.size > 1) {
                    uploadImgResponse.data.imgList[uploadImgResponse.data.imgList.size - 1]
                } else {
                    uploadImgResponse.data.imgList[0]
                }
                Log.e("TAG", "filePath: $filePath")
                Toast.makeText(applicationContext, "图片上传成功", Toast.LENGTH_LONG).show()
            } else {
                progressDialog!!.dismiss()
                dialogUtils!!.showTwo(this@PersonalInformationActivity, "提示", uploadImgResponse.msg)
            }
        })
        model!!.getpersoninfo().observe(this, Observer { response:Response<PersonalInfo>->
            if(response.status == 0){
                Log.e("s","d")
                putpersondata(response.data)
            }else{
                dialogUtils!!.showTwo(this, "提示", response.msg)
            }
        })

        model!!.getidentificationdata().observe(this, Observer { response: Response<Certificationinfo> ->
            if(response.status == 0){
                Log.e("data",""+response.data)
                    if(response.data == null){
                        auditStatus_tv.text = "未认证"
                    }else{
                        if(response.data.auditStatus.equals("审核中") ){
                            auditStatus_tv.text = "审核中"
                        }
                        if(response.data.auditStatus.equals("审核不通过") ){
                            auditStatus_tv.text = "审核不通过"
                        }
                        if(response.data.auditStatus.equals("审核通过") ){
                            auditStatus_tv.text = "审核通过"
                        }
                    }
                putcertificationinfodata(response.data)
            }else {
                dialogUtils!!.showTwo(this@PersonalInformationActivity, "提示", response.msg)
            }
        })


    }

    //保存个人信息
    fun usersave(view: View?) {
        if (filePath == null) {
            Toast.makeText(applicationContext, "请上传头像", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(binding!!.nickname.text.toString())) {
            Toast.makeText(applicationContext, "昵称不能为空！", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(binding!!.work.text.toString())) {
            Toast.makeText(applicationContext, "职位不能为空！", Toast.LENGTH_SHORT).show()
        } else {
            progressDialog!!.setMessage("正在提交...")
            progressDialog!!.show()
            if (NetworkUtils.isConnected(applicationContext)) {
                model!!.usersave(Utils.getShared2(applicationContext, "userId"), Utils.getShared2(applicationContext, "ctgId"), binding!!.edulevel.text.toString(), binding!!.nickname.text.toString(), filePath, binding!!.work.text.toString())
            } else {
                dialogUtils!!.showTwo(this@PersonalInformationActivity, "提示", "网络不给力")
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
            val part: MultipartBody.Part
            part = if (file.length() == 0L) {
                MultipartBody.Part.createFormData("", "")
            } else {
                MultipartBody.Part.createFormData("excelfile", file.name, requestFile)
            }
            model!!.uploadFile(part)
        } else {
            progressDialog!!.dismiss()
            dialogUtils!!.showTwo(this@PersonalInformationActivity, "提示", "网络不给力")
        }
    }

    //个人认证
    fun Personalauthentication(view: View?) {

        startActivity(Intent(applicationContext, PersonalAuthenticationActivity::class.java))

    }

    //拍照
    fun Headimageselection(view: View?) { //参数很多，根据需要添加
        PictureSelector.create(this@PersonalInformationActivity)
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
                .circleDimmedLayer(true) // 是否开启圆形裁剪
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
                Glide.with(this).load(images[0].compressPath).into(binding!!.niceImageView)
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
    private fun putcertificationinfodata(certificationinfo: Certificationinfo){
        CertificationinfoLiveData.getInstance().value = certificationinfo
    }

    //更新个人信息
    private fun putpersondata(personalInfo: PersonalInfo){
        UserInfoLiveData.getInstance().value = personalInfo
    }

    private fun updatamyinfo(myinfo:Int){
        val status = Status()
        status.myinfo = myinfo
        StatusLiveData.getInstance().value = status
    }

    override fun onChanged(t: PersonalInfo?) {
        if(t!=null){
            binding!!.nickname.setText(t.nickname)
            binding!!.work.setText(t.work)
            binding!!.edulevel.setText(t.edulevel)
            filePath = t.file.replace("https://ctgdev.oss-cn-shanghai.aliyuncs.com/urm/", "")
            Log.e("TAG", "filePath: $filePath")
            Glide.with(this).load(t.file).into(binding!!.niceImageView)
        }
    }


}