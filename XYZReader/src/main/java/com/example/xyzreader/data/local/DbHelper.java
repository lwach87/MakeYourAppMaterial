package com.example.xyzreader.data.local;

import com.example.xyzreader.data.model.Article;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface DbHelper {

    Flowable<List<Article>> getAllArticles();

    Single<Article> getArticleById(int id);

    void insertArticles(List<Article> article);
}
