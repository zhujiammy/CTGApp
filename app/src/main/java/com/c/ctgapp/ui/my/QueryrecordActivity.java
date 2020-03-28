package com.c.ctgapp.ui.my;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.c.ctgapp.Adapter.FragmentAdapter;
import com.c.ctgapp.R;
import com.c.ctgapp.Tools.NoScrollViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QueryrecordActivity extends AppCompatActivity {

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
    FragmentAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queryrecord);
        ButterKnife.bind(this);
        toolbar_title.setText("查询记录");
        this.setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        initUI();

    }

    private void initUI(){
        FrameNumberFragment frameNumberFragment = new FrameNumberFragment();
        OriginalDataFragment originalDataFragment = new OriginalDataFragment();
        adapter = new FragmentAdapter(getSupportFragmentManager(),mFragments,fragmentTitle);

        mFragments.add(frameNumberFragment);
        fragmentTitle.add("车架号");

        mFragments.add(originalDataFragment);
        fragmentTitle.add("原厂数据");
        pager.setAdapter(adapter);
        tab_layout.setupWithViewPager(pager);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
