package com.example.xyzreader.di.modules;

import com.example.xyzreader.data.remote.ArticleService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ArticleServiceModule {

  @Provides
  @Singleton
  Retrofit provideRetrofit() {
    return new Retrofit.Builder()
        .baseUrl("https://go.udacity.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
  }

  @Provides
  @Singleton
  ArticleService provideService(Retrofit retrofit) {
    return retrofit.create(ArticleService.class);
  }
}
