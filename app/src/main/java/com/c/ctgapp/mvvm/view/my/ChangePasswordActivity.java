package com.c.ctgapp.mvvm.view.my;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.c.ctgapp.R;
import com.c.ctgapp.Tools.DialogUtils;
import com.c.ctgapp.Tools.Utils;
import com.c.ctgapp.databinding.ActivityChangepasswordBinding;
import com.c.ctgapp.mvvm.viewmodel.ChangePasswordViewModel;
import com.pilot.common.utils.NetworkUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePasswordActivity extends AppCompatActivity {

    private ActivityChangepasswordBinding binding;
    private ChangePasswordViewModel model;
    private ProgressDialog progressDialog;
    private DialogUtils dialogUtils;
    TextView toolbar_title;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_changepassword);
        toolbar = findViewById(R.id.toolbar);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("修改密码");
        this.setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        dialogUtils = new DialogUtils();
        model = new ViewModelProvider(
                this, new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(ChangePasswordViewModel.class);
        model.getdata().observe(ChangePasswordActivity.this, response -> {
            if(response.getStatus() == 0){
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),response.getMsg(),Toast.LENGTH_LONG).show();
                finish();
            }else {
                progressDialog.dismiss();
                dialogUtils.showTwo(ChangePasswordActivity.this,"提示",response.getMsg());
            }
        });

        //是否显示密码
        binding.HideshowCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                binding.oldPassword.setInputType(InputType.TYPE_CLASS_TEXT);
            }else {
                binding.oldPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
        binding.HideshowCheckC.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                binding.newPassword.setInputType(InputType.TYPE_CLASS_TEXT);
            }else {
                binding.newPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });


    }

    //修改密码
    public void updatePassword(View view){
        if(TextUtils.isEmpty(binding.oldPassword.getText().toString())){
            Toast.makeText(getApplicationContext(),"旧密码不能为空！",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(binding.newPassword.getText().toString())){
            Toast.makeText(getApplicationContext(),"新密码不能为空！",Toast.LENGTH_SHORT).show();
        }else {
            progressDialog = new ProgressDialog(ChangePasswordActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(true);
            progressDialog.setMessage("正在提交...");
            progressDialog.show();
            if(NetworkUtils.isConnected(getApplicationContext())){
                model.updatePassword(Utils.getShared2(getApplicationContext(),"userId"),binding.oldPassword.getText().toString(),binding.newPassword.getText().toString());
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
