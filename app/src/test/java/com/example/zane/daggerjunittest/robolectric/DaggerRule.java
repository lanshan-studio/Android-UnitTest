package com.example.zane.daggerjunittest.robolectric;

import com.example.zane.daggerjunittest.LoginComponentHolder;
import com.example.zane.daggerjunittest.inject.LoginTestComponent;
import com.example.zane.daggerjunittest.inject.LoginTestModule;

import org.robolectric.RuntimeEnvironment;

import it.cosenonjaviste.daggermock.DaggerMockRule;

/**
 * Created by Zane on 16/9/17.
 * Email: zanebot96@gmail.com
 */

public class DaggerRule extends DaggerMockRule<LoginTestComponent>{
    public DaggerRule() {
        super(LoginTestComponent.class, new LoginTestModule(RuntimeEnvironment.application));
        set(new ComponentSetter<LoginTestComponent>() {
            @Override
            public void setComponent(LoginTestComponent loginTestComponent) {
                LoginComponentHolder.setComponent(loginTestComponent);
            }
        });
    }
}
