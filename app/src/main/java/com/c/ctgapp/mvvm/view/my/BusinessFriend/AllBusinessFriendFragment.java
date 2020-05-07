package com.c.ctgapp.mvvm.view.my.BusinessFriend;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.c.ctgapp.Adapter.AllBusinessFriendAdapter;
import com.c.ctgapp.R;
import com.c.ctgapp.Tools.RecyclerViewEmptySupport;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllBusinessFriendFragment extends Fragment {
    //所有商友
    public View view;
    private AllBusinessFriendAdapter adapter;
    @BindView(R.id.recyclerView)
    RecyclerViewEmptySupport recyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_allbusinessfriend,container,false);
        ButterKnife.bind(this,view);
        adapter = new AllBusinessFriendAdapter(getActivity());
        adapter.setOnitemClickListener((view, position) -> startActivity(new Intent(getActivity(),MyBusinessFriendActvity.class)));
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
