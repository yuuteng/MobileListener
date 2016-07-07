package com.nicolas.mobilelistener.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nicolas.mobilelistener.R;
import com.nicolas.mobilelistener.application.ListenerApplication;
import com.nicolas.mobilelistener.bean.IsSuccess;
import com.nicolas.mobilelistener.service.AccountService;
import com.orhanobut.logger.Logger;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Nikolas on 2015/9/14.
 */
public class RegisterActivity extends Activity implements View.OnClickListener, Callback<IsSuccess> {

    private EditText nameView;
    private EditText passwordView;
    private EditText surePassView;
    private EditText gradeView;
    private EditText classView;
    private EditText realNameView;
    private Button registerBtn;

    private AccountService accountService;
    private RestAdapter restAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        initView();

        restAdapter = ((ListenerApplication) getApplication()).getAdapter();
        accountService = restAdapter.create(AccountService.class);
    }

    private void initView() {
        nameView = (EditText) findViewById(R.id.registerUser);
        passwordView = (EditText) findViewById(R.id.registerPassword);
        surePassView = (EditText) findViewById(R.id.registerPwdConfirm);
        gradeView = (EditText) findViewById(R.id.grade);
        classView = (EditText) findViewById(R.id.clazz);
        realNameView = (EditText) findViewById(R.id.real_name);
        registerBtn = (Button) findViewById(R.id.registerConfirm);

        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerConfirm:
                if ("".equals(nameView.getText().toString())) {
                    Toast.makeText(this, "请输入学号", Toast.LENGTH_SHORT).show();
                } else if ("".equals(passwordView.getText().toString())) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else if (!passwordView.getText().toString().equals(surePassView.getText().toString())) {
                    Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                } else if ("".equals(gradeView.getText().toString())) {
                    Toast.makeText(this, "请输入年级", Toast.LENGTH_SHORT).show();
                } else if ("".equals(classView.getText().toString())) {
                    Toast.makeText(this, "请输入班级", Toast.LENGTH_SHORT).show();
                } else if ("".equals(realNameView.getText().toString())) {
                    Toast.makeText(this, "请输入真实姓名", Toast.LENGTH_SHORT).show();
                } else {
                    accountService.register("", passwordView.getText().toString(), Integer.valueOf(gradeView.getText().toString()),
                            Integer.valueOf(classView.getText().toString()), realNameView.getText().toString(),
                            nameView.getText().toString(), this);
                }
                break;
        }
    }

    @Override
    public void success(IsSuccess isSuccess, Response response) {
        if (isSuccess.getMessage().equals("success")) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else if (isSuccess.getMessage().equals("double")) {
            Toast.makeText(this, "学号已经注册，请登录", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(this, "网络连接异常", Toast.LENGTH_SHORT).show();
    }
}
