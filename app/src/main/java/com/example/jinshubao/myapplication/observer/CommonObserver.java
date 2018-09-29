package com.example.jinshubao.myapplication.observer;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CommonObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T o) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
