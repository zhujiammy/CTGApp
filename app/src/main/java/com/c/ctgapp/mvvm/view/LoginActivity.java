package com.c.ctgapp.mvvm.view;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.c.ctgapp.MainActivity;
import com.c.ctgapp.R;
import com.c.ctgapp.Tools.DialogUtils;
import com.c.ctgapp.Tools.Utils;
import com.c.ctgapp.databinding.ActivityLoginBinding;
import com.c.ctgapp.mvvm.viewmodel.UserViewModel;
import com.pilot.common.utils.NetworkUtils;

@SuppressLint("Registered")
public class LoginActivity extends AppCompatActivity{

    private ActivityLoginBinding activityLoginBinding;
    private UserViewModel userViewModel;
    private ProgressDialog progressDialog;
    private DialogUtils dialogUtils;
    int type = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        dialogUtils = new DialogUtils();
        userViewModel = new ViewModelProvider(
                this, new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(UserViewModel.class);
        userViewModel.getdata().observe(LoginActivity.this, response -> {
            if(response.getStatus() == 0){
                activityLoginBinding.GetverificationcodeBtn.start();
                Toast.makeText(getApplicationContext(),response.getMsg(),Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(),response.getMsg(),Toast.LENGTH_LONG).show();
            }
        });

        userViewModel.login().observe(LoginActivity.this, userBeanResponse -> {
            if(userBeanResponse.getStatus() == 0){
                progressDialog.dismiss();
                //service.queryUserData(userBeanResponse);
                Utils.setShare2(getApplicationContext(),"realName", userBeanResponse.getData().realName);
                Utils.setShare2(getApplicationContext(),"userId", String.valueOf(userBeanResponse.getData().userId));
                Utils.setShare2(getApplicationContext(),"ctgId", String.valueOf(userBeanResponse.getData().ctgId));
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                progressDialog.dismiss();
                dialogUtils.showTwo(LoginActivity.this,"提示",userBeanResponse.getMsg());
            }
        });
        initUI();
    }

    private void initUI(){
        activityLoginBinding.PhonenumberEd.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){//获得焦制点
                activityLoginBinding.linearLayout7.setBackgroundColor(getResources().getColor(R.color.line4));
            }else{//失去知焦道点
                activityLoginBinding.linearLayout7.setBackgroundColor(getResources().getColor(R.color.line));
            }
        });

        activityLoginBinding.VerificationCodeEd.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){//获得焦制点
                activityLoginBinding.linearLayout8.setBackgroundColor(getResources().getColor(R.color.line4));
            }else{//失去知焦道点
                activityLoginBinding.linearLayout8.setBackgroundColor(getResources().getColor(R.color.line));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!activityLoginBinding.GetverificationcodeBtn.isFinish()) {
            activityLoginBinding.GetverificationcodeBtn.cancel();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    //获取验证码
    public void getCode(View view){
        if(TextUtils.isEmpty(activityLoginBinding.PhonenumberEd.getText().toString())){
            Toast.makeText(getApplicationContext(),"手机号码不能为空",Toast.LENGTH_SHORT).show();
        }else {
            if(activityLoginBinding.GetverificationcodeBtn.isFinish()){

                if(NetworkUtils.isConnected(getApplicationContext())){
                    userViewModel.GetSMS(activityLoginBinding.PhonenumberEd.getText().toString(),"1");
                }else {
                    Toast.makeText(getApplicationContext(),"请检查您的网络是否连接",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //注册
    public void Registration(View view){
        startActivity(new Intent(getApplicationContext(),AccountRegistrationActivity.class));
    }
    //忘记密码
    public void Forgetpassword(View view){
        startActivity(new Intent(getApplicationContext(),ForgetPasswordActivity.class));
    }

    //登录
    public void  Login(View view){
        if(TextUtils.isEmpty(activityLoginBinding.PhonenumberEd.getText().toString())){
            Toast.makeText(getApplicationContext(),"手机号码不能为空",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(activityLoginBinding.VerificationCodeEd.getText().toString())){
                if(type == 1){
                    Toast.makeText(getApplicationContext(),"验证码不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                }
        } else {
            progressDialog = new ProgressDialog(LoginActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(true);
            progressDialog.setMessage("登陆中...");
            progressDialog.show();
            if(NetworkUtils.isConnected(getApplicationContext())){
                userViewModel.Login(activityLoginBinding.PhonenumberEd.getText().toString(),activityLoginBinding.VerificationCodeEd.getText().toString(),activityLoginBinding.VerificationCodeEd.getText().toString(),type);
            }else {
                Toast.makeText(getApplicationContext(),"请检查您的网络是否连接",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }


        }
    }
    public void Verificationcodelogin(View view){
        if(type == 1){
            activityLoginBinding.GetverificationcodeBtn.setVisibility(View.GONE);
            activityLoginBinding.VerificationcodeloginTv.setText("验证码登录");
            activityLoginBinding.VerificationCodeEd.setHint("请输入密码");
            activityLoginBinding.Forgetpasswordtv.setVisibility(View.VISIBLE);
            activityLoginBinding.VerificationCodeEd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
            activityLoginBinding.VerificationCodeEd.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            type = 2;
        }else {
            if(type == 2){
                activityLoginBinding.GetverificationcodeBtn.setVisibility(View.VISIBLE);
                activityLoginBinding.VerificationcodeloginTv.setText("密码登录");
                activityLoginBinding.VerificationCodeEd.setHint("请输入验证码");
                activityLoginBinding.Forgetpasswordtv.setVisibility(View.GONE);
                activityLoginBinding.VerificationCodeEd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                activityLoginBinding.VerificationCodeEd.setInputType(InputType.TYPE_CLASS_PHONE);
                type = 1 ;
            }
        }

    }


}