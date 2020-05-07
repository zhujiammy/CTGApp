package com.c.ctgapp.mvvm.view.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.c.ctgapp.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.toolbar_title)
    TextView toolbar_title;//标题
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.ChangePassword_rl)
    RelativeLayout ChangePassword_rl;//修改密码
    @BindView(R.id.Receivingaddress_rl)
    RelativeLayout Receivingaddress_rl;//收货地址

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        toolbar_title.setText("设置");
        this.setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        Event();
    }

    private void Event(){
        ChangePassword_rl.setOnClickListener(this);
        Receivingaddress_rl.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v == ChangePassword_rl){
            //修改密码
            startActivity(new Intent(getApplicationContext(),ChangePasswordActivity.class));

        }
        if(v == Receivingaddress_rl){
            startActivity(new Intent(getApplicationContext(),ReceivingAddressActivity.class));
        }
    }
}
