package com.c.ctgapp.ui.my.BusinessFriend;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.c.ctgapp.Adapter.FragmentAdapter;
import com.c.ctgapp.R;
import com.c.ctgapp.Tools.NoScrollViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusinessFriendActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;//标题
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pager)
    NoScrollViewPager pager;
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    public ArrayList<Fragment> mFragments = new ArrayList<>();
    public ArrayList<String> fragmentTitle = new ArrayList<>();
    @BindView(R.id.RB_Distributor)
    CheckBox RB_Distributor;//经销商
    @BindView(R.id.RB_RepairDepot)
    CheckBox RB_RepairDepot;//修理厂
    @BindView(R.id.RB_Manufacturer)
    CheckBox RB_Manufacturer;//制造商
    @BindView(R.id.RB_logistics)
    CheckBox RB_logistics;//物流
    @BindView(R.id.RB_Insurancecompany)
    CheckBox RB_Insurancecompany;//保险公司
    @BindView(R.id.RB_Cardemolitionplant)
    CheckBox RB_Cardemolitionplant;//拆车厂
    @BindView(R.id.Groupsetting_lin)
    LinearLayout Groupsetting_lin;//添加分组
    @BindView(R.id.mDrawer_layout)
    DrawerLayout mDrawer_layout;
    @BindView(R.id.screen_iv)
    ImageView screen_iv;//打开右边菜单
    @BindView(R.id.close_dr)
    ImageView close_dr;
    FragmentAdapter adapter;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businessfriend);

        ButterKnife.bind(this);
        toolbar_title.setText("商友");
        this.setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        initUI();
        Event();
    }

    private void  initUI(){

        RB_Distributor.setChecked(true);
        RB_RepairDepot.setChecked(true);
        RB_Manufacturer.setChecked(true);
        RB_logistics.setChecked(true);
        RB_Insurancecompany.setChecked(true);
        RB_Cardemolitionplant.setChecked(true);
        mDrawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        AllBusinessFriendFragment allBusinessFriendFragment = new AllBusinessFriendFragment();
        CarDismantlingFragment carDismantlingFragment = new CarDismantlingFragment();
        DistributorFragment distributorFragment = new DistributorFragment();
        LnsuranceCompanyFragment lnsuranceCompanyFragment = new LnsuranceCompanyFragment();
        LogisticsFragment logisticsFragment = new LogisticsFragment();
        ManufacturerFragment manufacturerFragment = new ManufacturerFragment();
        RepairDepotFragment repairDepotFragment = new RepairDepotFragment();
        adapter = new FragmentAdapter(getSupportFragmentManager(),mFragments,fragmentTitle);
        mFragments.add(allBusinessFriendFragment);
        fragmentTitle.add("所有");

        mFragments.add(distributorFragment);
        fragmentTitle.add("经销商");

        mFragments.add(repairDepotFragment);
        fragmentTitle.add("修理厂");

        mFragments.add(manufacturerFragment);
        fragmentTitle.add("制造商");

        mFragments.add(logisticsFragment);
        fragmentTitle.add("物流");

        mFragments.add(lnsuranceCompanyFragment);
        fragmentTitle.add("保险公司");

        mFragments.add(carDismantlingFragment);
        fragmentTitle.add("拆车厂");

        pager.setAdapter(adapter);
        tab_layout.setupWithViewPager(pager);
        for(int i = 0;i<fragmentTitle.size();i++){
            if(fragmentTitle.get(i).equals("经销商")){
                RB_Distributor.setChecked(true);
            }
            if(fragmentTitle.get(i).equals("修理厂")){
                RB_RepairDepot.setChecked(true);
            }
            if(fragmentTitle.get(i).equals("制造商")){
                RB_Manufacturer.setChecked(true);
            }
            if(fragmentTitle.get(i).equals("物流")){
                RB_logistics.setChecked(true);
            }
            if(fragmentTitle.get(i).equals("保险公司")){
                RB_Insurancecompany.setChecked(true);
            }
            if(fragmentTitle.get(i).equals("拆车厂")){
                RB_Cardemolitionplant.setChecked(true);
            }
        }
        RB_Distributor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mFragments.add(distributorFragment);
                    fragmentTitle.add("经销商");
                    pager.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    pager.setCurrentItem(fragmentTitle.size()-1);
                }else {
                    mFragments.remove(distributorFragment);
                    fragmentTitle.remove("经销商");
                    pager.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        RB_RepairDepot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mFragments.add(repairDepotFragment);
                    fragmentTitle.add("修理厂");
                    pager.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    pager.setCurrentItem(fragmentTitle.size()-1);

                }else {
                    mFragments.remove(repairDepotFragment);
                    fragmentTitle.remove("修理厂");
                    pager.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        RB_Manufacturer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mFragments.add(manufacturerFragment);
                    fragmentTitle.add("制造商");
                    pager.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    pager.setCurrentItem(fragmentTitle.size()-1);
                }else {
                    mFragments.remove(manufacturerFragment);
                    fragmentTitle.remove("制造商");
                    pager.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }
        });

        RB_logistics.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mFragments.add(logisticsFragment);
                    fragmentTitle.add("物流");
                    pager.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    pager.setCurrentItem(fragmentTitle.size()-1);
                }else {
                    mFragments.remove(logisticsFragment);
                    fragmentTitle.remove("物流");
                    pager.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }
        });

        RB_Insurancecompany.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mFragments.add(lnsuranceCompanyFragment);
                    fragmentTitle.add("保险公司");
                    pager.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    pager.setCurrentItem(fragmentTitle.size()-1);
                }else {
                    mFragments.remove(lnsuranceCompanyFragment);
                    fragmentTitle.remove("保险公司");
                    pager.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        RB_Cardemolitionplant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mFragments.add(carDismantlingFragment);
                    fragmentTitle.add("拆车厂");
                    pager.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    pager.setCurrentItem(fragmentTitle.size()-1);
                }else {
                    mFragments.remove(carDismantlingFragment);
                    fragmentTitle.remove("拆车厂");
                    pager.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }
        });

    }

    private void Event(){
        screen_iv.setOnClickListener(this);
        close_dr.setOnClickListener(this);
        Groupsetting_lin.setOnClickListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {
        if(v == screen_iv){
            mDrawer_layout.openDrawer(Gravity.END);
            }
        if(v == close_dr){
            mDrawer_layout.closeDrawer(Gravity.END);
        }
        if(v == Groupsetting_lin){
            Intent intent = new Intent(getApplicationContext(),GroupingActivity.class);
            startActivity(intent);
        }

    }

    //返回键处理
    @SuppressLint("WrongConstant")
    @Override
    public void onBackPressed() {
        if (mDrawer_layout.isDrawerOpen(Gravity.END)) {
            mDrawer_layout.closeDrawer(Gravity.END);
        } else {
            super.onBackPressed();
        }
    }

}
