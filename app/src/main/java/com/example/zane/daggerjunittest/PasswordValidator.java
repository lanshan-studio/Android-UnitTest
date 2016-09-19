package com.example.zane.daggerjunittest;

/**
 * Created by Zane on 16/9/8.
 * Email: zanebot96@gmail.com
 */

public class PasswordValidator {
    public boolean verifyPassword(String password){
        if (password.length() >= 6){
            return true;
        }
        return false;
    }
}
