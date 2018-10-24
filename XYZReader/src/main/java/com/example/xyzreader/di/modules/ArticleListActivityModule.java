package com.example.xyzreader.di.modules;

import com.example.xyzreader.di.ActivityScope;
import com.example.xyzreader.ui.Adapter;
import com.example.xyzreader.ui.ArticleListActivity;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;

@Module
public class ArticleListActivityModule {

  private ArticleListActivity listActivity;

  public ArticleListActivityModule(ArticleListActivity listActivity) {
    this.listActivity = listActivity;
  }

  @Provides
  @ActivityScope
  public Adapter provideAdapter(Picasso picasso) {
    return new Adapter(picasso, listActivity);
  }
}
