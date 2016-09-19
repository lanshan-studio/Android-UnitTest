package com.example.zane.daggerjunittest;

import com.example.zane.daggerjunittest.inject.LoginTestComponent;

/**
 * Created by Zane on 16/9/17.
 * Email: zanebot96@gmail.com
 */

public class LoginComponentHolder {

    private static LoginTestComponent component;

    public static void setComponent(LoginTestComponent component2){
        component = component2;
    }

    public static LoginTestComponent getComponent(){
        return component;
    }
}
