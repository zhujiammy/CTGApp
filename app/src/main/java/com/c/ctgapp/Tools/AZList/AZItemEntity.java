package com.c.ctgapp.Tools.AZList;


import android.net.Uri;

public class AZItemEntity {

	private String  Name;
	private String PhoneNum;
	private Uri imgUrl;
	private String mSortLetters;

	public AZItemEntity(String name, String telPhone,Uri imgUrl) {
		this.Name =  name;
		this.PhoneNum = telPhone;
		this.imgUrl = imgUrl;

	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPhoneNum() {
		return PhoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		PhoneNum = phoneNum;
	}

	public Uri getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(Uri imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getSortLetters() {
		return mSortLetters;
	}

	public void setSortLetters(String sortLetters) {
		mSortLetters = sortLetters;
	}
}
