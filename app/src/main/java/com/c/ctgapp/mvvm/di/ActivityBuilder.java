package com.c.ctgapp.mvvm.di;


import com.c.ctgapp.MainActivity;
import com.c.ctgapp.mvvm.view.my.PersonalInformationActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract PersonalInformationActivity bindVideoListActivity();
    @ContributesAndroidInjector
    abstract MainActivity bindmainactity();
}