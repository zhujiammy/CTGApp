package com.c.ctgapp.mvvm.model;

import androidx.lifecycle.LiveData;

public class UserInfoLiveData extends MutableLiveData<PersonalInfo> {
    private UserInfoLiveData() {
    }

    private static class Holder {
        public static final UserInfoLiveData INSTANCE = new UserInfoLiveData();
    }

    public static UserInfoLiveData getInstance() {
        return Holder.INSTANCE;
    }
}