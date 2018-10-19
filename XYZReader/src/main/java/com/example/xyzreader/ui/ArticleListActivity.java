package com.example.xyzreader.ui;

import static android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.xyzreader.ArticleApp;
import com.example.xyzreader.R;
import com.example.xyzreader.data.RequestState;
import com.example.xyzreader.data.local.Repository;
import com.example.xyzreader.di.components.DaggerArticleListActivityComponent;
import com.example.xyzreader.di.modules.ArticleListActivityModule;
import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;
import timber.log.Timber;


public class ArticleListActivity extends AppCompatActivity implements OnRefreshListener {

  @Inject
  Repository repository;

  @Inject
  Adapter adapter;

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.swipe_refresh_layout)
  SwipeRefreshLayout mSwipeRefreshLayout;

  @BindView(R.id.recycler_view)
  RecyclerView mRecyclerView;

  private CompositeDisposable disposable = new CompositeDisposable();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_article_list);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);

    DaggerArticleListActivityComponent.builder()
        .applicationComponent(ArticleApp.get(this).getComponent())
        .articleListActivityModule(new ArticleListActivityModule(this))
        .build().inject(this);

    mSwipeRefreshLayout.setOnRefreshListener(this);
    mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, VERTICAL));
    mRecyclerView.setAdapter(adapter);

    getArticlesFromDatabase();
    handleLoadingStatus();

    if (savedInstanceState == null) {
      repository.syncArticles().subscribe(getSyncObserver());
    }
  }

  @Override
  public void onRefresh() {
    repository.syncArticles().subscribe(getSyncObserver());
  }

  private void handleLoadingStatus() {
    repository.getRequestState().subscribe(state -> {
      switch (state) {
        case RequestState.IDLE:
          break;
        case RequestState.LOADING:
          mSwipeRefreshLayout.setRefreshing(true);
          break;
        case RequestState.COMPLETED:
          mSwipeRefreshLayout.setRefreshing(false);
          break;
        case RequestState.ERROR:
          mSwipeRefreshLayout.setRefreshing(false);
          break;
      }
    });
  }

  private void getArticlesFromDatabase() {
    disposable.add(
        repository.getArticlesFromDatabase()
            .subscribe(
                article -> adapter.swapData(article),
                error -> Timber.d("Articles loading - error: " + error.getMessage()),
                () -> Timber.d("onComplete")
//                subscription -> adapter.clearData()
            ));
  }

  private CompletableObserver getSyncObserver() {
    return new CompletableObserver() {
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        Timber.d("Sync started...");
      }

      @Override
      public void onComplete() {
        Timber.d("Sync finished...");
      }

      @Override
      public void onError(@NonNull Throwable e) {
        Timber.d("Sync failed! Error: " + e.getMessage());
      }
    };
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    disposable.clear();
  }
}
