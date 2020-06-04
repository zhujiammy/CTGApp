package com.c.ctgapp.mvvm.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.c.ctgapp.mvvm.view.my.MainViewModel;
import com.c.ctgapp.mvvm.view.my.MyViewModel;
import com.c.ctgapp.mvvm.viewmodel.DaggerViewModelFactory;
import com.c.ctgapp.mvvm.viewmodel.PersonalInformationViewModel;

import org.jetbrains.annotations.Nullable;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract  class ViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(DaggerViewModelFactory factory);


    @Binds
    @IntoMap
    @ViewModelKey(PersonalInformationViewModel.class)
    abstract ViewModel PersonalInformationViewModel(PersonalInformationViewModel personalInformationViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MyViewModel.class)
    public abstract ViewModel MyViewModel(@Nullable MyViewModel myViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel MainviewModel(@Nullable MainViewModel mainViewModel);
}
