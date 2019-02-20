package com.example.xyzreader.data.local;

import com.example.xyzreader.data.RequestState;
import com.example.xyzreader.data.model.Article;
import com.example.xyzreader.data.remote.ApiHelper;
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
  private ApiHelper apiHelper;
  private BehaviorRelay<Integer> requestState;

  public Repository(ArticleDao articleDao, ApiHelper apiHelper,
      BehaviorRelay<Integer> requestState) {
    this.articleDao = articleDao;
    this.apiHelper = apiHelper;
    this.requestState = requestState;
  }

  private void publishRequestState(@RequestState.State int state) {
    Observable.just(state)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(requestState);
  }

  public Completable syncArticles() {
    return apiHelper
        .getArticlesFromServer()
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(disposable -> publishRequestState(RequestState.LOADING))
        .doOnError(error -> publishRequestState(RequestState.ERROR))
        .doOnComplete(() -> publishRequestState(RequestState.COMPLETED))
        .flatMapCompletable(
            articles -> Completable.create(emitter -> articleDao.insertArticles(articles))
        );
  }

  public Flowable<List<Article>> getArticlesFromDatabase() {
    return articleDao
        .getAllArticles()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
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
