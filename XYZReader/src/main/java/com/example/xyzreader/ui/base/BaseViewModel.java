package com.example.xyzreader.ui.base;

import android.arch.lifecycle.ViewModel;

import com.example.xyzreader.data.DataManager;
import com.example.xyzreader.utils.SchedulerProvider;
import com.jakewharton.rxrelay2.BehaviorRelay;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel {

    private DataManager dataManager;
    private SchedulerProvider schedulerProvider;
    private CompositeDisposable disposable;
    private BehaviorRelay<Integer> requestState;

    public BaseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, BehaviorRelay<Integer> requestState) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
        this.requestState = requestState;
        disposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }

    public CompositeDisposable getDisposable() {
        return disposable;
    }

    public BehaviorRelay<Integer> getRequestState() {
        return requestState;
    }
}
