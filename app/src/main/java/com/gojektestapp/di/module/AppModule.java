package com.gojektestapp.di.module;

import android.content.Context;


import com.gojektestapp.GojetApplication;
import com.gojektestapp.rx.AppSchedulerProvider;
import com.gojektestapp.rx.RxSchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(GojetApplication application) {
        return application;
    }

    @Provides
    @Singleton
    RxSchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }


}
