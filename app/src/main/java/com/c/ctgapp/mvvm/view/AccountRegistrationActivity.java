package com.c.ctgapp.mvvm.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.c.ctgapp.R;
import com.c.ctgapp.Tools.DialogUtils;
import com.c.ctgapp.databinding.AccountRegistrationBinding;
import com.c.ctgapp.mvvm.viewmodel.AccountRegistrationViewmodel;
import com.pilot.common.utils.NetworkUtils;

import java.util.Objects;

public class AccountRegistrationActivity extends AppCompatActivity {

    private AccountRegistrationBinding binding;
    private AccountRegistrationViewmodel model;
    private ProgressDialog progressDialog;
    private DialogUtils dialogUtils;
    TextView toolbar_title;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.account_registration);
        toolbar = findViewById(R.id.toolbar);
        toolbar_title = findViewById(R.id.toolbar_title);
        this.setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        dialogUtils = new DialogUtils();
        model = new ViewModelProvider(
                this, new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(AccountRegistrationViewmodel.class);

        model.getdata().observe(AccountRegistrationActivity.this, response -> {
            if(response.getStatus() == 0){
                binding.GetverificationcodeBtn.start();
                Toast.makeText(getApplicationContext(),response.getMsg(),Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(),response.getMsg(),Toast.LENGTH_LONG).show();
            }
        });

        model.getdatareslut().observe(AccountRegistrationActivity.this, response -> {
            if(response.getStatus() == 0){
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),response.getMsg(),Toast.LENGTH_LONG).show();
                finish();
            }else {
                progressDialog.dismiss();
                dialogUtils.showTwo(AccountRegistrationActivity.this,"提示",response.getMsg());
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!binding.GetverificationcodeBtn.isFinish()) {
            binding.GetverificationcodeBtn.cancel();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    //获取验证码
    public void getCode(View view){
        if(TextUtils.isEmpty(binding.PhonenumberEd.getText().toString())){
            Toast.makeText(getApplicationContext(),"手机号码不能为空",Toast.LENGTH_SHORT).show();
        }else {
            if(binding.GetverificationcodeBtn.isFinish()){
                if(NetworkUtils.isConnected(getApplicationContext())){
                    model.GetSMS(binding.PhonenumberEd.getText().toString(),"4");
                }else {
                    Toast.makeText(getApplicationContext(),"请检查您的网络是否连接",Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    public void Register(View view){
        if(TextUtils.isEmpty(binding.realNameEd.getText().toString())){
            Toast.makeText(getApplicationContext(),"真实姓名不能为空！",Toast.LENGTH_SHORT).show();
        }else  if(TextUtils.isEmpty(binding.PhonenumberEd.getText().toString())){
            Toast.makeText(getApplicationContext(),"手机号码不能为空！",Toast.LENGTH_SHORT).show();
        }else  if(TextUtils.isEmpty(binding.VerificationCodeEd.getText().toString())){
            Toast.makeText(getApplicationContext(),"验证码不能为空！",Toast.LENGTH_SHORT).show();
        }else  if(TextUtils.isEmpty(binding.passwordEd.getText().toString())){
            Toast.makeText(getApplicationContext(),"密码不能为空！",Toast.LENGTH_SHORT).show();
        }else  if(TextUtils.isEmpty(binding.confirmpassword.getText().toString())){
            Toast.makeText(getApplicationContext(),"确认密码不能为空！",Toast.LENGTH_SHORT).show();
        }else  if(!binding.passwordEd.getText().toString().equals(binding.confirmpassword.getText().toString())){
            Toast.makeText(getApplicationContext(),"两次密码输入不一致！",Toast.LENGTH_SHORT).show();
        }else {
            progressDialog = new ProgressDialog(AccountRegistrationActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(true);
            progressDialog.setMessage("正在提交...");
            progressDialog.show();
            if(NetworkUtils.isConnected(getApplicationContext())){
                model.register(binding.realNameEd.getText().toString(),binding.PhonenumberEd.getText().toString(),binding.passwordEd.getText().toString(),null,binding.VerificationCodeEd.getText().toString());
            }else {
                Toast.makeText(getApplicationContext(),"请检查您的网络是否连接",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
