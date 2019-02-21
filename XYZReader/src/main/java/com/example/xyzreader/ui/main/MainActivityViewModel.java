package com.example.xyzreader.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.example.xyzreader.data.DataManager;
import com.example.xyzreader.data.RequestState;
import com.example.xyzreader.data.model.Article;
import com.example.xyzreader.ui.base.BaseViewModel;
import com.example.xyzreader.utils.NetworkUtils;
import com.example.xyzreader.utils.SchedulerProvider;
import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivityViewModel extends BaseViewModel {

    private MutableLiveData<List<Article>> articleLiveData;

    public MainActivityViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, BehaviorRelay<Integer> requestState) {
        super(dataManager, schedulerProvider, requestState);
        articleLiveData = new MutableLiveData<>();
    }

    public void handleLoadingStatus(SwipeRefreshLayout refreshLayout, Context context) {
        getDisposable().add(
                getRequestState().subscribe(state -> {
                    switch (state) {
                        case RequestState.IDLE:
                            break;
                        case RequestState.LOADING:
                            refreshLayout.setRefreshing(true);
                            break;
                        case RequestState.COMPLETED:
                            refreshLayout.setRefreshing(false);
                            break;
                        case RequestState.ERROR:
                            showErrorMessage(context);
                            refreshLayout.setRefreshing(false);
                            break;
                    }
                })
        );
    }

    private void showErrorMessage(Context context) {
        String message = NetworkUtils.isNetworkConnected(context) ?
                "Unknown error occurred! Please try again later." :
                "Articles server is not available! Please try again later.";

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void syncArticles() {
        getDisposable().add(getDataManager()
                .getArticlesFromServer()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> publishRequestState(RequestState.LOADING))
                .doOnError(error -> publishRequestState(RequestState.ERROR))
                .doOnComplete(() -> publishRequestState(RequestState.COMPLETED))
                .flatMapCompletable(
                        articles -> Completable.create(emitter -> getDataManager().insertArticles(articles))
                ).subscribe(() -> Timber.d("Sync finished..."),
                        throwable -> Timber.d("Sync failed! Error: %s", throwable.getMessage())));
    }

    private void publishRequestState(@RequestState.State int state) {
        Observable.just(state)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(getRequestState());
    }

    public void getArticlesFromDatabase() {
        getDisposable().add(getDataManager()
                .getAllArticles()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(
                        articles -> articleLiveData.setValue(articles),
                        error -> Timber.d("Articles loading - error: %s", error.getLocalizedMessage())
                )
        );
    }

    public MutableLiveData<List<Article>> getArticleLiveData() {
        return articleLiveData;
    }
}
