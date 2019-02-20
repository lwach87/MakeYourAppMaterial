package com.example.xyzreader.data.remote;

import com.example.xyzreader.data.model.Article;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiHelper {

    @GET("/xyz-reader-json")
    Observable<List<Article>> getArticlesFromServer();
}
