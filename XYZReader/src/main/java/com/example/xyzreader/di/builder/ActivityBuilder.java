package com.example.xyzreader.di.builder;

import com.example.xyzreader.di.modules.ArticleListActivityModule;
import com.example.xyzreader.ui.ArticleDetailActivity;
import com.example.xyzreader.ui.ArticleListActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

  @ContributesAndroidInjector(modules = ArticleListActivityModule.class)
  abstract ArticleListActivity bindArticleListActivity();

  @ContributesAndroidInjector()
  abstract ArticleDetailActivity bindArticleDetailActivity();
}
