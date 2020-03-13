package com.c.ctgapp;
import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
public class MainViewModel extends ViewModel {
    private MutableLiveData<View> mHomeViewModel;
    private MutableLiveData<View> mMyViewModel;
    private MutableLiveData<View> mNewsViewMode;
    private MutableLiveData<View> mTransactionViewMode;
    private MutableLiveData<View> mWorkViewMode;

    public MainViewModel() {
        mHomeViewModel = new MutableLiveData<>();
        mMyViewModel = new MutableLiveData<>();
        mNewsViewMode = new MutableLiveData<>();
        mTransactionViewMode = new MutableLiveData<>();
        mWorkViewMode = new MutableLiveData<>();
    }

    public void setmHomeViewModel(View v) {
        mHomeViewModel.setValue(v);
    }

    public void setmMyViewModel(View v) {
        mMyViewModel.setValue(v);
    }

    public void setmNewsViewMode(View v) {
        mNewsViewMode.setValue(v);
    }

    public void setmTransactionViewMode(View v) {
        mTransactionViewMode.setValue(v);
    }

    public void setmWorkViewMode(View v) {
        mWorkViewMode.setValue(v);
    }

    public LiveData<View> getmHomeViewModel() {
        return mHomeViewModel;
    }

    public LiveData<View> getmMyViewModel() {
        return mMyViewModel;
    }

    public LiveData<View> getmNewsViewMode() {
        return mNewsViewMode;
    }

    public LiveData<View> getmTransactionViewMode() {
        return mTransactionViewMode;
    }

    public LiveData<View> getmWorkViewMode() {
        return mWorkViewMode;
    }
}
