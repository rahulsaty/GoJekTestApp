package com.gojektestapp.ui;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TreadingProvider {

    @ContributesAndroidInjector()
    abstract NoNetworkFragment provideWelcomeFragment();
    @ContributesAndroidInjector()
    abstract TrendingListFragment provideAccoutListFragment();
}
