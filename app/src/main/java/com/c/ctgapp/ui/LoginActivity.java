package com.c.ctgapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.c.ctgapp.MainActivity;
import com.c.ctgapp.R;
import com.c.ctgapp.databinding.ActivityLoginBinding;
import com.c.ctgapp.mvvm.model.Response;
import com.c.ctgapp.mvvm.model.User;
import com.c.ctgapp.mvvm.viewmodel.UserViewModel;
import com.c.ctgapp.widget.CountDownButton;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity{

    private ActivityLoginBinding activityLoginBinding;
    private UserViewModel userViewModel;
    private ProgressDialog progressDialog;
    @BindView(R.id.Verificationcodelogin_tv)
    TextView Verificationcodelogin_tv;//切换验证码登录
    @BindView(R.id.Phonenumber_ed)
    EditText Phonenumber_ed;//手机号
    @BindView(R.id.VerificationCode_ed)
    EditText VerificationCode_ed;//验证码/密码
    @BindView(R.id.Getverificationcode_btn)
    CountDownButton Getverificationcode_btn;//获取验证码
    @BindView(R.id.login_btn)
    Button login_btn;//登录
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    int type = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        userViewModel = new ViewModelProvider(
                this, new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(UserViewModel.class);

        userViewModel.getdata().observe(LoginActivity.this, response -> {
            if(response.getStatus() == 0){
                Getverificationcode_btn.start();
                Toast.makeText(getApplicationContext(),response.getMsg(),Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(),response.getMsg(),Toast.LENGTH_LONG).show();
            }
        });

        userViewModel.login().observe(LoginActivity.this, userBeanResponse -> {
            if(userBeanResponse.getStatus() == 0){
                progressDialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }else {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),userBeanResponse.getMsg(),Toast.LENGTH_LONG).show();
            }
        });

        ButterKnife.bind(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!Getverificationcode_btn.isFinish()) {
            Getverificationcode_btn.cancel();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    //获取验证码
    public void getCode(View view){
        if(TextUtils.isEmpty(Phonenumber_ed.getText().toString())){
            Toast.makeText(getApplicationContext(),"手机号码不能为空",Toast.LENGTH_SHORT).show();
        }else {
            if(Getverificationcode_btn.isFinish()){
                userViewModel.GetSMS(Phonenumber_ed.getText().toString(),"1");
            }
        }
    }

    //登录
    public void  Login(View view){
        if(TextUtils.isEmpty(Phonenumber_ed.getText().toString())){
            Toast.makeText(getApplicationContext(),"手机号码不能为空",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(VerificationCode_ed.getText().toString())){
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
            progressDialog.setCancelable(false);
            progressDialog.setMessage("登陆中...");
            progressDialog.show();
            userViewModel.Login(Phonenumber_ed.getText().toString(),VerificationCode_ed.getText().toString(),VerificationCode_ed.getText().toString(),type);

        }
    }
    public void Verificationcodelogin(View view){
        if(type == 1){
            Getverificationcode_btn.setVisibility(View.GONE);
            Verificationcodelogin_tv.setText("验证码登录");
            VerificationCode_ed.setHint("请输入密码");
            VerificationCode_ed.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
            VerificationCode_ed.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            type = 2;
        }else {
            if(type == 2){
                Getverificationcode_btn.setVisibility(View.VISIBLE);
                Verificationcodelogin_tv.setText("密码登录");
                VerificationCode_ed.setHint("请输入验证码");
                VerificationCode_ed.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                VerificationCode_ed.setInputType(InputType.TYPE_CLASS_PHONE);
                type = 1 ;
            }
        }

    }


}