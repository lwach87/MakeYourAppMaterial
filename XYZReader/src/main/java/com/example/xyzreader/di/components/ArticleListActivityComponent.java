package com.example.xyzreader.di.components;

import com.example.xyzreader.di.MainActivityScope;
import com.example.xyzreader.di.components.ApplicationComponent;
import com.example.xyzreader.di.modules.MainActivityModule;
import com.example.xyzreader.ui.ArticleListActivity;
import dagger.Component;

@MainActivityScope
@Component(modules = MainActivityModule.class, dependencies = ApplicationComponent.class)
public interface ArticleListActivityComponent {

  void inject(ArticleListActivity activity);
}
