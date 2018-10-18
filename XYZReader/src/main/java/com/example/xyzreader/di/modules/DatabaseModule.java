package com.example.xyzreader.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;
import com.example.xyzreader.data.local.ArticleDao;
import com.example.xyzreader.data.local.ArticleDatabase;
import com.example.xyzreader.data.local.Repository;
import com.example.xyzreader.data.remote.ArticleService;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class DatabaseModule {

  private Context context;

  public DatabaseModule(Context context) {
    this.context = context;
  }

  @Provides
  @Singleton
  public Context provideContext() {
    return context;
  }

  @Provides
  @Singleton
  public Picasso providePicasso(Context context) {
    return new Picasso.Builder(context).build();
  }

  @Provides
  @Singleton
  public ArticleDatabase provideDatabase(Context context) {
    return Room.databaseBuilder(context, ArticleDatabase.class, "ArticleDatabase").build();
  }

  @Provides
  @Singleton
  public ArticleDao provideDao(ArticleDatabase articleDatabase) {
    return articleDatabase.articleDao();
  }

  @Provides
  @Singleton
  public Repository provideRepository(ArticleDao articleDao, ArticleService articleService,
      BehaviorRelay<Integer> behaviorRelay) {
    return new Repository(articleDao, articleService, behaviorRelay);
  }
}
