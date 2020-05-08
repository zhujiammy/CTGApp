package com.c.ctgapp.mvvm.view;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.c.ctgapp.CTGApp;
import com.c.ctgapp.MainActivity;
import com.c.ctgapp.R;
import com.c.ctgapp.Service.CTGDBService;
import com.c.ctgapp.Tools.AppExecutors;
import com.c.ctgapp.Tools.DialogUtils;
import com.c.ctgapp.Tools.Utils;
import com.c.ctgapp.dao.UserDao;
import com.c.ctgapp.databasectg.DatabaseHelper;
import com.c.ctgapp.databinding.ActivityLoginBinding;
import com.c.ctgapp.mvvm.model.Response;
import com.c.ctgapp.mvvm.model.User;
import com.c.ctgapp.mvvm.viewmodel.UserViewModel;
import com.c.ctgapp.retrofit.Constants;
import com.c.ctgapp.widget.ClearEditText;
import com.c.ctgapp.widget.CountDownButton;
import com.pilot.common.utils.NetworkUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("Registered")
public class LoginActivity extends AppCompatActivity{

    private ActivityLoginBinding activityLoginBinding;
    private UserViewModel userViewModel;
    private ProgressDialog progressDialog;
    private DialogUtils dialogUtils;
    private CTGApp ctgApp;
    private CTGDBService service;

    int type = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        //创建数据库
        //service = new CTGDBService(getApplicationContext(), Constants.DB_NAME,null,Constants.DB_VERSION);
        ctgApp = (CTGApp)getApplication();
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
                   AppExecutors executors = new AppExecutors();
                    executors.getDiskIO().execute(() -> {
                        if(ctgApp.getAppDatabase().userDao().loadAllUsersByUserid(String.valueOf(userBeanResponse.getData().userId)).getCount() == 0){
                            if(ctgApp.getAppDatabase().userDao().insertUsers(userBeanResponse.getData()) > 0){
                                Log.e("TAG", "onCreate: "+"数据成功插入user表" );
                            }else {
                                Log.e("TAG", "onCreate: "+"数据插入失败" );
                            }
                        }else {
                            Log.e("TAG", "onCreate: "+"数据已存在" );
                        }
                        ctgApp.getAppDatabase().userDao().loadAllUsersByUserid(String.valueOf(userBeanResponse.getData().userId)).close();

                    });

                //service.queryUserData(userBeanResponse);
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