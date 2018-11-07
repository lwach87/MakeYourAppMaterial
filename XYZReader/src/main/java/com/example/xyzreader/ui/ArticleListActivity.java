package com.example.xyzreader.ui;

import static android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL;
import static com.example.xyzreader.utils.Constants.ARTICLE_ID;
import static com.example.xyzreader.utils.RxUtils.getSyncObserver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.xyzreader.R;
import com.example.xyzreader.data.RequestState;
import com.example.xyzreader.data.local.Repository;
import com.example.xyzreader.ui.Adapter.OnClickListener;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;
import timber.log.Timber;


public class ArticleListActivity extends AppCompatActivity implements OnRefreshListener,
    OnClickListener {

  @Inject
  Repository repository;

  @Inject
  Adapter adapter;

  @BindInt(R.integer.list_column_count)
  int columnCount;

  @BindView(R.id.toolbar)
  Toolbar articleToolbar;

  @BindView(R.id.swipe_refresh_layout)
  SwipeRefreshLayout swipeRefreshLayout;

  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;

  private CompositeDisposable disposable = new CompositeDisposable();
  private CompositeDisposable statusDisposable = new CompositeDisposable();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_article_list);
    ButterKnife.bind(this);
    setSupportActionBar(articleToolbar);

    AndroidInjection.inject(this);

    adapter.setOnClickListener(this);
    swipeRefreshLayout.setOnRefreshListener(this);
    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(columnCount, VERTICAL));
    recyclerView.setAdapter(adapter);

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

  @Override
  public void onArticleClick(int id) {
    Intent intent = new Intent(this, ArticleDetailActivity.class);
    intent.putExtra(ARTICLE_ID, id);
    startActivity(intent);
  }

  private void handleLoadingStatus() {
    statusDisposable.add(
        repository.getRequestState().subscribe(state -> {
          switch (state) {
            case RequestState.IDLE:
              break;
            case RequestState.LOADING:
              swipeRefreshLayout.setRefreshing(true);
              break;
            case RequestState.COMPLETED:
              swipeRefreshLayout.setRefreshing(false);
              break;
            case RequestState.ERROR:
              swipeRefreshLayout.setRefreshing(false);
              break;
          }
        })
    );
  }

  private void getArticlesFromDatabase() {
    disposable.add(
        repository.getArticlesFromDatabase()
            .subscribe(
                articles -> adapter.swapData(articles),
                error -> Timber.d("Articles loading - error: %s", error.getLocalizedMessage())
            )
    );
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    disposable.clear();
    statusDisposable.clear();
  }
}
