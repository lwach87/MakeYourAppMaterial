package com.example.xyzreader.ui.main;

import com.example.xyzreader.data.DataManager;
import com.example.xyzreader.ui.base.BaseViewModel;
import com.example.xyzreader.utils.SchedulerProvider;
import com.jakewharton.rxrelay2.BehaviorRelay;

public class MainActivityViewModel extends BaseViewModel {

    public MainActivityViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, BehaviorRelay<Integer> requestState) {
        super(dataManager, schedulerProvider, requestState);
    }
}
