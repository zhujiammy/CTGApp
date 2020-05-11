package com.c.ctgapp.mvvm.di;

import com.c.ctgapp.mvvm.view.my.MyFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuilder {
    @ContributesAndroidInjector
    public abstract MyFragment bindmyfragment();
}
