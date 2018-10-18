package com.example.xyzreader.data.local;

import com.example.xyzreader.data.RequestState;
import com.example.xyzreader.data.model.Article;
import com.example.xyzreader.data.remote.ArticleService;
import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class Repository {

  private ArticleDao articleDao;
  private ArticleService articleService;
  private BehaviorRelay<Integer> requestState;

  public Repository(ArticleDao articleDao, ArticleService articleService,
      BehaviorRelay<Integer> requestState) {
    this.articleDao = articleDao;
    this.articleService = articleService;
    this.requestState = requestState;
  }

  private void publishRequestState(@RequestState.State int state) {
    Observable.just(state)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(requestState);
  }

  public Completable syncArticles() {
    return articleService
        .getArticlesFromServer()
        .observeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(disposable -> publishRequestState(RequestState.LOADING))
        .doOnError(error -> publishRequestState(RequestState.ERROR))
        .doOnComplete(() -> publishRequestState(RequestState.COMPLETED))
        .flatMapCompletable(this::saveArticlesToDatabase);
  }

  private Completable saveArticlesToDatabase(List<Article> articles) {
    return Completable.create(emitter -> {
          articleDao.deleteArticle();
          articleDao.insertArticles(articles);
        }
    );
  }

  public Flowable<Article> getArticleFromDatabase() {
    return articleDao
        .getAllArticles()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .flatMap(Flowable::fromIterable);
  }

  public Single<Article> getArticleById(int id) {
    return articleDao
        .getArticleById(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public BehaviorRelay<Integer> getRequestState() {
    return requestState;
  }
}