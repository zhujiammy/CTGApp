package com.c.ctgapp.mvvm.di;
import android.app.Activity;

import com.c.ctgapp.CTGApp;

import javax.inject.Singleton;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class,
        MainFragmentBuilder.class,
        ViewModelModule.class,
})

public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance Builder application(CTGApp application);
        Builder appModule(AppModule appModule);
        AppComponent build();
    }

    void inject(CTGApp application);

}