package com.example.xyzreader.di.components;

import com.example.xyzreader.data.local.Repository;
import com.example.xyzreader.di.modules.ArticleServiceModule;
import com.example.xyzreader.di.modules.DatabaseModule;
import com.squareup.picasso.Picasso;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {DatabaseModule.class, ArticleServiceModule.class})
public interface ApplicationComponent {

  Picasso getPicasso();

  Repository getRepository();
}
