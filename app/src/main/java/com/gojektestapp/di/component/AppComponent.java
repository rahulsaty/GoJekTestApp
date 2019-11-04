package com.gojektestapp.di.component;


import com.gojektestapp.GojetApplication;
import com.gojektestapp.di.builder.ActivityBuilder;
import com.gojektestapp.di.module.AppModule;
import com.gojektestapp.di.module.GsonModule;
import com.gojektestapp.di.module.OkHttpModule;
import com.gojektestapp.di.module.RestServiceModule;
import com.gojektestapp.di.module.ViewModelModule;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, ActivityBuilder.class, OkHttpModule.class,
        RestServiceModule.class, GsonModule.class,  ViewModelModule.class
})
public abstract class AppComponent implements AndroidInjector<GojetApplication> {

    @Component.Builder
    public interface Builder {
        @BindsInstance
        Builder application(GojetApplication app);
        AppComponent build();
    }

}
