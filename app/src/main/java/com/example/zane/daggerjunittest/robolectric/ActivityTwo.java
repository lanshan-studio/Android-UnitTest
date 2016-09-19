package com.example.zane.daggerjunittest.robolectric;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.zane.daggerjunittest.R;

/**
 * Created by Zane on 16/9/8.
 * Email: zanebot96@gmail.com
 */

public class ActivityTwo extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_layout);
    }
}
