package com.c.ctgapp.mvvm.view.my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.c.ctgapp.Adapter.ContactsAdatper;
import com.c.ctgapp.R;
import com.c.ctgapp.Tools.MyBottomSheetDialog;
import com.c.ctgapp.Tools.RecyclerViewEmptySupport;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyBusinessActivity extends AppCompatActivity implements View.OnClickListener {
     MyBottomSheetDialog dialog;
    @BindView(R.id.edit_iv)
    ImageView edit_iv;//编辑信息
    @BindView(R.id.Companyprofile_lin)
    LinearLayout Companyprofile_lin;//公司简介
    @BindView(R.id.Contacts_lin)
    LinearLayout Contacts_lin;//对外联系人
    @BindView(R.id.Accountinformation_lin)
    LinearLayout Accountinformation_lin;//账户信息
    @BindView(R.id.Invoiceinformation_lin)
    LinearLayout Invoiceinformation_lin;////开票信息
    @BindView(R.id.Receivinginformation_lin)
    LinearLayout Receivinginformation_lin;//收货信息
    private ContactsAdatper adapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybusiness);
        ButterKnife.bind(this);
        this.setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        Event();
    }
    private void Event(){
        edit_iv.setOnClickListener(this);
        Companyprofile_lin.setOnClickListener(this);
        Contacts_lin.setOnClickListener(this);
        Accountinformation_lin.setOnClickListener(this);
        Invoiceinformation_lin.setOnClickListener(this);
        Receivinginformation_lin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == edit_iv){
            dialog = new MyBottomSheetDialog(MyBusinessActivity.this);
            View box_view = LayoutInflater.from(MyBusinessActivity.this).inflate(R.layout.editinginformation_layout,null);
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //←重点在这里，来，都记下笔记
            dialog.setContentView(box_view);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
            ImageView close_iv = (ImageView) box_view.findViewById(R.id.close_iv);
            View.OnClickListener listener = new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }
            };
            close_iv.setOnClickListener(listener);

        }

        if(v == Companyprofile_lin){
            dialog = new MyBottomSheetDialog(MyBusinessActivity.this);
            View box_view = LayoutInflater.from(MyBusinessActivity.this).inflate(R.layout.companyprofile_layout,null);
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //←重点在这里，来，都记下笔记
            dialog.setContentView(box_view);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
            ImageView close_iv = (ImageView) box_view.findViewById(R.id.close_iv);
            View.OnClickListener listener = new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }
            };
            close_iv.setOnClickListener(listener);
        }


        if(v == Contacts_lin){
            dialog = new MyBottomSheetDialog(MyBusinessActivity.this);
            View box_view = LayoutInflater.from(MyBusinessActivity.this).inflate(R.layout.contacts_layout,null);
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //←重点在这里，来，都记下笔记
            dialog.setContentView(box_view);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
            ImageView close_iv = box_view.findViewById(R.id.close_iv);
            RecyclerViewEmptySupport recycler =box_view.findViewById(R.id.recyclerView);
            LinearLayoutManager mLinearLayoutManager;
            adapter = new ContactsAdatper(getApplicationContext());
            mLinearLayoutManager = new LinearLayoutManager(MyBusinessActivity.this);
            recycler.setLayoutManager (new GridLayoutManager(getApplicationContext (),2,GridLayoutManager.VERTICAL,false));
            recycler.setAdapter(adapter);
            View.OnClickListener listener = new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }
            };
            close_iv.setOnClickListener(listener);
        }

        if(v == Accountinformation_lin){
            dialog = new MyBottomSheetDialog(MyBusinessActivity.this);
            View box_view = LayoutInflater.from(MyBusinessActivity.this).inflate(R.layout.accountinformation_layout,null);
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //←重点在这里，来，都记下笔记
            dialog.setContentView(box_view);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
            ImageView close_iv = box_view.findViewById(R.id.close_iv);
            View.OnClickListener listener = new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }
            };
            close_iv.setOnClickListener(listener);
        }

        if(v == Invoiceinformation_lin){
            dialog = new MyBottomSheetDialog(MyBusinessActivity.this);
            View box_view = LayoutInflater.from(MyBusinessActivity.this).inflate(R.layout.invoiceinformation_layout,null);
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //←重点在这里，来，都记下笔记
            dialog.setContentView(box_view);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
            ImageView close_iv = box_view.findViewById(R.id.close_iv);
            View.OnClickListener listener = new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }
            };
            close_iv.setOnClickListener(listener);
        }

        if(v == Receivinginformation_lin){
            dialog = new MyBottomSheetDialog(MyBusinessActivity.this);
            View box_view = LayoutInflater.from(MyBusinessActivity.this).inflate(R.layout.receivinginformation_layout,null);
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //←重点在这里，来，都记下笔记
            dialog.setContentView(box_view);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
            ImageView close_iv = box_view.findViewById(R.id.close_iv);
            View.OnClickListener listener = new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }
            };
            close_iv.setOnClickListener(listener);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_plant_detail, menu);
        return true;
    }
}
