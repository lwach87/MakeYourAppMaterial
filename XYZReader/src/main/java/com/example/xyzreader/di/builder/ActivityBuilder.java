package com.example.xyzreader.di.builder;

import com.example.xyzreader.ui.detail.DetailActivity;
import com.example.xyzreader.ui.detail.DetailActivityModule;
import com.example.xyzreader.ui.main.MainActivity;
import com.example.xyzreader.ui.main.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity bindArticleListActivity();

    @ContributesAndroidInjector(modules = DetailActivityModule.class)
    abstract DetailActivity bindArticleDetailActivity();
}
