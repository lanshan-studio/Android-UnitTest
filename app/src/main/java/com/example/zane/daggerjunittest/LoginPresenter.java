package com.example.zane.daggerjunittest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Zane on 16/9/8.
 * Email: zanebot96@gmail.com
 */

public class LoginPresenter{

    private PasswordValidator passwordValidator;
    private UsersManager manager;

    public LoginPresenter(UsersManager manager, PasswordValidator passwordValidator){
        this.manager = manager;
        this.passwordValidator = passwordValidator;
    }

    public void login(String username, String password){
        if (passwordValidator.verifyPassword(password)){
            manager.remoteLogin(username, password);
        }
    }

}
