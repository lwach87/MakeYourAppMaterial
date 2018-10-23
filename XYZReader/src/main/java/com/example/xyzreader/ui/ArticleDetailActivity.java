package com.example.xyzreader.ui;

import static com.example.xyzreader.utils.Constants.ARTICLE_ID;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.NestedScrollView.OnScrollChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
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
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;
import timber.log.Timber;

public class ArticleDetailActivity extends AppCompatActivity implements OnClickListener {

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

  @BindView(R.id.nested_scroll_view)
  NestedScrollView nestedScrollView;

  @BindView(R.id.article_title)
  TextView articleTitleView;

  @BindView(R.id.article_subtitle)
  TextView articleSubtitleView;

  @BindView(R.id.article_body)
  TextView articleBodyView;

  @BindView(R.id.share_fab)
  FloatingActionButton shareFab;

  private int id;
  private boolean showTitleInToolbar = true;
  private CompositeDisposable disposable = new CompositeDisposable();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_article_detail);
    ButterKnife.bind(this);

    ((ArticleApp) getApplication()).getComponent().inject(this);

    id = getIntent().getIntExtra(ARTICLE_ID, 0);
    shareFab.setOnClickListener(this);

    if (detailsToolbar != null) {
      setSupportActionBar(detailsToolbar);
      getSupportActionBar().setDisplayShowTitleEnabled(false);

      detailsToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
      detailsToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    nestedScrollView.setOnScrollChangeListener(
        (OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

          boolean scrollingDirection = scrollY > oldScrollY;

          if (scrollingDirection & shareFab.isShown()) {
            shareFab.hide();
          } else {
            shareFab.show();
          }
        }
    );

    loadArticle();
  }

  private void loadArticle() {
    disposable.add(
        repository.getArticleById(id)
            .doOnSubscribe(disposable -> supportPostponeEnterTransition())
            .doFinally(this::supportPostponeEnterTransition)
            .subscribe(this::updateActivityLayout,
                error -> Timber.d("Single article loading error!%s", error.getLocalizedMessage())
            )
    );
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

  @Override
  public void onClick(View v) {
    String sharedText = articleBodyView.getText().toString();

    startActivity(
        Intent.createChooser(ShareCompat.IntentBuilder.from(ArticleDetailActivity.this)
            .setType("text/plain")
            .setText(sharedText.substring(0, Math.min(sharedText.length(), 100)))
            .getIntent(), "Share")
    );
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    disposable.clear();
  }
}
