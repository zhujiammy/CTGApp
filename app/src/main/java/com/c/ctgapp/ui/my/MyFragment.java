package com.c.ctgapp.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.c.ctgapp.R;
import com.c.ctgapp.ui.my.SwitchingEnterprises.SwitchingEnterprisesActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

//我的
public class MyFragment extends Fragment implements View.OnClickListener {

    private MyViewModel myViewModell;
    private Intent intent;
    @BindView(R.id.SwitchingEnterprises_iv)
    ImageView SwitchingEnterprises_iv;//切换企业
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
    }
    @Override
    public void onClick(View v) {
        //切换企业
        if(v == SwitchingEnterprises_iv){
            intent = new Intent(getActivity(), SwitchingEnterprisesActivity.class);
            startActivity(intent);
        }
    }
}
