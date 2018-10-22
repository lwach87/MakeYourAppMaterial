package com.example.xyzreader.ui;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.xyzreader.ArticleApp;
import com.example.xyzreader.R;
import com.example.xyzreader.data.local.Repository;
import com.example.xyzreader.data.model.Article;
import com.example.xyzreader.utils.StringUtils;
import com.squareup.picasso.Picasso;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;
import timber.log.Timber;

public class ArticleDetailActivity extends AppCompatActivity {

  @Inject
  Repository repository;

  @Inject
  Picasso picasso;

  @BindView(R.id.details_toolbar)
  Toolbar detailsToolbar;

  @BindView(R.id.details_toolbar_collapsing)
  CollapsingToolbarLayout detailsToolbarCollapsing;

  @BindView(R.id.photo)
  ImageView photoView;

  @BindView(R.id.article_title)
  TextView articleTitleView;

  @BindView(R.id.article_subtitle)
  TextView articleSubtitleView;

  @BindView(R.id.article_body)
  TextView articleBodyView;

  private int id;
  private boolean showTitleInToolbar = true;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_article_detail);
    ButterKnife.bind(this);
    setSupportActionBar(detailsToolbar);

    ((ArticleApp) getApplication()).getComponent().inject(this);

    id = getIntent().getIntExtra("id", 0);

    loadArticle();
  }

  private void loadArticle() {
    repository.getArticleById(id)
        .doOnSubscribe(disposable -> supportPostponeEnterTransition())
        .doFinally(this::supportPostponeEnterTransition)
        .subscribe(getSingleObserver());
  }

  private void updateActivityLayout(Article article) {
    String title = article.title;

    String subtitle = StringUtils.getFormattedDetailsSubtitle(
        article.published_date,
        article.author);

    if (detailsToolbarCollapsing != null) {

      if (showTitleInToolbar) {
        detailsToolbarCollapsing.setTitle(title);
      } else {
        articleTitleView.setText(title);
      }
      articleSubtitleView.setText(subtitle);
    }

    articleBodyView.setText(StringUtils.getFormattedBookText(article.body));

    picasso.load(article.photo)
        .into(photoView);
  }

  private SingleObserver<Article> getSingleObserver() {
    return new SingleObserver<Article>() {
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        Timber.d("Single article loading started, id: %s", id);
      }

      @Override
      public void onSuccess(@NonNull Article article) {
        updateActivityLayout(article);
        Timber.d("Single article loading finished.");
      }

      @Override
      public void onError(@NonNull Throwable e) {
        Timber.d("Single article loading error!");
      }
    };
  }
}
