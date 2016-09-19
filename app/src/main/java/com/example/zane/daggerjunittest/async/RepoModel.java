package com.example.zane.daggerjunittest.async;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Zane on 16/9/17.
 * Email: zanebot96@gmail.com
 */

//模拟异步加载数据
public class RepoModel {

    private LoadListener listener;

    public interface LoadListener{
        void onSuccess(List<String> repos);
        void onFail(int code, String message);
    }

    public void setLoadListener(LoadListener listener){
        this.listener = listener;
    }

    public void loadRepos(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    final List<String> repos = new ArrayList<String>();
                    repos.add("sb");

                    if (listener != null){
                        listener.onSuccess(repos);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    listener.onFail(500, "sbsbbsbs");
                }
            }
        }).start();
    }
}
