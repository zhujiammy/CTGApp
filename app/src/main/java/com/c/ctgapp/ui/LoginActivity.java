package com.c.ctgapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.c.ctgapp.MainActivity;
import com.c.ctgapp.R;
import com.c.ctgapp.Tools.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.Verificationcodelogin_tv)
    TextView Verificationcodelogin_tv;//切换验证码登录
    @BindView(R.id.Phonenumber_ed)
    ClearEditText Phonenumber_ed;//手机号
    @BindView(R.id.VerificationCode_ed)
    ClearEditText VerificationCode_ed;//验证码/密码
    @BindView(R.id.Getverificationcode_btn)
    Button Getverificationcode_btn;//获取验证码
    @BindView(R.id.login_btn)
    Button login_btn;//登录
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Event();
    }

    private void Event(){
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == login_btn){
            //登录
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
