package com.c.ctgapp.mvvm.view.my;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.c.ctgapp.R;
import com.c.ctgapp.Tools.DialogUtils;
import com.c.ctgapp.Tools.Utils;
import com.c.ctgapp.databinding.ActivityPersonalinformationBinding;
import com.c.ctgapp.mvvm.view.BaseActivity;
import com.c.ctgapp.mvvm.viewmodel.DaggerViewModelFactory;
import com.c.ctgapp.mvvm.viewmodel.PersonalInformationViewModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.pilot.common.utils.NetworkUtils;

import java.io.File;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PersonalInformationActivity extends BaseActivity {

    private String filePath = null;
    private ProgressDialog progressDialog;
    private DialogUtils dialogUtils;
    private ActivityPersonalinformationBinding binding;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;//标题
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Inject
    DaggerViewModelFactory  viewModelFactory;
    PersonalInformationViewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_personalinformation);
        ButterKnife.bind(this);
        toolbar_title.setText("个人信息");
        this.setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        progressDialog = new ProgressDialog(PersonalInformationActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        dialogUtils = new DialogUtils();
        model = ViewModelProviders.of(this, viewModelFactory).get(PersonalInformationViewModel.class);
        model.init(Integer.parseInt(Utils.getShared2(getApplicationContext(),"userId")),Utils.getShared2(getApplicationContext(),"realName"));
        model.getdata().observe(PersonalInformationActivity.this, response -> {
            if(response.getStatus() == 0){
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),response.getMsg(),Toast.LENGTH_LONG).show();
            }else {
                progressDialog.dismiss();
                dialogUtils.showTwo(PersonalInformationActivity.this,"提示",response.getMsg());
            }
        });

        model.getuploaddata().observe(this, uploadImgResponse -> {
            if(uploadImgResponse.getStatus() == 0){
                progressDialog.dismiss();
                if(uploadImgResponse.getData().getImgList().size()>1){
                    filePath = uploadImgResponse.getData().getImgList().get(uploadImgResponse.getData().getImgList().size()-1);
                }else {
                    filePath = uploadImgResponse.getData().getImgList().get(0);
                }
                Log.e("TAG", "filePath: "+filePath );
                Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
            }else {
                progressDialog.dismiss();
                dialogUtils.showTwo(PersonalInformationActivity.this,"提示",uploadImgResponse.getMsg());
            }
        });



        model.getPersonalInfo().observe(this, personalInfo -> {
            if(personalInfo !=null){
                binding.nickname.setText(personalInfo.nickname);
                binding.work.setText(personalInfo.work);
                binding.edulevel.setText(personalInfo.edulevel);
                filePath = personalInfo.file.replace("https://ctgdev.oss-cn-shanghai.aliyuncs.com/urm/","");
                Log.e("TAG", "filePath: "+filePath );
                Glide.with(this).load(personalInfo.file).into(binding.niceImageView);
            }

        });

    }



    //保存个人信息
    public void usersave(View view){
        if(filePath == null){
            Toast.makeText(getApplicationContext(),"请上传头像",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(binding.nickname.getText().toString())){
            Toast.makeText(getApplicationContext(),"昵称不能为空！",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(binding.work.getText().toString())){
            Toast.makeText(getApplicationContext(),"职位不能为空！",Toast.LENGTH_SHORT).show();
        }else {
            progressDialog.setMessage("正在提交...");
            progressDialog.show();
            if(NetworkUtils.isConnected(getApplicationContext())){
                model.usersave(Utils.getShared2(getApplicationContext(),"userId"),Utils.getShared2(getApplicationContext(),"ctgId"),binding.edulevel.getText().toString(),binding.nickname.getText().toString(),filePath,binding.work.getText().toString());
            }else {
                dialogUtils.showTwo(PersonalInformationActivity.this,"提示","网络不给力");
                progressDialog.dismiss();
            }
        }
    }

    //上传文件
    private void uploadImg(String imgurl){
        Log.e("TAG", "imgurl: "+imgurl );
            progressDialog.setMessage("正在上传...");
            progressDialog.show();
            if(NetworkUtils.isConnected(getApplicationContext())){
                final File file = new File(imgurl);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                MultipartBody.Part part;
                if(file.length() == 0){
                    part = MultipartBody.Part.createFormData("","");
                }else {
                    part = MultipartBody.Part.createFormData("excelfile", file.getName(), requestFile);
                }
                model.uploadFile(part);
            }else {
                progressDialog.dismiss();
                dialogUtils.showTwo(PersonalInformationActivity.this,"提示","网络不给力");
            }

    }

    //个人认证
    public void Personalauthentication(View view){
        startActivity(new Intent(getApplicationContext(),PersonalAuthenticationActivity.class));
    }

    //拍照
    public void Headimageselection(View view){
       //参数很多，根据需要添加
        PictureSelector.create(PersonalInformationActivity.this)
        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
        .maxSelectNum(1)// 最大图片选择数量
        .minSelectNum(1)// 最小选择数量
        .imageSpanCount(4)// 每行显示个数
        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选PictureConfig.MULTIPLE : PictureConfig.SINGLE
        .previewImage(true)// 是否可预览图片
         .isWeChatStyle(true)//开启R.style.picture_WeChat_style样式
        //.setPictureCropStyle(style)//动态自定义裁剪主题
        .isCamera(true)// 是否显示拍照按钮
        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
        .enableCrop(true)// 是否裁剪
        .compress(true)// 是否压缩
        .circleDimmedLayer(true)// 是否开启圆形裁剪

        //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
        .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
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
        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        if (resultCode == RESULT_OK) {
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调
        images = PictureSelector.obtainMultipleResult(data);
        Glide.with(this).load(images.get(0).getCompressPath()).into(binding.niceImageView);
        if(images.get(0).getCompressPath()!=null){
            uploadImg(images.get(0).getCompressPath());
        }else {
            Toast.makeText(getApplicationContext(),"选择图片错误",Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
