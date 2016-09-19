package com.example.zane.daggerjunittest.inject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.zane.daggerjunittest.ApiService;
import com.example.zane.daggerjunittest.LoginPresenter;
import com.example.zane.daggerjunittest.PasswordValidator;
import com.example.zane.daggerjunittest.UsersManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zane on 16/9/8.
 * Email: zanebot96@gmail.com
 */
@Module
public class LoginTestModule {

    private Context context;

    public LoginTestModule(Context context){
        this.context = context;
    }

    @Provides
    public Context provideContext(){
        return context;
    }

    @Provides
    public PasswordValidator providePasswordValidator(){
        return new PasswordValidator();
    }

    @Provides
    public SharedPreferences provideSharedPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    public ApiService provideApiService(){
        return new ApiService();
    }

    @Provides
    public UsersManager provideUserManager(SharedPreferences sp, ApiService service){
        return new UsersManager(sp, service);
    }

    @Provides
    public LoginPresenter provideLoginPresenter(UsersManager manager, PasswordValidator passwordValidator){
        return new LoginPresenter(manager, passwordValidator);
    }

}
