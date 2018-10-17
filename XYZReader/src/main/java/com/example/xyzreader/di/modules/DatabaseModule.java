package com.example.xyzreader.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;
import com.example.xyzreader.data.local.ArticleDao;
import com.example.xyzreader.data.local.ArticleDatabase;
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
  Context provideContext() {
    return context;
  }

  @Provides
  @Singleton
  Picasso providePicasso(Context context) {
    return new Picasso.Builder(context).build();
  }

  @Provides
  @Singleton
  ArticleDatabase provideDatabase(Context context) {
    return Room.databaseBuilder(context, ArticleDatabase.class, "ArticleDatabase").build();
  }

  @Provides
  @Singleton
  ArticleDao provideDao(ArticleDatabase articleDatabase) {
    return articleDatabase.articleDao();
  }
}
