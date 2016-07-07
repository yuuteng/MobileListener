package com.nicolas.mobilelistener.service;

import com.nicolas.mobilelistener.bean.StuIdHolder;

import retrofit.RequestInterceptor;

/**
 * Created by Nikolas on 2015/9/14.
 */
public class LoginInterceptor implements RequestInterceptor {
    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("Cookie", "stuId=" + StuIdHolder.userId);
    }

}
