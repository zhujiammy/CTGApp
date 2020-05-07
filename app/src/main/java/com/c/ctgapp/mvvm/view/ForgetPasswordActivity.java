package com.c.ctgapp.mvvm.view;

import android.annotation.SuppressLint;
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
import com.c.ctgapp.databinding.ForgetpasswordBinding;
import com.c.ctgapp.mvvm.viewmodel.ForgetPasswordViewModel;
import com.pilot.common.utils.NetworkUtils;

import java.util.Objects;

@SuppressLint("Registered")
public class ForgetPasswordActivity extends AppCompatActivity {
    private ForgetpasswordBinding binding;
    private ForgetPasswordViewModel model;
    private ProgressDialog progressDialog;
    private DialogUtils dialogUtils;
    TextView toolbar_title;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.forgetpassword);
        toolbar = findViewById(R.id.toolbar);
        toolbar_title = findViewById(R.id.toolbar_title);
        this.setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        dialogUtils = new DialogUtils();
        model = new ViewModelProvider(
                this, new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(ForgetPasswordViewModel.class);

        model.getdata().observe(ForgetPasswordActivity.this, response -> {
            if(response.getStatus() == 0){
                binding.GetverificationcodeBtn.start();
                Toast.makeText(getApplicationContext(),response.getMsg(),Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(),response.getMsg(),Toast.LENGTH_LONG).show();
            }
        });

        model.getdatareslut().observe(ForgetPasswordActivity.this, response -> {
            if(response.getStatus() == 0){
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),response.getMsg(),Toast.LENGTH_LONG).show();
                finish();
            }else {
                progressDialog.dismiss();
                dialogUtils.showTwo(ForgetPasswordActivity.this,"提示",response.getMsg());
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
                    model.GetSMS(binding.PhonenumberEd.getText().toString(),"2");
                }else {
                    Toast.makeText(getApplicationContext(),"请检查您的网络是否连接",Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    //忘记密码
    public void updatePassByCodeAndNewPass(View view){
        if(TextUtils.isEmpty(binding.PhonenumberEd.getText().toString())){
            Toast.makeText(getApplicationContext(),"手机号码不能为空",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(binding.VerificationCodeEd.getText().toString())){
            Toast.makeText(getApplicationContext(),"验证码不能为空",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(binding.passwordEd.getText().toString())){
            Toast.makeText(getApplicationContext(),"新密码不能为空",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(binding.confirmpassword.getText().toString())){
            Toast.makeText(getApplicationContext(),"确认新密码不能为空",Toast.LENGTH_SHORT).show();
        }else if(!binding.passwordEd.getText().toString().equals(binding.confirmpassword.getText().toString())){
            Toast.makeText(getApplicationContext(),"两次密码输入不一致，请重新输入",Toast.LENGTH_SHORT).show();
        }else {
            progressDialog = new ProgressDialog(ForgetPasswordActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(true);
            progressDialog.setMessage("正在提交...");
            progressDialog.show();
            if(NetworkUtils.isConnected(getApplicationContext())){
                model.updatePassByCodeAndNewPass(binding.PhonenumberEd.getText().toString(),binding.VerificationCodeEd.getText().toString(),binding.confirmpassword.getText().toString());
            }else {
                Toast.makeText(getApplicationContext(),"请检查您的网络是否连接",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
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
