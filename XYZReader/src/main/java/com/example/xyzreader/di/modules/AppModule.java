package com.example.xyzreader.di.modules;

import static com.example.xyzreader.utils.Constants.DATABASE_NAME;
import static com.example.xyzreader.utils.Constants.UDACITY_URL;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import com.example.xyzreader.data.RequestState;
import com.example.xyzreader.data.local.ArticleDao;
import com.example.xyzreader.data.local.ArticleDatabase;
import com.example.xyzreader.data.local.Repository;
import com.example.xyzreader.data.remote.ArticleService;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

  @Provides
  @Singleton
  public Context provideContext(Application application) {
    return application;
  }

  @Provides
  @Singleton
  public Picasso providePicasso(Context context) {
    return new Picasso.Builder(context).build();
  }

  @Provides
  @Singleton
  public ArticleDatabase provideDatabase(Context context) {
    return Room.databaseBuilder(context, ArticleDatabase.class, DATABASE_NAME).build();
  }

  @Provides
  @Singleton
  public ArticleDao provideDao(ArticleDatabase articleDatabase) {
    return articleDatabase.articleDao();
  }

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

  @Provides
  @Singleton
  public Repository provideRepository(ArticleDao articleDao, ArticleService articleService,
      BehaviorRelay<Integer> behaviorRelay) {
    return new Repository(articleDao, articleService, behaviorRelay);
  }
}
