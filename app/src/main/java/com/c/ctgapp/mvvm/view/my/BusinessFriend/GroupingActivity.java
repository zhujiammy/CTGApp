package com.c.ctgapp.mvvm.view.my.BusinessFriend;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.c.ctgapp.Adapter.GroupingAdapter;
import com.c.ctgapp.R;
import com.c.ctgapp.Tools.RecyclerViewEmptySupport;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupingActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;//标题
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.add_group_lin)
    LinearLayout add_group_lin;

    private GroupingAdapter adapter;
    @BindView(R.id.recyclerView)
    RecyclerViewEmptySupport recyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouping);
        ButterKnife.bind(this);
        toolbar_title.setText("分组");
        this.setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        adapter = new GroupingAdapter(getApplicationContext());
        mLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(adapter);

        Event();
    }

    private void Event(){
        add_group_lin.setOnClickListener(this);
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
        if(v == add_group_lin){
            //添加分组
            add_group();
        }
    }

    private void add_group(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this.getBaseContext()).inflate(R.layout.dialog_group,null,false);
        dialog.setView(view);
        dialog.setCancelable(false);
        EditText group_name = view.findViewById(R.id.group_name);
        Button confirm_bt = view.findViewById(R.id.confirm_bt);
        Button cancel_bt = view.findViewById(R.id.cancel_bt);
        AlertDialog dialog1 = dialog.show();

        confirm_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

    }
}
