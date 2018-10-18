package com.example.xyzreader.di.modules;

import com.example.xyzreader.di.ArticleActivityScope;
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
  @ArticleActivityScope
  public Adapter provideAdapter(Picasso picasso) {
    return new Adapter(picasso, listActivity);
  }
}
