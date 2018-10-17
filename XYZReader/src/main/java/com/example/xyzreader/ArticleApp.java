package com.example.xyzreader;

import android.app.Activity;
import android.app.Application;
import com.example.xyzreader.di.components.ApplicationComponent;
import com.example.xyzreader.di.components.DaggerApplicationComponent;
import com.example.xyzreader.di.modules.DatabaseModule;

public class ArticleApp extends Application {

  private ApplicationComponent component;

  public static ArticleApp get(Activity activity) {
    return (ArticleApp) activity.getApplication();
  }

  @Override
  public void onCreate() {
    super.onCreate();

    component = DaggerApplicationComponent.builder()
        .databaseModule(new DatabaseModule(this))
        .build();

  }

  public ApplicationComponent getComponent() {
    return component;
  }
}
