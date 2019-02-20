package com.example.xyzreader.ui.detail;

import com.example.xyzreader.data.DataManager;
import com.example.xyzreader.data.model.Article;
import com.example.xyzreader.ui.base.BaseViewModel;
import com.example.xyzreader.utils.SchedulerProvider;
import com.jakewharton.rxrelay2.BehaviorRelay;

import io.reactivex.Single;

public class DetailActivityViewModel extends BaseViewModel {

    public DetailActivityViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, BehaviorRelay<Integer> requestState) {
        super(dataManager, schedulerProvider, requestState);
    }

    public Single<Article> loadArticle(int id) {
        return getDataManager().getArticleById(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui());
    }
}
