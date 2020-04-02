package com.c.ctgapp.Data;

public class PhoneDto <T>{
    private T  Name;
    private T PhoneNum;
    private T imgUrl;
    private String mSortLetters;

    public PhoneDto(String name, String telPhone,String imgUrl) {
        this.Name = (T) name;
        this.PhoneNum = (T) telPhone;
        this.imgUrl = (T) imgUrl;

    }



    public T getName() {
        return Name;
    }

    public void setName(T name) {
        Name = name;
    }

    public T getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(T phoneNum) {
        PhoneNum = phoneNum;
    }

    public T getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(T imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getSortLetters() {
        return mSortLetters;
    }

    public void setSortLetters(String sortLetters) {
        mSortLetters = sortLetters;
    }
}