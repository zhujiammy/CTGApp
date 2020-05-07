package com.c.ctgapp.mvvm.view.my.BusinessFriend;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.c.ctgapp.Adapter.ItemAdapter;
import com.c.ctgapp.R;
import com.c.ctgapp.Tools.AZList.AZItemEntity;
import com.c.ctgapp.Tools.AZList.AZTitleDecoration;
import com.c.ctgapp.Tools.AZList.AZWaveSideBarView;
import com.c.ctgapp.Tools.AZList.LettersComparator;
import com.c.ctgapp.Tools.PhoneUtil;
import com.c.ctgapp.Tools.RecyclerViewEmptySupport;
import com.pilot.common.utils.PinyinUtils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MaillistActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;//标题
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_list)
    RecyclerViewEmptySupport mRecyclerView;
    @BindView(R.id.bar_list)
    AZWaveSideBarView mBarList;
    private Context mContext;
    private ItemAdapter mAdapter;
    private List<String> lettersarry = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maillist);
        ButterKnife.bind(this);
        mContext = this;
        toolbar_title.setText("通讯录");
        this.setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        initView();
        initEvent();
        check();
    }
    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new AZTitleDecoration(new AZTitleDecoration.TitleAttributes(mContext)));
    }

    private void initEvent() {
        mBarList.setOnLetterChangeListener(letter -> {
            int position = mAdapter.getSortLettersFirstPosition(letter);
            if (position != -1) {
                if(mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    LinearLayoutManager manager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    manager.scrollToPositionWithOffset(position, 0);
                } else {
                    mRecyclerView.getLayoutManager().scrollToPosition(position);
                }
            }
        });
    }

    /**
     * 检查权限
     */
    private void check() {
        //判断是否有权限
        if(ContextCompat.checkSelfPermission(MaillistActivity.this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MaillistActivity.this,new String[]{Manifest.permission.READ_CONTACTS},201);
        }else{
            initData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==201){
            initData();
        }else{
            return;
        }
    }
    private void initData() {
        PhoneUtil phoneUtil = new PhoneUtil(this);
        List<AZItemEntity> dateList = fillData(phoneUtil.getPhone());
        Collections.sort(dateList, new LettersComparator());

        mRecyclerView.setAdapter(mAdapter = new ItemAdapter(dateList,this));
    }

    private List<AZItemEntity> fillData(List<AZItemEntity> date) {

        List<AZItemEntity> sortList = new ArrayList<>();
        for (AZItemEntity aDate : date) {
            AZItemEntity item = new AZItemEntity(aDate.getName(),aDate.getPhoneNum(),aDate.getImgUrl());
            item.setName(aDate.getName());
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(aDate.getName());
            //取第一个首字母
            String letters = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (letters.matches("[A-Z]")) {
                item.setSortLetters(letters.toUpperCase());
                lettersarry.add(letters.toUpperCase());
                Log.e("TAG", "fillData: "+ letters.toUpperCase());
            } else {
                item.setSortLetters("#");
                lettersarry.add("#");
            }
            sortList.add(item);
        }
        Comparator comparator= Collator.getInstance(java.util.Locale.CHINA);
        Collections.sort(lettersarry,comparator);
        mBarList.mLetters = ridRepeat2(lettersarry);
        return sortList;

    }

    // Set集合去重，保持原来顺序
    public static List<String> ridRepeat2(List<String> list) {
        System.out.println("list = [" + list + "]");
        List<String> listNew = new ArrayList<String>();
        Set set = new HashSet();
        for (String str : list) {
            if (set.add(str)) {
                listNew.add(str);
            }
        }
        System.out.println("listNew = [" + listNew + "]");
        return listNew;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
