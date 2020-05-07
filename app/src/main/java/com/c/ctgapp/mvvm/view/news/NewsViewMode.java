package com.c.ctgapp.mvvm.view.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewsViewMode extends ViewModel {

    private MutableLiveData<String> mText;

    public NewsViewMode() {
        mText = new MutableLiveData<>();
        mText.setValue("这是消息");
    }

    public LiveData<String> getText() {
        return mText;
    }
}