package com.c.ctgapp.mvvm.view.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.c.ctgapp.Adapter.CustomerManagementAdapter;
import com.c.ctgapp.R;
import com.c.ctgapp.Tools.RecyclerViewEmptySupport;
import com.c.ctgapp.mvvm.view.my.BusinessFriend.BusinessFriendActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerManagementActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;//标题
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.Addcustomer_tv)
    TextView Addcustomer_tv;//添加客户
    private CustomerManagementAdapter adapter;
    @BindView(R.id.recyclerView)
    RecyclerViewEmptySupport recyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customermanagement);
        ButterKnife.bind(this);
        toolbar_title.setText("客户管理");
        this.setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        adapter = new CustomerManagementAdapter(getApplicationContext());
        mLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(adapter);
        Event();
    }
    private void Event(){
        Addcustomer_tv.setOnClickListener(this);
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
        if(v == Addcustomer_tv){
           Intent intent = new Intent(getApplicationContext(), BusinessFriendActivity.class);
           startActivity(intent);
        }
    }
}
