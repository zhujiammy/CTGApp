package com.c.ctgapp.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.c.ctgapp.Adapter.NewsFragmentAdapter;
import com.c.ctgapp.R;
import com.c.ctgapp.Tools.RecyclerViewEmptySupport;

import butterknife.BindView;
import butterknife.ButterKnife;


//消息
public class NewsFragment extends Fragment {

    private NewsViewMode newsViewMode;
    private NewsFragmentAdapter adapter;
    @BindView(R.id.recyclerView)
    RecyclerViewEmptySupport recyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        newsViewMode =
                ViewModelProviders.of(this).get(NewsViewMode.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this,root);
        adapter = new NewsFragmentAdapter(getActivity());
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(adapter);
        newsViewMode.getText().observe(getViewLifecycleOwner(), s -> {
        });
        return root;
    }
}
