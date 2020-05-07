package com.c.ctgapp.mvvm.view.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.c.ctgapp.Adapter.MyTeamAdapter;
import com.c.ctgapp.R;
import com.c.ctgapp.Tools.RecyclerViewEmptySupport;
import com.c.ctgapp.mvvm.view.my.BusinessFriend.ApplicationNoticeActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTeamActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;//标题
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private MyTeamAdapter adapter;
    @BindView(R.id.recyclerView)
    RecyclerViewEmptySupport recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    @BindView(R.id.ApplicationNotice_btn)
    LinearLayout ApplicationNotice_btn;//申请通知

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myteam);

        ButterKnife.bind(this);
        toolbar_title.setText("我的团队");
        this.setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        adapter = new MyTeamAdapter(getApplicationContext());
        mLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(adapter);
        Event();
    }

    private void Event(){
        ApplicationNotice_btn.setOnClickListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        if(item.getItemId() == R.id.add){
            startActivity(new Intent(getApplicationContext(),AddStaffActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_tem, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v == ApplicationNotice_btn){
            startActivity(new Intent(getApplicationContext(), ApplicationNoticeActivity.class));
        }
    }
}
