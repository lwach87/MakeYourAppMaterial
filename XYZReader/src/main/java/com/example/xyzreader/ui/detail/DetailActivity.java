package com.example.xyzreader.ui.detail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.NestedScrollView.OnScrollChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xyzreader.R;
import com.example.xyzreader.data.model.Article;
import com.example.xyzreader.ui.base.BaseActivity;
import com.example.xyzreader.utils.StringUtils;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.xyzreader.utils.Constants.ARTICLE_ID;

public class DetailActivity extends BaseActivity implements OnClickListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

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

    @BindBool(R.bool.show_title_in_toolbar)
    boolean showTitleInToolbar;

    private DetailActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailActivityViewModel.class);

        int id = getIntent().getIntExtra(ARTICLE_ID, 0);
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

                    if (scrollingDirection && shareFab.isShown()) {
                        shareFab.hide();
                    } else {
                        shareFab.show();
                    }
                }
        );

        viewModel.loadArticle(id, this);
        subscribeToLiveData();
    }

    private void subscribeToLiveData() {
        viewModel.getMutableLiveData().observe(this, this::updateActivityLayout);
    }

    private void updateActivityLayout(Article article) {
        String title = article.getTitle();

        String subtitle = StringUtils.getFormattedDetailsSubtitle(
                article.getPublished_date(),
                article.getAuthor());

        if (detailsToolbarCollapsing != null) {

            if (showTitleInToolbar) {
                detailsToolbarCollapsing.setTitle(title);
            } else {
                articleTitleView.setText(title);
            }
            articleSubtitleView.setText(subtitle);
        }

        articleBodyView.setText(StringUtils.getFormattedBookText(article.getBody()));

        picasso.load(article.getPhoto())
                .into(photoView);
    }

    @Override
    public void onClick(View v) {
        String sharedText = articleBodyView.getText().toString();

        startActivity(
                Intent.createChooser(ShareCompat.IntentBuilder.from(DetailActivity.this)
                        .setType("text/plain")
                        .setText(sharedText.substring(0, Math.min(sharedText.length(), 100)))
                        .getIntent(), "Share")
        );
    }
}
