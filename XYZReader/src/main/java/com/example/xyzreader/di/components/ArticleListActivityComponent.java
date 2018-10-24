package com.example.xyzreader.di.components;

import com.example.xyzreader.di.ActivityScope;
import com.example.xyzreader.di.modules.ArticleListActivityModule;
import com.example.xyzreader.ui.ArticleListActivity;
import dagger.Component;

@ActivityScope
@Component(modules = ArticleListActivityModule.class, dependencies = ApplicationComponent.class)
public interface ArticleListActivityComponent {

  void inject(ArticleListActivity activity);
}
