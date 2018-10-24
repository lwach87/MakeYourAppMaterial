package com.example.xyzreader.di.modules;

import static com.example.xyzreader.utils.Constants.UDACITY_URL;

import com.example.xyzreader.data.RequestState;
import com.example.xyzreader.data.remote.ArticleService;
import com.jakewharton.rxrelay2.BehaviorRelay;
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
  public Retrofit provideRetrofit() {
    return new Retrofit.Builder()
        .baseUrl(UDACITY_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
  }

  @Provides
  @Singleton
  public ArticleService provideService(Retrofit retrofit) {
    return retrofit.create(ArticleService.class);
  }

  @Provides
  @Singleton
  public BehaviorRelay<Integer> provideBehaviourRelay() {
    return BehaviorRelay.createDefault(RequestState.IDLE);
  }
}
