package com.example.xyzreader.di.builder;

import com.example.xyzreader.ui.main.MainActivityModule;
import com.example.xyzreader.ui.ArticleDetailActivity;
import com.example.xyzreader.ui.main.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

  @ContributesAndroidInjector(modules = MainActivityModule.class)
  abstract MainActivity bindArticleListActivity();

  @ContributesAndroidInjector()
  abstract ArticleDetailActivity bindArticleDetailActivity();
}
