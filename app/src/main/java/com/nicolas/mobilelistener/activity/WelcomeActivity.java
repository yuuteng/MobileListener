package com.nicolas.mobilelistener.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import com.nicolas.mobilelistener.R;
import com.nicolas.mobilelistener.bean.StuIdHolder;
import com.orhanobut.logger.Logger;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Nikolas on 2015/9/14.
 */
public class WelcomeActivity extends Activity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        final boolean isLogined = preferences.getBoolean("isLogined", false);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (isLogined) {
                    startActivity(new Intent(WelcomeActivity.this, MusicActivity.class));
                } else {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }
                WelcomeActivity.this.finish();
            }
        }, 2000);
    }

}
