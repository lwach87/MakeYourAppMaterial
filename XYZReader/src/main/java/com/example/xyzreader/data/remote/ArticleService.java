package com.example.xyzreader.data.remote;

import com.example.xyzreader.data.model.Article;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.GET;

public interface ArticleService {

  @GET("/xyz-reader-json")
  Observable<List<Article>> getArticlesFromServer();
}
