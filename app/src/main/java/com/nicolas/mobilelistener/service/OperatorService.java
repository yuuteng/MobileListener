package com.nicolas.mobilelistener.service;

import com.nicolas.mobilelistener.bean.AllTest;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Nikolas on 2015/9/14.
 */
public interface OperatorService {

    @FormUrlEncoded
    @POST("/student/allTest")
    public void getAllTest(@Field("stu_id") String stuId, Callback<AllTest> callback);

}
