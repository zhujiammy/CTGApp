package com.c.ctgapp.ui.my;

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

public class PersonalInformationActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.toolbar_title)
    TextView toolbar_title;//标题
    @BindView(R.id.Personalauthentication_rt)
    RelativeLayout Personalauthentication_rt;//个人认证
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinformation);
        ButterKnife.bind(this);
        toolbar_title.setText("个人信息");
        this.setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        Event();
    }

    private void Event(){
        Personalauthentication_rt.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v == Personalauthentication_rt){
            //个人认证
            startActivity(new Intent(getApplicationContext(),PersonalAuthenticationActivity.class));
        }
    }
}
