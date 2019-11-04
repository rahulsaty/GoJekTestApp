package com.gojektestapp.di.module;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.gojektestapp.BuildConfig;
import com.gojektestapp.R;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


@Module
public class OkHttpModule {
    public static final String BASE_URL = "baseUrl";

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(OkHttpClient.Builder clientBuilder) {

        addLoggingInterceptor(clientBuilder);
        return clientBuilder.build();
    }


    @Provides
    OkHttpClient.Builder provideOkHttpClientBuilder() {
        return new OkHttpClient.Builder();
    }

    @Named(BASE_URL)
    @Provides
    @Singleton
    protected HttpUrl provideBaseUrl(Context context) {
        return HttpUrl.parse(context.getString(R.string.base_url));
    }


    private void addLoggingInterceptor(@NonNull OkHttpClient.Builder clientBuilder) {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.d("OkHttp", message));
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(loggingInterceptor);
            clientBuilder.addNetworkInterceptor(loggingInterceptor);
        }
    }

}
