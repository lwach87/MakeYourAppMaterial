package com.example.xyzreader.di.components;

import com.example.xyzreader.di.ArticleActivityScope;
import com.example.xyzreader.di.modules.ArticleListActivityModule;
import com.example.xyzreader.ui.ArticleListActivity;
import dagger.Component;

@ArticleActivityScope
@Component(modules = ArticleListActivityModule.class, dependencies = ApplicationComponent.class)
public interface ArticleListActivityComponent {

  void inject(ArticleListActivity activity);
}
