package com.example.xyzreader.ui.detail;

import android.arch.lifecycle.MutableLiveData;
import android.support.v4.app.FragmentActivity;

import com.example.xyzreader.data.DataManager;
import com.example.xyzreader.data.model.Article;
import com.example.xyzreader.ui.base.BaseViewModel;
import com.example.xyzreader.utils.SchedulerProvider;
import com.jakewharton.rxrelay2.BehaviorRelay;

import timber.log.Timber;

public class DetailActivityViewModel extends BaseViewModel {

    private MutableLiveData<Article> mutableLiveData;

    public DetailActivityViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, BehaviorRelay<Integer> requestState) {
        super(dataManager, schedulerProvider, requestState);
        mutableLiveData = new MutableLiveData<>();
    }

    public void loadArticle(int id, FragmentActivity activity) {
        getDisposable().add(getDataManager().getArticleById(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .doOnSubscribe(disposable -> activity.supportPostponeEnterTransition())
                .doFinally(activity::supportPostponeEnterTransition)
                .subscribe(article -> mutableLiveData.setValue(article),
                        throwable -> Timber.d("Single article loading error!%s", throwable.getLocalizedMessage())));
    }

    public MutableLiveData<Article> getMutableLiveData() {
        return mutableLiveData;
    }
}
