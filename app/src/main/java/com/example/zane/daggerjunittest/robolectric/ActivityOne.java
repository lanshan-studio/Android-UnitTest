package com.example.zane.daggerjunittest.robolectric;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.zane.daggerjunittest.R;

/**
 * Created by Zane on 16/9/8.
 * Email: zanebot96@gmail.com
 */

public class ActivityOne extends AppCompatActivity {

    private TextView mText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ont_layout);
        mText = (TextView) findViewById(R.id.text_activity_one);
        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityOne.this, ActivityTwo.class));
            }
        });
    }
}
