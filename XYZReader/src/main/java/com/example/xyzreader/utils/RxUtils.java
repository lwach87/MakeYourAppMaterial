package com.example.xyzreader.utils;

import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class RxUtils {

    public static CompletableObserver getSyncObserver() {
        return new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Timber.d("Sync started...");
            }

            @Override
            public void onComplete() {
                Timber.d("Sync finished...");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.d("Sync failed! Error: %s", e.getMessage());
            }
        };
    }
}
