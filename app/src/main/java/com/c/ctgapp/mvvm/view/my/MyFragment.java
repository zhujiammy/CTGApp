package com.c.ctgapp.mvvm.view.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.c.ctgapp.R;
import com.c.ctgapp.mvvm.view.my.BusinessFriend.BusinessFriendActivity;
import com.c.ctgapp.mvvm.view.my.SwitchingEnterprises.SwitchingEnterprisesActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

//我的
public class MyFragment extends Fragment implements View.OnClickListener {

    private MyViewModel myViewModell;
    private Intent intent;
    @BindView(R.id.niceImageView)
    CircleImageView niceImageView;//头像
    @BindView(R.id.name)
    TextView name;//昵称
    @BindView(R.id.phone_num)
    TextView phone_num;//手机号
    @BindView(R.id.edit_iv)
    ImageView edit_iv;//修改个人信息
    @BindView(R.id.SwitchingEnterprises_iv)
    ImageView SwitchingEnterprises_iv;//切换企业
    @BindView(R.id.Businessfriend_tv)
    TextView Businessfriend_tv;//商友
    @BindView(R.id.Set_RL)
    RelativeLayout Set_RL;//设置
    @BindView(R.id.Attentionnotes_rl)
    RelativeLayout Attentionnotes_rl;//关注记录
    @BindView(R.id.Queryrecord_rl)
    RelativeLayout Queryrecord_rl;//查询记录
    @BindView(R.id.mybusiness_lin)
    LinearLayout mybusiness_lin;//我的企业
    @BindView(R.id.Myteam_lin)
    LinearLayout Myteam_lin;//我的团队
    @BindView(R.id.customermanagement_lin)
    LinearLayout customermanagement_lin;//客户管理
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myViewModell = ViewModelProviders.of(this).get(MyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this,root);
        Event();
        myViewModell.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    private void Event(){
        SwitchingEnterprises_iv.setOnClickListener(this);
        niceImageView.setOnClickListener(this);
        name.setOnClickListener(this);
        phone_num.setOnClickListener(this);
        edit_iv.setOnClickListener(this);
        Businessfriend_tv.setOnClickListener(this);
        Set_RL.setOnClickListener(this);
        Attentionnotes_rl.setOnClickListener(this);
        Queryrecord_rl.setOnClickListener(this);
        mybusiness_lin.setOnClickListener(this);
        Myteam_lin.setOnClickListener(this);
        customermanagement_lin.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        //切换企业
        if(v == SwitchingEnterprises_iv){
            intent = new Intent(getActivity(), SwitchingEnterprisesActivity.class);
            startActivity(intent);
        }
        if(v == niceImageView||v == name || v == phone_num || v == edit_iv){
            //修改个人信息
            intent = new Intent(getActivity(),PersonalInformationActivity.class);
            startActivity(intent);
        }
        if(v == Businessfriend_tv){
            //商友
            intent = new Intent(getActivity(), BusinessFriendActivity.class);
            startActivity(intent);
        }
        if(v == Set_RL){
            //设置
            intent = new Intent(getActivity(),SetActivity.class);
            startActivity(intent);
        }
        if(v == Attentionnotes_rl){
            //关注记录
            intent = new Intent(getActivity(),AttentionNotesActivity.class);
            startActivity(intent);
        }
        if(v == Queryrecord_rl){
            //查询记录
            intent = new Intent(getActivity(),QueryrecordActivity.class);
            startActivity(intent);
        }
        if(v == mybusiness_lin){
            //我的企业
            intent = new Intent(getActivity(),MyBusinessActivity.class);
            startActivity(intent);
        }
        if(v == Myteam_lin){
            //我的团队
            intent = new Intent(getActivity(),MyTeamActivity.class);
            startActivity(intent);
        }
        if(v == customermanagement_lin){
            //客户管理
            intent = new Intent(getActivity(),CustomerManagementActivity.class);
            startActivity(intent);

        }
    }
}
