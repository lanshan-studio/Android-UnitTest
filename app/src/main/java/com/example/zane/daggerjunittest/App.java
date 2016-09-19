package com.example.zane.daggerjunittest;

import android.app.Application;

import com.example.zane.daggerjunittest.inject.DaggerLoginTestComponent;
import com.example.zane.daggerjunittest.inject.LoginTestComponent;
import com.example.zane.daggerjunittest.inject.LoginTestModule;

/**
 * Created by Zane on 16/9/17.
 * Email: zanebot96@gmail.com
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        LoginTestComponent component = DaggerLoginTestComponent.builder()
                .loginTestModule(new LoginTestModule(this))
                .build();

        LoginComponentHolder.setComponent(component);
    }
}
