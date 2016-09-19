package com.example.zane.daggerjunittest.inject;

import com.example.zane.daggerjunittest.MainActivity;

import dagger.Component;

/**
 * Created by Zane on 16/9/8.
 * Email: zanebot96@gmail.com
 */

@Component(modules = {LoginTestModule.class})
public interface LoginTestComponent {
    void inject(MainActivity activity);
}
