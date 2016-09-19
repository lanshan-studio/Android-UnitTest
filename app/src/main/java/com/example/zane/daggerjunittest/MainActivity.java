package com.example.zane.daggerjunittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zane.daggerjunittest.inject.DaggerLoginTestComponent;
import com.example.zane.daggerjunittest.inject.LoginTestModule;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    LoginPresenter loginPresenter;

    private EditText mUsername;
    private EditText mPassword;
    private Button mButton;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //通过一个holder来传入mock出来的mocule
        LoginComponentHolder.getComponent().inject(this);

        mUsername = (EditText) findViewById(R.id.edit_name);
        mPassword = (EditText) findViewById(R.id.edit_password);
        mButton = (Button) findViewById(R.id.button);

        mButton.setOnClickListener(v -> {
            String username = mUsername.getText().toString();
            String password = mPassword.getText().toString();
            loginPresenter.login(username, password);
        });
    }
}