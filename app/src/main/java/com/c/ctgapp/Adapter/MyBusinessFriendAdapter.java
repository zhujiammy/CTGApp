package com.c.ctgapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.ctgapp.Data.Trace;
import com.c.ctgapp.R;

import java.util.ArrayList;
import java.util.List;

public class MyBusinessFriendAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Trace> traceList = new ArrayList<>();
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL= 0x0001;

    public MyBusinessFriendAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
   /*     if (model.size() == 0) {
            return 1;
        }*/
        return 3;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //在onCreateViewHolder方法中，我们要根据不同的ViewType来返回不同的ViewHolder
        if (viewType == TYPE_TOP) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.empty_view_tab, viewGroup, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        View baseView;
        baseView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mybusinessfriend_data, viewGroup, false);
        BodyViewHolder bodyViewHolder = new BodyViewHolder(baseView);
        return bodyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int id) {
/*        if (id == 0) {
            return TYPE_TOP;
        }*/
        return TYPE_NORMAL;
    }
    /**
     * 给GridView中的条目用的ViewHolder，里面只有一个TextView
     */
    public class BodyViewHolder extends RecyclerView.ViewHolder {

        public BodyViewHolder(View itemView) {
            super(itemView);

        }

    }
}

