package com.example.xyzreader.data.local;

import com.example.xyzreader.data.model.Article;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Singleton
public class AppDbHelper implements DbHelper {

    private ArticleDatabase articleDatabase;

    @Inject
    public AppDbHelper(ArticleDatabase articleDatabase) {
        this.articleDatabase = articleDatabase;
    }

    @Override
    public Flowable<List<Article>> getAllArticles() {
        return Flowable.fromCallable(() -> articleDatabase.articleDao().getAllArticles());
    }

    @Override
    public Single<Article> getArticleById(int id) {
        return Single.fromCallable(() -> articleDatabase.articleDao().getArticleById(id));
    }

    @Override
    public void insertArticles(List<Article> article) {
        articleDatabase.articleDao().insertArticles(article);
    }
}
