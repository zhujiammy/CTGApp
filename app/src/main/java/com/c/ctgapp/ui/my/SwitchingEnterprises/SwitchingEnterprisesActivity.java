package com.c.ctgapp.ui.my.SwitchingEnterprises;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.c.ctgapp.Adapter.SwitchingEnterprisesAdapter;
import com.c.ctgapp.R;
import com.c.ctgapp.Tools.RecyclerViewEmptySupport;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

//企业切换
@SuppressLint("Registered")
public class SwitchingEnterprisesActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;//标题
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private SwitchingEnterprisesAdapter adapter;
    @BindView(R.id.recyclerView)
    RecyclerViewEmptySupport recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    @BindView(R.id.createenterprise_btn)
    Button createenterprise_btn;//创建企业
    @BindView(R.id.Applicationaccession_btn)
    Button Applicationaccession_btn;//申请加入

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switchingenterprises);
        ButterKnife.bind(this);
        toolbar_title.setText("切换企业");
        this.setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        Event();
        adapter = new SwitchingEnterprisesAdapter(this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void Event(){
        createenterprise_btn.setOnClickListener(this);
        Applicationaccession_btn.setOnClickListener(this);
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
        if(v == createenterprise_btn){
            //创建企业
            startActivity(new Intent(this,CreateEnterpriseActivity.class));

        }
        if(v == Applicationaccession_btn){
            //创建企业
            startActivity(new Intent(this,ApplicationAccessionActivity.class));
        }
    }
}
