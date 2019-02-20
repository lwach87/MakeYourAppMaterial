package com.example.xyzreader.ui.main;

import android.arch.lifecycle.ViewModelProvider;

import com.example.xyzreader.ViewModelProviderFactory;
import com.example.xyzreader.data.DataManager;
import com.example.xyzreader.utils.SchedulerProvider;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    Adapter provideAdapter(Picasso picasso, MainActivity listActivity) {
        return new Adapter(picasso, listActivity);
    }

    @Provides
    MainActivityViewModel provideMainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, BehaviorRelay<Integer> requestState) {
        return new MainActivityViewModel(dataManager, schedulerProvider, requestState);
    }

    @Provides
    ViewModelProvider.Factory mainViewModelProvider(MainActivityViewModel mainViewModel) {
        return new ViewModelProviderFactory<>(mainViewModel);
    }
}
