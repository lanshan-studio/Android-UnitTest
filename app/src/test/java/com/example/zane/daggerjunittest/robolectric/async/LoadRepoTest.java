package com.example.zane.daggerjunittest.robolectric.async;

import com.example.zane.daggerjunittest.async.RepoModel;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * Created by Zane on 16/9/17.
 * Email: zanebot96@gmail.com
 */

public class LoadRepoTest {

    private RepoModel module;
    private CountDownLatch countDownLatch;
    private List<String> mRepos;

    @Before
    public void setup(){
        module = new RepoModel();
        mRepos = new ArrayList<>();
        countDownLatch = new CountDownLatch(1);
    }

    @Test
    public void testLoadRepo() throws Exception{
        module.loadRepos();
        module.setLoadListener(new RepoModel.LoadListener() {
            @Override
            public void onSuccess(List<String> repos) {
                mRepos.addAll(repos);
                countDownLatch.countDown();
            }

            @Override
            public void onFail(int code, String message) {
                fail();
            }
        });
        countDownLatch.await();
        assertEquals(1, mRepos.size());
    }
}
