package com.c.ctgapp.mvvm.model;

import androidx.lifecycle.LiveData;

public class GroupIdLiveData extends MutableLiveData<UrmFriendGroup> {
    private GroupIdLiveData() {
    }

    private static class Holder {
        public static final GroupIdLiveData INSTANCE = new GroupIdLiveData();
    }

    public static GroupIdLiveData getInstance() {
        return Holder.INSTANCE;
    }
}