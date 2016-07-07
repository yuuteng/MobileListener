package com.nicolas.mobilelistener.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nicolas.mobilelistener.R;
import com.nicolas.mobilelistener.application.ListenerApplication;
import com.nicolas.mobilelistener.bean.IsSuccess;
import com.nicolas.mobilelistener.bean.StuIdHolder;
import com.nicolas.mobilelistener.service.AccountService;
import com.orhanobut.logger.Logger;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Nikolas on 2015/9/14.
 */
public class LoginActivity extends Activity implements Callback<IsSuccess>, View.OnClickListener {

    private EditText userView;
    private EditText passwordView;
    private Button loginBtn;
    private View registerView;
    private static LoginActivity self;

    private AccountService accountService;
    private RestAdapter restAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initView();

        restAdapter = ((ListenerApplication) getApplication()).getAdapter();
        accountService = restAdapter.create(AccountService.class);
        self = this;
    }

    private void initView() {
        userView = (EditText) findViewById(R.id.loginUser);
        passwordView = (EditText) findViewById(R.id.loginPassword);
        loginBtn = (Button) findViewById(R.id.btnLogin);
        registerView = findViewById(R.id.register);

        loginBtn.setOnClickListener(this);
        registerView.setOnClickListener(this);
    }

    @Override
    public void success(IsSuccess isSuccess, Response response) {
        if (isSuccess.getMessage().equals("true")) {
            SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
            StuIdHolder.userId = isSuccess.getStuId();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isLogined", true);
            editor.putString("stuId", StuIdHolder.userId);
            editor.commit();
            startActivity(new Intent(this, MusicActivity.class));
            finish();
        } else {
            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void failure(RetrofitError error) {
        Logger.d(error.getMessage());
        Toast.makeText(this, "网络连接失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                if ("".equals(userView.getText().toString())) {
                    Toast.makeText(this, "请输入学号", Toast.LENGTH_SHORT).show();
                } else if ("".equals(passwordView.getText().toString())) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else {
                    accountService.login(userView.getText().toString(), passwordView.getText().toString(), this);
                }
                break;
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

}
