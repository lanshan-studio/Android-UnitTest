package com.example.zane.daggerjunittest.robolectric.async;

import com.example.zane.daggerjunittest.async.RxJavaRepoModel;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import rx.Scheduler;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Zane on 16/9/18.
 * Email: zanebot96@gmail.com
 */

public class RxjavaLoadRepoTest {

    private RxJavaRepoModel model;

    @Before
    public void setup() {
        model = new RxJavaRepoModel();
    }

    @Test
    public void testRxjavaRepos() {
        //自定义调度器插件,使操作变成同步的
        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook(){
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }
        });

        final List<String> mRepos = new ArrayList<>();
        model.loadRepos().subscribe(repos -> mRepos.addAll(repos));

        assertEquals(1, mRepos.size());
    }
}
