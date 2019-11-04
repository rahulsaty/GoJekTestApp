package com.gojektestapp.di.module;


import androidx.annotation.NonNull;

import com.gojektestapp.net.RestApi;
import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class RestServiceModule {

  @NonNull
  private Retrofit createRetrofitClient(OkHttpClient client, RxJava2CallAdapterFactory rxJavaCallAdapterFactory,
                                        GsonConverterFactory gsonConverterFactory,
                                        HttpUrl baseUrl) {
    return new Retrofit.Builder()
            .addCallAdapterFactory(rxJavaCallAdapterFactory)
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(baseUrl)
            .client(client)
            .build();
  }

  @Provides
  @Singleton
  Retrofit provideRetrofit( OkHttpClient client,
                           RxJava2CallAdapterFactory rxJavaCallAdapterFactory,
                           GsonConverterFactory gsonConverterFactory,
                           @Named(OkHttpModule.BASE_URL) HttpUrl baseUrl) {
    return createRetrofitClient(client, rxJavaCallAdapterFactory, gsonConverterFactory, baseUrl);
  }
  @Provides
  @Singleton
  RxJava2CallAdapterFactory provideRxJavaCallAdapterFactory() {
    return RxJava2CallAdapterFactory.create();
  }

  @Provides
  @Singleton
  GsonConverterFactory provideGsonConverterFactory(Gson gson) {
    return GsonConverterFactory.create(gson);
  }

  @Provides
  RestApi provideRestApi(Retrofit retrofit){
    return retrofit.create(RestApi.class);
  }

}
