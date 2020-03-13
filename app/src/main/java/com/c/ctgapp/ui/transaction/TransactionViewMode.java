package com.c.ctgapp.ui.transaction;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TransactionViewMode extends ViewModel {

    private MutableLiveData<String> mText;

    public TransactionViewMode() {
        mText = new MutableLiveData<>();
        mText.setValue("这是交易");
    }

    public LiveData<String> getText() {
        return mText;
    }
}