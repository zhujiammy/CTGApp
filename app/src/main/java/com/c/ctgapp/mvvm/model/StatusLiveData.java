package com.c.ctgapp.mvvm.model;

import androidx.lifecycle.LiveData;

public class StatusLiveData extends MutableLiveData<Status> {
    private StatusLiveData() {
    }

    private static class Holder {
        public static final StatusLiveData INSTANCE = new StatusLiveData();
    }

    public static StatusLiveData getInstance() {
        return Holder.INSTANCE;
    }
}


