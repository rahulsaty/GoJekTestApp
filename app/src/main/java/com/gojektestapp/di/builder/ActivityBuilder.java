package com.gojektestapp.di.builder;

import com.gojektestapp.ui.TreadingProvider;
import com.gojektestapp.ui.TrendingActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = TreadingProvider.class)
    abstract TrendingActivity bindSplashActivity();




}
