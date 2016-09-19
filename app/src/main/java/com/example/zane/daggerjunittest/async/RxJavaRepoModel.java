package com.example.zane.daggerjunittest.async;



import android.view.View;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zane on 16/9/18.
 * Email: zanebot96@gmail.com
 */

//使用Rxjava模拟异步
public class RxJavaRepoModel {

    public Observable<List<String>> loadRepos(){
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                try {
                    Thread.sleep(1000);
                    final List<String> repos = new ArrayList<String>();
                    repos.add("sbsbsb");
                    if (!subscriber.isUnsubscribed()){
                        subscriber.onNext(repos);
                        subscriber.onCompleted();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    if (!subscriber.isUnsubscribed()){
                        subscriber.onError(e);
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
