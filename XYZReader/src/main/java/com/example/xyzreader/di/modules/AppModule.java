package com.example.xyzreader.di.modules;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.xyzreader.data.AppDataManager;
import com.example.xyzreader.data.DataManager;
import com.example.xyzreader.data.RequestState;
import com.example.xyzreader.data.local.AppDbHelper;
import com.example.xyzreader.data.local.ArticleDao;
import com.example.xyzreader.data.local.ArticleDatabase;
import com.example.xyzreader.data.local.DbHelper;
import com.example.xyzreader.data.remote.ApiHelper;
import com.example.xyzreader.utils.AppSchedulerProvider;
import com.example.xyzreader.utils.SchedulerProvider;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.xyzreader.utils.Constants.DATABASE_NAME;
import static com.example.xyzreader.utils.Constants.UDACITY_URL;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    Picasso providePicasso(Context context) {
        return new Picasso.Builder(context).build();
    }

    @Provides
    @Singleton
    ArticleDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(context, ArticleDatabase.class, DATABASE_NAME).build();
    }

    @Provides
    @Singleton
    ArticleDao provideDao(ArticleDatabase articleDatabase) {
        return articleDatabase.articleDao();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(UDACITY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ApiHelper provideService(Retrofit retrofit) {
        return retrofit.create(ApiHelper.class);
    }

    @Provides
    @Singleton
    BehaviorRelay<Integer> provideBehaviourRelay() {
        return BehaviorRelay.createDefault(RequestState.IDLE);
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }
}
