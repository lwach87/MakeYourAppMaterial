package com.example.xyzreader.data;

import com.example.xyzreader.data.local.DbHelper;
import com.example.xyzreader.data.model.Article;
import com.example.xyzreader.data.remote.ApiHelper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class AppDataManager implements DataManager {

    private DbHelper dbHelper;
    private ApiHelper apiHelper;

    @Inject
    public AppDataManager(DbHelper dbHelper, ApiHelper apiHelper) {
        this.dbHelper = dbHelper;
        this.apiHelper = apiHelper;
    }

    @Override
    public Flowable<List<Article>> getAllArticles() {
        return dbHelper.getAllArticles();
    }

    @Override
    public Single<Article> getArticleById(int id) {
        return dbHelper.getArticleById(id);
    }

    @Override
    public void insertArticles(List<Article> article) {
        dbHelper.insertArticles(article);
    }

    @Override
    public Observable<List<Article>> getArticlesFromServer() {
        return apiHelper.getArticlesFromServer();
    }
}
