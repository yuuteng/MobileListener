package com.nicolas.mobilelistener.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.nicolas.mobilelistener.bean.StuIdHolder;
import com.nicolas.mobilelistener.service.LoginInterceptor;

import retrofit.RestAdapter;

/**
 * Created by Nikolas on 2015/9/14.
 */
public class ListenerApplication extends Application {

    private RestAdapter adapter;

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        StuIdHolder.userId = preferences.getString("stuId", "");
        adapter = new RestAdapter.Builder().setEndpoint("http://192.168.1.124:8080").setRequestInterceptor(new LoginInterceptor()).build();
    }

    public RestAdapter getAdapter() {
        return adapter;
    }
}
