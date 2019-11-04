package com.gojektestapp.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GsonModule {


    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

}
