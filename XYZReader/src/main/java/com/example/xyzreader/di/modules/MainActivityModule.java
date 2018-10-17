package com.example.xyzreader.di.modules;

import com.example.xyzreader.di.MainActivityScope;
import com.example.xyzreader.ui.Adapter;
import com.example.xyzreader.ui.ArticleListActivity;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

  private ArticleListActivity listActivity;

  public MainActivityModule(ArticleListActivity listActivity) {
    this.listActivity = listActivity;
  }

  @Provides
  @MainActivityScope
  Adapter provideAdapter(Picasso picasso) {
    return new Adapter(picasso, listActivity);
  }
}
