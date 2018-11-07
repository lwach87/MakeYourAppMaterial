package com.example.xyzreader.di.modules;

import com.example.xyzreader.ui.Adapter;
import com.example.xyzreader.ui.ArticleListActivity;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;

@Module
public class ArticleListActivityModule {

  @Provides
  public Adapter provideAdapter(Picasso picasso, ArticleListActivity listActivity) {
    return new Adapter(picasso, listActivity);
  }
}
