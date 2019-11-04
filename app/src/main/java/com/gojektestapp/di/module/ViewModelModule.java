package com.gojektestapp.di.module;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.gojektestapp.di.ViewModelFactory;
import com.gojektestapp.ui.viemodel.TrendingViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TrendingViewModel.class)
    abstract ViewModel bindWelcomeViewModel(TrendingViewModel listViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}