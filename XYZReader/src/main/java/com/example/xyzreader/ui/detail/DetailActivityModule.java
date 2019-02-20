package com.example.xyzreader.ui.detail;

import android.arch.lifecycle.ViewModelProvider;

import com.example.xyzreader.ViewModelProviderFactory;
import com.example.xyzreader.data.DataManager;
import com.example.xyzreader.utils.SchedulerProvider;
import com.jakewharton.rxrelay2.BehaviorRelay;

import dagger.Module;
import dagger.Provides;

@Module
public class DetailActivityModule {

    @Provides
    DetailActivityViewModel provideDetailViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, BehaviorRelay<Integer> requestState) {
        return new DetailActivityViewModel(dataManager, schedulerProvider, requestState);
    }

    @Provides
    ViewModelProvider.Factory mainViewModelProvider(DetailActivityViewModel detailViewModel) {
        return new ViewModelProviderFactory<>(detailViewModel);
    }
}
