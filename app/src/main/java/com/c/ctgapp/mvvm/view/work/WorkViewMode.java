package com.c.ctgapp.mvvm.view.work;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WorkViewMode extends ViewModel {

    private MutableLiveData<String> mText;

    public WorkViewMode() {
        mText = new MutableLiveData<>();
        mText.setValue("这是工作");
    }

    public LiveData<String> getText() {
        return mText;
    }
}