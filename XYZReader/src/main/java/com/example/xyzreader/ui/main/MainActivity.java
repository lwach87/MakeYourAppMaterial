package com.example.xyzreader.ui.main;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.example.xyzreader.R;
import com.example.xyzreader.ui.base.BaseActivity;
import com.example.xyzreader.ui.detail.DetailActivity;
import com.example.xyzreader.ui.main.Adapter.OnClickListener;

import javax.inject.Inject;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL;
import static com.example.xyzreader.utils.Constants.ARTICLE_ID;
import static com.example.xyzreader.utils.RxUtils.getSyncObserver;


public class MainActivity extends BaseActivity implements OnRefreshListener,
        OnClickListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

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

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        ButterKnife.bind(this);
        setSupportActionBar(articleToolbar);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel.class);

        adapter.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(columnCount, VERTICAL));
        recyclerView.setAdapter(adapter);

        if (savedInstanceState == null) {
            viewModel.syncArticles().subscribe(getSyncObserver());
        }

        viewModel.handleLoadingStatus(swipeRefreshLayout, this);
        viewModel.getArticlesFromDatabase();
        subscribeToLiveData();
    }

    @Override
    public void onRefresh() {
        viewModel.syncArticles().subscribe(getSyncObserver());
    }

    @Override
    public void onArticleClick(int id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(ARTICLE_ID, id);
        startActivity(intent);
    }

    private void subscribeToLiveData() {
        viewModel.getArticleLiveData().observe(this, articles -> adapter.swapData(articles));
    }
}
