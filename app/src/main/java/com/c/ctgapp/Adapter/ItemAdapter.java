package com.c.ctgapp.Adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.c.ctgapp.R;
import com.c.ctgapp.Tools.AZList.AZBaseAdapter;
import com.c.ctgapp.Tools.AZList.AZItemEntity;
import com.shehuan.niv.NiceImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemAdapter extends AZBaseAdapter<String, ItemAdapter.ItemHolder> {
	private Context context;
	public ItemAdapter(List<AZItemEntity> dataList,Context context) {
		super(dataList);
		this.context = context;
	}

	@Override
	public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter, parent, false));
	}

	@Override
	public void onBindViewHolder(ItemHolder holder, int position) {
		holder.mTextName.setText(mDataList.get(position).getName());
		holder.phone_num.setText(mDataList.get(position).getPhoneNum());
		Log.e("TAG", "onBindViewHolder: "+mDataList.get(position).getImgUrl());
		if(mDataList.get(position).getImgUrl() != null){
			Glide.with(context).load(mDataList.get(position).getImgUrl()).into(holder.imgurl);
		}else {
			Glide.with(context).load(R.mipmap.defaulthead).into(holder.imgurl);
		}

	}

	static class ItemHolder extends RecyclerView.ViewHolder {

		TextView mTextName;
		TextView phone_num;
		CircleImageView imgurl;

		ItemHolder(View itemView) {
			super(itemView);
			mTextName = itemView.findViewById(R.id.mTextName);
			phone_num = itemView.findViewById(R.id.phone_num);
            imgurl = itemView.findViewById(R.id.imgurl);
		}
	}
}
