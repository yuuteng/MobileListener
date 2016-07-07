package com.nicolas.mobilelistener.service;

import com.nicolas.mobilelistener.bean.AllQuestion;

import java.util.Map;
import java.util.Objects;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Nikolas on 2015/9/15.
 */
public interface QuestionService {

    @FormUrlEncoded
    @POST("/student/queById")
    public void getQueById(@Field("test_id") String testId, Callback<AllQuestion> callback);

    @FormUrlEncoded
    @POST("/student/ans")
    public void checkQueAns(@Field("que_id") int queId, @Field("ans") String ans, @Field("stu_id") String stuId, Callback<Map<String, Object>> callback);

    @FormUrlEncoded
    @POST("/student/compTest")
    public void completeTest(@Field("stu_id") String stuId, @Field("test_id") String queId, Callback<Map<String, Object>> callback);

}
