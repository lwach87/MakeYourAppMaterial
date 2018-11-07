package com.example.xyzreader.di.components;

import android.app.Application;
import com.example.xyzreader.ArticleApp;
import com.example.xyzreader.di.builder.ActivityBuilder;
import com.example.xyzreader.di.modules.AppModule;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import javax.inject.Singleton;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

  void inject(ArticleApp app);

  @Component.Builder
  interface Builder {

    @BindsInstance
    Builder application(Application application);

    AppComponent build();
  }

}
